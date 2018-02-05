package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.enumerations.DurationType;
import tn.legacy.monivulationws.repositories.CycleRepository;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class CycleService {

    public static float DEFAULT_PERIOD_LENGTH = 3;
    public static float DEFAULT_CYCLE_LENGTH = 28;
    public static int DEFAULT_FERTILITY_START = 8;
    public static int DEFAULT_FERTILITY_END = 15;

    @Autowired
    private CycleRepository cycleRepository;



    //get cycle of specific date
    public Cycle getCycle (User user, LocalDateTime startDate){
        return cycleRepository.findByUserAndStartDate(user,startDate);
    }

    //get last recorded cycle
    public  Cycle getCycle(User user){
        return cycleRepository.findByUserAndStartDateBefore(user, DateUtil.getCurrentTime());
    }

    //save the first default cycle
    public void saveFirstCycle(Cycle newCyle){
        if (newCyle.getLength() == 0)
            newCyle.setLength(DEFAULT_CYCLE_LENGTH);
        if(newCyle.getPeriodLength() == 0)
            newCyle.setPeriodLength(DEFAULT_PERIOD_LENGTH);
        if (newCyle.getStartDate() == null)
            newCyle.setStartDate(DateUtil.getCurrentTime());

        newCyle.setFertilityStartDate(newCyle.getStartDate().plusDays(DEFAULT_FERTILITY_START));
        newCyle.setFertilityEndDate(newCyle.getStartDate().plusDays(DEFAULT_FERTILITY_END));

        cycleRepository.save(newCyle);
    }
    //start new cycle (period start)
    public void startCycle(User user){
        Cycle newCycle = new Cycle();
        LocalDateTime startDate = DateUtil.getCurrentTime();
        float cycleLength = cycleRepository.getAverageCycleLenght(user);
        if (cycleLength == 0) cycleLength = DEFAULT_CYCLE_LENGTH;
        float periodLength = cycleRepository.getAveragePeriodLenght(user);
        if (periodLength == 0) periodLength = DEFAULT_PERIOD_LENGTH;

        newCycle.setStartDate(startDate);
        newCycle.setLength(cycleLength);
        newCycle.setPeriodLength(periodLength);

        cycleRepository.save(newCycle);
    }

    //end period of current cycle
    public void endCyclePeriod(User user){
        Cycle cycle = getCycle(user);
        float periodLength = DateUtil.getDurationBetween(cycle.getStartDate(),DateUtil.getCurrentTime(), DurationType.Days);

        cycle.setPeriodLength(periodLength);

        cycleRepository.save(cycle);
    }

    public void updateCycle(Cycle cycle){
        cycleRepository.save(cycle);
    }

    public float getAveragePeriodLenght (User user){
        return  cycleRepository.getAveragePeriodLenght(user);
    }

    public float getAverageCycleLenght (User user){
        return  cycleRepository.getAverageCycleLenght(user);
    }
}
