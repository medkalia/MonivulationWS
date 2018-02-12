package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.Pregnancy;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.repositories.PregnancyRepository;

import java.time.LocalDateTime;

@Service
public class PregnancyService {

    public final static int DEFAULT_PREGNANCY_LENGTH = 9;

    @Autowired
    private PregnancyRepository pregnancyRepository;

    @Autowired
    private CycleService cycleService;

    //---------------CRUD---------------
    //Create at current date
    public void startPregnancy (User user){
        Pregnancy newPregnancy = new Pregnancy();
        Cycle currentCycle = cycleService.getCycle(user);

        newPregnancy.setStartDate(DateUtil.getCurrentDateTime());
        newPregnancy.setFinishDate(newPregnancy.getStartDate().plusMonths(DEFAULT_PREGNANCY_LENGTH));
        newPregnancy.setCycle(currentCycle);
        newPregnancy.setFinished(false);

        pregnancyRepository.save(newPregnancy);
    }

    //Create at specified date
    public void startPregnancy (User user, LocalDateTime startDate){
        Pregnancy newPregnancy = new Pregnancy();
        Cycle currentCycle = cycleService.getCycle(user);

        newPregnancy.setStartDate(startDate);
        newPregnancy.setFinishDate(startDate.plusMonths(DEFAULT_PREGNANCY_LENGTH));
        newPregnancy.setCycle(currentCycle);
        newPregnancy.setFinished(false);

        pregnancyRepository.save(newPregnancy);
    }
    //Get by user
    public Pregnancy getPregnancy (User user){
        return pregnancyRepository.findByCycle(cycleService.getCycle(user));
    }
    //Get by cycle
    public Pregnancy getPregnancy (Cycle cycle){
        return pregnancyRepository.findByCycle(cycle);
    }
    //Get ongoing by user
    public Pregnancy getCurrentPregnancy (User user){
        return pregnancyRepository.findByCycleAndIsFinished(cycleService.getCycle(user), false);
    }
    //Get ongoing by cycle
    public Pregnancy getCurrentPregnancy (Cycle cycle){
        return pregnancyRepository.findByCycleAndIsFinished(cycle, false);
    }
    //------------------------------------------
    //---------------Operations---------------
    //Confirm the end of a pregnancy
    public void confirmFinishedPregnancy (User user){
        Pregnancy pregnancyToFinish = pregnancyRepository.findByCycleAndIsFinished(cycleService.getCycle(user),false);
        if (pregnancyToFinish != null){
            pregnancyToFinish.setFinished(true);
            pregnancyToFinish.setFinishDate(DateUtil.getCurrentDateTime());

            pregnancyRepository.save(pregnancyToFinish);
        }
    }
}
