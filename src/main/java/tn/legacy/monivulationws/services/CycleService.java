package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.CustomClasses.CycleInfo;
import tn.legacy.monivulationws.CustomClasses.PeriodInfo;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.Status;
import tn.legacy.monivulationws.enumerations.DurationType;
import tn.legacy.monivulationws.enumerations.PeriodInfoDescription;
import tn.legacy.monivulationws.enumerations.StatusName;
import tn.legacy.monivulationws.repositories.CycleRepository;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private StatusService statusService;


    //---------------CRUD---------------
    //get cycle of specific date
    public Cycle getCycle(AppUser appUser, LocalDateTime startDate) {
        return cycleRepository.findFirstByAppUserAndStartDate(appUser, startDate);
    }

    //get last recorded cycle
    public CycleInfo getCycleInfo(AppUser appUser) {
        List<Cycle> resultList = cycleRepository.getLastCycle(appUser);
        Status status = statusService.getStatus(appUser);
        if (resultList.size() > 0) {
            Cycle currentCycle = resultList.get(0);
            CycleInfo cycleInfo = new CycleInfo();
            if ((status.getName() == StatusName.follicular && status.isConfirmed())) {
                cycleInfo.startDate = status.getStartDate();
                cycleInfo.currentStatus = StatusName.follicular;
            } else if (status.getName() == StatusName.follicular && !status.isConfirmed()) {
                cycleInfo.startDate = currentCycle.getStartDate();
                cycleInfo.currentStatus = StatusName.luteal;
            } else if (status.getName() == StatusName.luteal) {
                cycleInfo.startDate = currentCycle.getStartDate();
                cycleInfo.currentStatus = StatusName.luteal;
            }
            cycleInfo.length = currentCycle.getLength();
            cycleInfo.periodLength = currentCycle.getPeriodLength();
            cycleInfo.fertilityStartDate = currentCycle.getFertilityStartDate();
            cycleInfo.fertilityEndDate = currentCycle.getFertilityEndDate();
            cycleInfo.follicularLength = currentCycle.getFollicularLength();
            cycleInfo.lutealLength = currentCycle.getLutealLength();
            cycleInfo.currentDayOfCycle = (int) DateUtil.getDurationBetween(cycleInfo.startDate, DateUtil.getCurrentDateTime(), DurationType.Days);
            cycleInfo.endDate = DateUtil.addNumberOfDaysTo(cycleInfo.startDate, currentCycle.getLength());
            return cycleInfo;
        } else {
            return null;
        }
    }

    public CycleInfo getCycleInfo(AppUser appUser, LocalDateTime date) {
        Cycle cycle = null;
        List<Cycle> resultList = cycleRepository.getFirstCycleBeforeOrAt(appUser, date);
        if (resultList.size() > 0) {
            cycle = resultList.get(0);
        }
        if (cycle != null) {
            CycleInfo cycleInfo = new CycleInfo();
            cycleInfo.startDate = cycle.getStartDate();
            cycleInfo.currentStatus = StatusName.luteal;
            cycleInfo.length = cycle.getLength();
            cycleInfo.periodLength = cycle.getPeriodLength();
            cycleInfo.fertilityStartDate = cycle.getFertilityStartDate();
            cycleInfo.fertilityEndDate = cycle.getFertilityEndDate();
            cycleInfo.follicularLength = cycle.getFollicularLength();
            cycleInfo.lutealLength = cycle.getLutealLength();
            cycleInfo.currentDayOfCycle = (int) DateUtil.getDurationBetween(cycleInfo.startDate, DateUtil.getCurrentDateTime(), DurationType.Days);
            cycleInfo.endDate = DateUtil.addNumberOfDaysTo(cycleInfo.startDate, cycle.getLength());
            return cycleInfo;
        } else {
            return null;
        }
    }

    //get last recorded cycle
    public Cycle getCycle(AppUser appUser) {
        List<Cycle> resultList = cycleRepository.getLastCycle(appUser);
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    //get all user cycles
    public List<Cycle> getAllCycle(AppUser appUser) {
        return cycleRepository.findAllByAppUser(appUser);
    }


    //save the first default cycle
    public String saveFirstCycle(Cycle newCycle) {
        if (cycleRepository.findByAppUser(newCycle.getAppUser()) == null) {
            if (newCycle.getLength() == 0)
                newCycle.setLength(DEFAULT_CYCLE_LENGTH);
            if (newCycle.getPeriodLength() == 0)
                newCycle.setPeriodLength(DEFAULT_PERIOD_LENGTH);
            if (newCycle.getStartDate() == null)
                newCycle.setStartDate(DateUtil.getCurrentDateTime());

            newCycle.setFertilityStartDate(newCycle.getStartDate().plusDays(DEFAULT_FERTILITY_START));
            newCycle.setFertilityEndDate(newCycle.getStartDate().plusDays(DEFAULT_FERTILITY_END));
            newCycle.setFollicularLength(DEFAULT_FOLLICULAR_LENGTH);
            newCycle.setLutealLength(DEFAULT_LUTHEAL_LENGTH);

            cycleRepository.save(newCycle);
            return "First cycle created";
        } else return "ERROR : User already have a cycle";

    }

    //start new cycle (period start)
    public void startCycle(AppUser appUser) {
        Cycle newCycle = new Cycle();
        /*TEST*/ //LocalDateTime startDate = DateUtil.parseDate("31-01-2018 10:00:00");
        LocalDateTime startDate = DateUtil.getCurrentDateTime();
        float cycleLength = getAverageCycleLenght(appUser);
        float periodLength = getAveragePeriodLenght(appUser);
        float follicularLength = getAverageFollicularLength(appUser);
        float lutealLength = getAverageLutealLength(appUser);

        newCycle.setStartDate(startDate);
        newCycle.setLength(cycleLength);
        newCycle.setPeriodLength(periodLength);
        newCycle.setFollicularLength(follicularLength);
        newCycle.setLutealLength(lutealLength);
        newCycle.setFertilityStartDate(newCycle.getStartDate().plusDays(DEFAULT_FERTILITY_START));
        newCycle.setFertilityEndDate(newCycle.getStartDate().plusDays(DEFAULT_FERTILITY_END));
        newCycle.setAppUser(appUser);

        cycleRepository.save(newCycle);
    }

    //------------------------------------------
    //---------------Operations---------------
    //end period of current cycle
    public String endCyclePeriod(AppUser appUser) {
        Cycle cycle = getCycle(appUser);
        if (cycle != null && statusService.getStatus(appUser) != null) {
            /*TEST*/ //float periodLength = DateUtil.getDurationBetween(cycle.getStartDate(),DateUtil.parseDate("03-02-2018 15:00:00"), DurationType.Days);
            float periodLength = DateUtil.getDurationBetween(cycle.getStartDate(), DateUtil.getCurrentDateTime(), DurationType.Days);

            cycle.setPeriodLength(periodLength);

            cycleRepository.save(cycle);
            return "Period Finished";
        }
        return "No Status Or/And Cycle Created! Please call the addFirstCycle Service before adding other data for user of id " + appUser.getId();
    }

    public void updateFollicularLength(AppUser appUser) {
        Cycle cycle = getCycle(appUser);
        /*TEST*/ /*float follicularLength = DateUtil.getDurationBetween(
                cycle.getStartDate(),
                DateUtil.parseDate("16-01-2018 08:00:00"),
                DurationType.Days);*/
        float follicularLength = DateUtil.getDurationBetween(
                cycle.getStartDate(),
                DateUtil.getCurrentDateTime(),
                DurationType.Days);
        if (follicularLength == 0) follicularLength = DEFAULT_FOLLICULAR_LENGTH;
        cycle.setFollicularLength(follicularLength);

        cycleRepository.save(cycle);
    }

    public void updateLutealLength(AppUser appUser) {
        Cycle cycle = getCycle(appUser);
        /*TEST*/ /*float lutealLength = DateUtil.getDurationBetween(
                DateUtil.addNumberOfDaysTo(cycle.getStartDate(), cycle.getFollicularLength()),
                DateUtil.parseDate("31-01-2018 10:00:00"),
                DurationType.Days);*/
        float lutealLength = DateUtil.getDurationBetween(
                DateUtil.addNumberOfDaysTo(cycle.getStartDate(), cycle.getFollicularLength()),
                DateUtil.getCurrentDateTime(),
                DurationType.Days);
        if (lutealLength == 0) lutealLength = DEFAULT_LUTHEAL_LENGTH;
        cycle.setLutealLength(lutealLength);

        cycleRepository.save(cycle);
    }

    //------------------------------------------
    //---------------Calculations---------------
    public float getAverageCycleLenght(AppUser appUser) {
        float cycleLength;
        if (cycleRepository.findByAppUser(appUser) != null) {
            cycleLength = cycleRepository.getAverageCycleLenght(appUser);

        } else {
            cycleLength = 0;
        }
        return cycleLength;
    }

    public float getAveragePeriodLenght(AppUser appUser) {
        float periodLength;
        if (cycleRepository.findByAppUser(appUser) != null) {
            periodLength = cycleRepository.getAveragePeriodLenght(appUser);
        } else {
            periodLength = 0;
        }
        return periodLength;
    }

    public float getAverageFollicularLength(AppUser appUser) {
        float follicularLength;
        if (cycleRepository.findByAppUser(appUser) != null) {
            follicularLength = cycleRepository.getAverageFollicularLength(appUser);
        } else {
            follicularLength = 0;
        }
        return follicularLength;
    }

    public float getAverageLutealLength(AppUser appUser) {
        float lutealLength;
        if (cycleRepository.findByAppUser(appUser) != null) {
            lutealLength = cycleRepository.getAverageLutealLength(appUser);
        } else {
            lutealLength = 0;
        }
        return lutealLength;
    }

    public PeriodInfo getPeriodInfo(AppUser appUser) {
        Cycle currentCycle = getCycle(appUser);
        Status status = statusService.getStatus(appUser);
        PeriodInfo periodInfo = new PeriodInfo();

        if (status.getName() == StatusName.follicular && status.isConfirmed()) {
            Cycle previousCycle = new Cycle();
            List<Cycle> resultList = cycleRepository.getFirstCycleBefore(appUser, currentCycle.getStartDate());
            if (resultList.size() > 0) {
                previousCycle = resultList.get(0);
            }
            periodInfo.startDate = currentCycle.getStartDate();
            periodInfo.endDate = DateUtil.addNumberOfDaysTo(periodInfo.startDate, currentCycle.getPeriodLength());
            periodInfo.isDelayed = previousCycle.getLutealLength() > currentCycle.getLutealLength();
            periodInfo.delay = previousCycle.getLutealLength() - currentCycle.getLutealLength();
            periodInfo.description = PeriodInfoDescription.confirmed;
        } else if (status.getName() == StatusName.follicular && !status.isConfirmed()) {
            LocalDateTime startOfCurrentLutealPhase = DateUtil.addNumberOfDaysTo(
                    currentCycle.getStartDate(),
                    currentCycle.getFollicularLength());
            float durationOfCurrentLutealPhase = DateUtil.getDurationBetween(
                    startOfCurrentLutealPhase,
                    DateUtil.getCurrentDateTime(),
                    DurationType.Days);

            periodInfo.startDate = status.getStartDate();
            periodInfo.endDate = DateUtil.addNumberOfDaysTo(periodInfo.startDate, currentCycle.getPeriodLength());
            periodInfo.isDelayed = durationOfCurrentLutealPhase > currentCycle.getLutealLength();
            if (periodInfo.isDelayed)
                periodInfo.delay = durationOfCurrentLutealPhase - currentCycle.getLutealLength();
            else
                periodInfo.delay = 0;
            periodInfo.description = PeriodInfoDescription.notConfirmed;
        } else if (status.getName() == StatusName.luteal) {
            LocalDateTime startOfCurrentLutealPhase = DateUtil.addNumberOfDaysTo(
                    currentCycle.getStartDate(),
                    currentCycle.getFollicularLength());
            float durationOfCurrentLutealPhase = DateUtil.getDurationBetween(
                    startOfCurrentLutealPhase,
                    DateUtil.getCurrentDateTime(),
                    DurationType.Days);

            periodInfo.startDate = DateUtil.addNumberOfDaysTo(currentCycle.getStartDate(), currentCycle.getLength());
            periodInfo.endDate = DateUtil.addNumberOfDaysTo(periodInfo.startDate, currentCycle.getPeriodLength());
            periodInfo.isDelayed = durationOfCurrentLutealPhase > currentCycle.getLutealLength();
            if (periodInfo.isDelayed)
                periodInfo.delay = durationOfCurrentLutealPhase - currentCycle.getLutealLength();
            else
                periodInfo.delay = 0;
            periodInfo.description = PeriodInfoDescription.approximate;
        }

        return periodInfo;
    }
    //------------------------------------------

}

