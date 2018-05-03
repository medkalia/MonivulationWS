package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.CustomClasses.DateEntry;
import tn.legacy.monivulationws.Util.CycleCalculationUtil;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.Status;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.enumerations.DurationType;
import tn.legacy.monivulationws.enumerations.StatusName;
import tn.legacy.monivulationws.repositories.StatusRepository;

import java.time.LocalDateTime;

@Service
public class StatusService {
    public static final float DEFAULT_MIN_IGNORE_TEMPERATURE = 36;
    public static final float DEFAULT_SWITCH_TEMPERATURE = 37;
    public static final float DEFAULT_MAX_IGNORE_TEMPERATURE = 38;


    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CycleService cycleService;
    @Autowired
    private PregnancyService pregnancyService;

    //create status for first time
    public String createFirstStatus(AppUser appUser, Cycle newCycle){
        if (statusRepository.findByAppUser(appUser) == null){
            Status newStatus = new Status();
            StatusName currentStatusName = DateUtil.getDurationBetween(
                    newCycle.getStartDate(),
                    DateUtil.getCurrentDateTime(),
                    DurationType.Days) < CycleCalculationUtil.getStatusLength((int)newCycle.getLength()).get(StatusName.follicular) ?
                    StatusName.follicular :
                    StatusName.luteal ;

            newStatus.setName(currentStatusName);
            newStatus.setStartDate(newCycle.getStartDate());
            newStatus.setConfirmed(true);
            newStatus.setAppUser(appUser);

            statusRepository.save(newStatus);

            return "Status created";
        }

        return  "Status already exists";

    }

    //return status of appUser
    public Status getStatus(AppUser appUser) {
        return statusRepository.findByAppUser(appUser);
    }

    //Called after each temperature data entry to update status if needed
    public String checkStatus(TemperatureData temperatureData){
        AppUser appUser = temperatureData.getAppUser();
        Status status = getStatus(appUser);
        LocalDateTime startDateToSave = DateUtil.getCurrentDateTime();
        if (temperatureData.getEntryDate() != null)
            startDateToSave = temperatureData.getEntryDate();
        if(temperatureData.getValue() > DEFAULT_MAX_IGNORE_TEMPERATURE)
            temperatureData.setValue(DEFAULT_MAX_IGNORE_TEMPERATURE);
        else if (temperatureData.getValue() < DEFAULT_MIN_IGNORE_TEMPERATURE)
            temperatureData.setValue(DEFAULT_MIN_IGNORE_TEMPERATURE);
        switch (status.getName()){
            case follicular:
                if (temperatureData.getValue() >= DEFAULT_SWITCH_TEMPERATURE ){
                    status.setName(StatusName.luteal);
                    /*TEST*/ //status.setStartDate(DateUtil.parseDate("16-01-2018 08:00:00"));
                    status.setStartDate(startDateToSave);
                    cycleService.updateFollicularLength(appUser,startDateToSave);
                    statusRepository.save(status);
                    return "Temperature higher than 37 C° : Luteal phase started";
                }
                break;
            case luteal:
                if (temperatureData.getValue() < DEFAULT_SWITCH_TEMPERATURE ){
                    status.setName(StatusName.follicular);
                    /*TEST*/ //status.setStartDate(DateUtil.parseDate("30-01-2018 08:00:00"));
                    status.setStartDate(startDateToSave);
                    status.setConfirmed(false);
                    statusRepository.save(status);
                    return "Temperature lower than 37 C° : waiting for appUser confirmation of new cycle start";
                }
                else if (temperatureData.getValue() > DEFAULT_SWITCH_TEMPERATURE &&
                        DateUtil.getDurationBetween(status.getStartDate(), startDateToSave,DurationType.Days) > cycleService.getAverageLutealLength(appUser)){
                    status.setName(StatusName.pregnancy);
                    /*TEST*/ //status.setStartDate(DateUtil.parseDate("30-01-2018 08:00:00"));
                    status.setStartDate(startDateToSave);
                    status.setConfirmed(false);
                    statusRepository.save(status);
                    return "Temperature still higher than 37 C° and period delayed: waiting for appUser confirmation of pregnancy";
                }
            case pregnancy:
                if (!status.isConfirmed()){
                    if (temperatureData.getValue() < DEFAULT_SWITCH_TEMPERATURE ){
                        status.setName(StatusName.follicular);
                        /*TEST*/ //status.setStartDate(DateUtil.parseDate("30-01-2018 08:00:00"));
                        status.setStartDate(startDateToSave);
                        status.setConfirmed(false);
                        statusRepository.save(status);
                        return "Temperature lower than 37 C° : not pregnant, waiting for appUser confirmation of new cycle start";
                    }
                }
        break;
        }
        return "No changed were applied";
    }

    //This is called when appUser confirms having her period
    public String confirmStartCycle(AppUser appUser, LocalDateTime startDate){
        Status status = getStatus(appUser);
        LocalDateTime startDateToSave = DateUtil.getCurrentDateTime();
        if (startDate != null)
            startDateToSave = startDate;

        if (status != null && cycleService.getCycle(appUser) != null){
            /*TEST*/ //status.setStartDate(DateUtil.parseDate("31-01-2018 08:00:00"));
            status.setStartDate(startDateToSave);
            status.setConfirmed(true);
            statusRepository.save(status);
            cycleService.updateLutealLength(appUser, startDateToSave);
            cycleService.startCycle(appUser, startDateToSave);
            return "Start of new cycle confirmed";
        }
        return "No Status Or/And Cycle Created! Please call the addFirstCycle Service before adding other data for user of id " + appUser.getId();
    }

    //This is called when appUser confirms being pregnant
    public String confirmPregnancy (AppUser appUser, LocalDateTime startDate){
        Status status = getStatus(appUser);
        LocalDateTime startDateToSave = DateUtil.getCurrentDateTime();
        Cycle currentCycle = cycleService.getCycle(appUser);
        if (startDate != null)
            startDateToSave = startDate;
        if (status != null &&  currentCycle != null){
            currentCycle.setConsiderForCalculation(false);
            if (status.getName() !=  StatusName.pregnancy){
                status.setName(StatusName.pregnancy);
                status.setStartDate(startDateToSave);
                status.setConfirmed(true);
                statusRepository.save(status);
                pregnancyService.startPregnancy(appUser,startDateToSave);
                return "Started pregnancy manually";
            }else{
                status.setConfirmed(true);
                statusRepository.save(status);
                pregnancyService.startPregnancy(appUser,status.getStartDate());
                return "Started pregnancy manually after automatic detection";
            }
        }
        return "No Status Or/And Cycle Created! Please call the addFirstCycle Service before adding other data for user of id " + appUser.getId();
    }

    public String endPregnancy (AppUser appUser, LocalDateTime finishDate, boolean endedWithChild){
        Status status = getStatus(appUser);
        LocalDateTime finishDateToSave = DateUtil.getCurrentDateTime();
        Cycle currentCycle = cycleService.getCycle(appUser);
        if (finishDate != null)
            finishDateToSave = finishDate;
        if (status != null &&  currentCycle != null){
            confirmStartCycle(appUser, finishDateToSave);
            pregnancyService.confirmFinishedPregnancy(appUser,finishDateToSave,endedWithChild);
            if (endedWithChild)
                return "End Of pregnancy with a child";
            else
                return "End Of pregnancy without a child";
        }
        return "No Status Or/And Cycle Created! Please call the addFirstCycle Service before adding other data for user of id " + appUser.getId();
    }

}
