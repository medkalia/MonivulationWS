package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.Pregnancy;
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
    public void startPregnancy (AppUser appUser){
        Pregnancy newPregnancy = new Pregnancy();
        Cycle currentCycle = cycleService.getCycle(appUser);

        newPregnancy.setStartDate(DateUtil.getCurrentDateTime());
        newPregnancy.setFinishDate(newPregnancy.getStartDate().plusMonths(DEFAULT_PREGNANCY_LENGTH));
        newPregnancy.setCycle(currentCycle);
        newPregnancy.setFinished(false);

        pregnancyRepository.save(newPregnancy);
    }

    //Create at specified date
    public void startPregnancy (AppUser appUser, LocalDateTime startDate){
        Pregnancy newPregnancy = new Pregnancy();
        Cycle currentCycle = cycleService.getCycle(appUser);

        newPregnancy.setStartDate(startDate);
        newPregnancy.setFinishDate(startDate.plusMonths(DEFAULT_PREGNANCY_LENGTH));
        newPregnancy.setCycle(currentCycle);
        newPregnancy.setFinished(false);

        pregnancyRepository.save(newPregnancy);
    }

    //Get by appUser
    public Pregnancy getPregnancy (AppUser appUser){
        return pregnancyRepository.findByCycle(cycleService.getCycle(appUser));
    }
    //Get by cycle
    public Pregnancy getPregnancy (Cycle cycle){
        return pregnancyRepository.findByCycle(cycle);
    }
    //Get ongoing by appUser
    public Pregnancy getCurrentPregnancy (AppUser appUser){
        return pregnancyRepository.findFirstByCycleAndFinished(cycleService.getCycle(appUser), false);
    }
    //Get ongoing by cycle
    public Pregnancy getCurrentPregnancy (Cycle cycle){
        return pregnancyRepository.findFirstByCycleAndFinished(cycle, false);
    }
    //------------------------------------------
    //---------------Operations---------------
    //Confirm the end of a pregnancy
    public void confirmFinishedPregnancy (AppUser appUser, LocalDateTime finishDate, boolean endedWithChild){
        Cycle currentCycle = cycleService.getCycle(appUser);
        Pregnancy pregnancyToFinish = pregnancyRepository.findFirstByCycleAndFinished(currentCycle,false);
        if (pregnancyToFinish != null){
            pregnancyToFinish.setFinished(true);
            pregnancyToFinish.setFinishDate(finishDate);
            pregnancyToFinish.setEndedWithChild(endedWithChild);
            pregnancyRepository.save(pregnancyToFinish);
        }

    }
}
