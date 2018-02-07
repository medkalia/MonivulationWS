package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.enumerations.DurationType;
import tn.legacy.monivulationws.repositories.CycleRepository;

import java.time.LocalDateTime;

@Service
public class CycleService {

    public final static float DEFAULT_CYCLE_LENGTH = 28;
    public final static float DEFAULT_PERIOD_LENGTH = 3;
    public final static int DEFAULT_FERTILITY_START = 8;
    public final static int DEFAULT_FERTILITY_END = 15;
    public final static float DEFAULT_FOLLICULAR_LENGTH = 14;
    public final static float DEFAULT_LUTHEAL_LENGTH = 14;


    @Autowired
    private CycleRepository cycleRepository;


    //---------------CRUD---------------
    //get cycle of specific date
    public Cycle getCycle (User user, LocalDateTime startDate){
        return cycleRepository.findByUserAndStartDate(user,startDate);
    }

    //get last recorded cycle
    public  Cycle getCycle(User user){
        return cycleRepository.getLastCycle(user);
    }

    //save the first default cycle
    public void saveFirstCycle(Cycle newCycle){
        if (newCycle.getLength() == 0)
            newCycle.setLength(DEFAULT_CYCLE_LENGTH);
        if(newCycle.getPeriodLength() == 0)
            newCycle.setPeriodLength(DEFAULT_PERIOD_LENGTH);
        if (newCycle.getStartDate() == null)
            newCycle.setStartDate(DateUtil.getCurrentDateTime());

        newCycle.setFertilityStartDate(newCycle.getStartDate().plusDays(DEFAULT_FERTILITY_START));
        newCycle.setFertilityEndDate(newCycle.getStartDate().plusDays(DEFAULT_FERTILITY_END));
        newCycle.setFollicularLength(DEFAULT_FOLLICULAR_LENGTH);
        newCycle.setLutealLength(DEFAULT_LUTHEAL_LENGTH);

        cycleRepository.save(newCycle);
    }

    //start new cycle (period start)
    public void startCycle(User user){
        Cycle newCycle = new Cycle();
        /*TEST*/ LocalDateTime startDate = DateUtil.parseDate("31-01-2018 10:00:00");
        //LocalDateTime startDate = DateUtil.getCurrentDateTime();
        float cycleLength = getAverageCycleLenght(user);
        float periodLength = getAveragePeriodLenght(user);
        float follicularLength = getAverageFollicularLength(user);
        float lutealLength = getAverageLutealLength(user);

        newCycle.setStartDate(startDate);
        newCycle.setLength(cycleLength);
        newCycle.setPeriodLength(periodLength);
        newCycle.setFollicularLength(follicularLength);
        newCycle.setLutealLength(lutealLength);
        newCycle.setFertilityStartDate(newCycle.getStartDate().plusDays(DEFAULT_FERTILITY_START));
        newCycle.setFertilityEndDate(newCycle.getStartDate().plusDays(DEFAULT_FERTILITY_END));
        newCycle.setUser(user);

        cycleRepository.save(newCycle);
    }
    //------------------------------------------
    //---------------Operations---------------
    //end period of current cycle
    public void endCyclePeriod(User user){
        Cycle cycle = getCycle(user);
        /*TEST*/ float periodLength = DateUtil.getDurationBetween(cycle.getStartDate(),DateUtil.parseDate("03-02-2018 15:00:00"), DurationType.Days);
        //float periodLength = DateUtil.getDurationBetween(cycle.getStartDate(),DateUtil.getCurrentDateTime(), DurationType.Days);

        cycle.setPeriodLength(periodLength);

        cycleRepository.save(cycle);
    }
    //------------------------------------------
    //---------------Calculations---------------
    public float getAverageCycleLenght (User user){
        float cycleLength = cycleRepository.getAverageCycleLenght(user);
        if (cycleLength == 0) cycleLength = DEFAULT_CYCLE_LENGTH;
        return  cycleLength;
    }

    public float getAveragePeriodLenght (User user){
        float periodLength = cycleRepository.getAveragePeriodLenght(user);
        if (periodLength == 0) periodLength = DEFAULT_PERIOD_LENGTH;
        return  periodLength;
    }

    public float getAverageFollicularLength (User user){
        float follicularLength = cycleRepository.getAverageFollicularLength(user);
        if (follicularLength == 0) follicularLength = DEFAULT_FOLLICULAR_LENGTH;
        return  follicularLength;
    }

    public float getAverageLutealLength (User user){
        float lutealLength = cycleRepository.getAverageLutealLength(user);
        if (lutealLength == 0) lutealLength = DEFAULT_LUTHEAL_LENGTH;
        return  lutealLength;
    }

    public void updateFollicularLength (User user){
        Cycle cycle = getCycle(user);
        /*TEST*/ float follicularLength = DateUtil.getDurationBetween(
                cycle.getStartDate(),
                DateUtil.parseDate("16-01-2018 08:00:00"),
                DurationType.Days);
        /*float follicularLength = DateUtil.getDurationBetween(
                cycle.getStartDate(),
                DateUtil.getCurrentDateTime(),
                DurationType.Days);*/
        if (follicularLength == 0) follicularLength = DEFAULT_FOLLICULAR_LENGTH ;
        cycle.setFollicularLength(follicularLength);

        cycleRepository.save(cycle);
    }

    public void updateLutealLength (User user){
        Cycle cycle = getCycle(user);
        /*TEST*/ float lutealLength = DateUtil.getDurationBetween(
                DateUtil.addNumberOfDaysTo(cycle.getStartDate(), cycle.getFollicularLength()),
                DateUtil.parseDate("31-01-2018 10:00:00"),
                DurationType.Days);
        /*float lutealLength = DateUtil.getDurationBetween(
                DateUtil.addNumberOfDaysTo(cycle.getStartDate(), cycle.getFollicularLength()),
                DateUtil.getCurrentDateTime(),
                DurationType.Days);*/
        if (lutealLength == 0) lutealLength = DEFAULT_LUTHEAL_LENGTH ;
        cycle.setLutealLength(lutealLength);

        cycleRepository.save(cycle);
    }
    //------------------------------------------

}
