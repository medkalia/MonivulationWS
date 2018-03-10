package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Status;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.enumerations.DurationType;
import tn.legacy.monivulationws.enumerations.StatusName;
import tn.legacy.monivulationws.repositories.StatusRepository;

import java.time.LocalDateTime;

@Service
public class StatusService {

    public static final float DEFAULT_SWITCH_TEMPERATURE = 37;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CycleService cycleService;
    @Autowired
    private PregnancyService pregnancyService;

    //create status for first time
    public void createFirstStatus(AppUser appUser, LocalDateTime startDate){
        Status newStatus = new Status();
        StatusName currentStatusName = DateUtil.getDurationBetween(
                startDate,
                DateUtil.getCurrentDateTime(),
                DurationType.Days) < CycleService.DEFAULT_FOLLICULAR_LENGTH ? StatusName.follicular : StatusName.luteal ;
        Boolean isStatusConfirmed = true;

        newStatus.setName(currentStatusName);
        newStatus.setStartDate(startDate);
        newStatus.setConfirmed(isStatusConfirmed);
        newStatus.setAppUser(appUser);

        statusRepository.save(newStatus);
    }

    //return status of appUser
    public Status getStatus(AppUser appUser) {
        return statusRepository.findByAppUser(appUser);
    }

    //Called after each temperature data entry to update status if needed
    public String checkStatus(TemperatureData temperatureData){
        AppUser appUser = temperatureData.getAppUser();
        Status status = getStatus(appUser);
        switch (status.getName()){
            case follicular:
                if (temperatureData.getValue() >= DEFAULT_SWITCH_TEMPERATURE ){
                    status.setName(StatusName.luteal);
                    /*TEST*/ status.setStartDate(DateUtil.parseDate("16-01-2018 08:00:00"));
                    //status.setStartDate(DateUtil.getCurrentDateTime());
                    cycleService.updateFollicularLength(appUser);
                    statusRepository.save(status);
                    return "Temperature higher than 37 C° : Luteal phase started";
                }
                break;
            case luteal:
                if (temperatureData.getValue() < DEFAULT_SWITCH_TEMPERATURE ){
                    status.setName(StatusName.follicular);
                    /*TEST*/ status.setStartDate(DateUtil.parseDate("30-01-2018 08:00:00"));
                    //status.setStartDate(DateUtil.getCurrentDateTime());
                    status.setConfirmed(false);
                    statusRepository.save(status);
                    return "Temperature lower than 37 C° : waiting for appUser confirmation of new cycle start";
                }
                else if (temperatureData.getValue() > DEFAULT_SWITCH_TEMPERATURE &&
                        DateUtil.getDurationBetween(status.getStartDate(), DateUtil.getCurrentDateTime(),DurationType.Days) > cycleService.getAverageLutealLength(appUser)){
                    status.setName(StatusName.pregnancy);
                    /*TEST*/ //status.setStartDate(DateUtil.parseDate("30-01-2018 08:00:00"));
                    status.setStartDate(DateUtil.getCurrentDateTime());
                    status.setConfirmed(false);
                    statusRepository.save(status);
                    return "Temperature still higher than 37 C° and period delayed: waiting for appUser confirmation of pregnancy";
                }
            case pregnancy:
                if (temperatureData.getValue() < DEFAULT_SWITCH_TEMPERATURE ){
                    status.setName(StatusName.follicular);
                    /*TEST*/ status.setStartDate(DateUtil.parseDate("30-01-2018 08:00:00"));
                    //status.setStartDate(DateUtil.getCurrentDateTime());
                    status.setConfirmed(false);
                    statusRepository.save(status);
                    return "Temperature lower than 37 C° : not pregnant, waiting for appUser confirmation of new cycle start";
                }
        break;
        }
        return "No changed were applied";
    }

    //This is called when appUser confirms having her period
    public String confirmStartCycle(AppUser appUser){
        Status status = getStatus(appUser);
        /*TEST*/ status.setStartDate(DateUtil.parseDate("31-01-2018 08:00:00"));
        //status.setStartDate(DateUtil.getCurrentDateTime());
        status.setConfirmed(true);
        statusRepository.save(status);
        cycleService.updateLutealLength(appUser);
        cycleService.startCycle(appUser);
        return "Start of new cycle confirmed";
    }

    //This is called when appUser confirms being pregnant
    public String confirmPregnancy (AppUser appUser){
        Status status = getStatus(appUser);
        if (status.getName() !=  StatusName.pregnancy){
            status.setName(StatusName.pregnancy);
            status.setStartDate(DateUtil.getCurrentDateTime());
            status.setConfirmed(true);
            statusRepository.save(status);
            pregnancyService.startPregnancy(appUser);
            return "Started pregnancy manually";
        }else{
            status.setConfirmed(true);
            statusRepository.save(status);
            pregnancyService.startPregnancy(appUser,status.getStartDate());
            return "Started pregnancy manually after automatic detection";
        }
    }

}
