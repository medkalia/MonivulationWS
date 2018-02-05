package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.DailyRecord;
import tn.legacy.monivulationws.entities.Status;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.enumerations.DurationType;
import tn.legacy.monivulationws.enumerations.StatusName;
import tn.legacy.monivulationws.repositories.DailyRecordRepository;
import tn.legacy.monivulationws.repositories.StatusRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService {

    public static float DEFAULT_FOLLICULAR_LENGTH = 14;
    public static float DEFAULT_SWITCH_TEMPERATURE = 37;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private CycleService cycleService;

    //create status for first time
    public void createFirstStatus(User user, LocalDateTime lastPeriodDate){
        Status newStatus = new Status();
        if (lastPeriodDate == null)
            lastPeriodDate = DateUtil.getCurrentTime();
        StatusName currentStatusName = DateUtil.getDurationBetween(
                lastPeriodDate,
                DateUtil.getCurrentTime(),
                DurationType.Days) < DEFAULT_FOLLICULAR_LENGTH ? StatusName.follicular : StatusName.luteal ;
        Boolean isStatusConfirmed = true;

        newStatus.setName(currentStatusName);
        newStatus.setStartDate(lastPeriodDate);
        newStatus.setConfirmed(isStatusConfirmed);
        newStatus.setUser(user);

        statusRepository.save(newStatus);
    }

    //return status of user
    public Status getStatus(User user) {
        return statusRepository.findByUser(user);
    }

    //Called after each temperature data entry to update status if needed
    public String checkStatus(TemperatureData temperatureData){
        Status status = getStatus(temperatureData.getUser());
        switch (status.getName()){
            case follicular:
                if (temperatureData.getValue() >= DEFAULT_SWITCH_TEMPERATURE ){
                    status.setName(StatusName.luteal);
                    status.setStartDate(DateUtil.getCurrentTime());
                    return "Temperature higher than 37 C° : Luteal phase started";
                }
                break;
            case luteal:
                if (temperatureData.getValue() < DEFAULT_SWITCH_TEMPERATURE ){
                    status.setName(StatusName.follicular);
                    status.setStartDate(DateUtil.getCurrentTime());
                    return "Temperature lower than 37 C° : waiting for user confirmation of new cycle start";
                }
                /*else if (temperatureData.getValue() > DEFAULT_SWITCH_TEMPERATURE *//*&&
                        DateUtil.getDurationBetween(status.getStartDate(), DateUtil.getCurrentTime(),DurationType.Days) > cycleService.getAverageCycleLenght()*//*){

                }*/
        break;
        }
        return "No changed were applied";
    }

    //This is called when user confirms having her period
    public String confirmStartCycle(User user){
        Status status = getStatus(user);
        if (status.getName() ==  StatusName.luteal){
            status.setName(StatusName.follicular);
            status.setStartDate(DateUtil.getCurrentTime());
            status.setConfirmed(true);
            cycleService.startCycle(user);
            return "ALERT : New cycle confirmed while in follicular phase";
        }
        else if (status.getName() == StatusName.follicular && !status.isConfirmed()){
            status.setStartDate(DateUtil.getCurrentTime());
            status.setConfirmed(true);
            return "Start of new cycle confirmed";
        }
        return "No changed were applied";
    }

    //This is called when user confirms being pregnant
    /*public String confirmPregnancy (User user){
        Status status = getStatus(user);
        if (status.getName() ==  StatusName.luteal){

        }
    }*/

}
