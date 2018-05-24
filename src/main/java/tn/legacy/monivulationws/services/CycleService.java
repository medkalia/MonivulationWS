package tn.legacy.monivulationws.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.legacy.monivulationws.CustomClasses.CycleInfo;
import tn.legacy.monivulationws.CustomClasses.DateEntry;
import tn.legacy.monivulationws.CustomClasses.PeriodInfo;
import tn.legacy.monivulationws.Util.CycleCalculationUtil;
import tn.legacy.monivulationws.Util.DateUtil;
import tn.legacy.monivulationws.Util.DebugUtil;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.Pregnancy;
import tn.legacy.monivulationws.entities.Status;
import tn.legacy.monivulationws.enumerations.DurationType;
import tn.legacy.monivulationws.enumerations.PeriodInfoDescription;
import tn.legacy.monivulationws.enumerations.StatusName;
import tn.legacy.monivulationws.repositories.CycleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CycleService {


    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private PregnancyService pregnancyService;


    //---------------CRUD---------------
    //get cycle of specific date
    public Cycle getCycle(AppUser appUser, LocalDateTime startDate) {
        return cycleRepository.findFirstByAppUserAndStartDate(appUser, startDate);
    }

    //get last recorded cycle
    public CycleInfo getCycleInfo(AppUser appUser, LocalDateTime entryDate) {
        List<Cycle> resultList = cycleRepository.getLastCycle(appUser);
        Status status = statusService.getStatus(appUser);
        LocalDateTime actualEntryDate = DateUtil.getCurrentDateTime();
        if (entryDate != null)
            actualEntryDate = entryDate;
        if (resultList.size() > 0) {
            Cycle currentCycle = resultList.get(0);
            CycleInfo cycleInfo = new CycleInfo();
            Pregnancy currentPregnancy = pregnancyService.getCurrentPregnancy(currentCycle);
            if (status.getName() != StatusName.pregnancy || (status.getName() == StatusName.pregnancy && currentPregnancy == null)) {
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
                cycleInfo.currentDayOfCycle = (int) DateUtil.getDurationBetween(cycleInfo.startDate, actualEntryDate, DurationType.Days);
                cycleInfo.endDate = DateUtil.addNumberOfDaysTo(cycleInfo.startDate, currentCycle.getLength());
            } else {
                cycleInfo.startDate = currentPregnancy.getStartDate();
                cycleInfo.currentStatus = StatusName.pregnancy;
                cycleInfo.endDate = currentPregnancy.getFinishDate();
                cycleInfo.currentDayOfCycle = (int) DateUtil.getDurationBetween(cycleInfo.startDate, actualEntryDate, DurationType.Days);
            }
            return cycleInfo;
        } else {
            return null;
        }
    }

    public CycleInfo getCycleInfoAt(AppUser appUser, LocalDateTime date) {
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
            cycleInfo.currentDayOfCycle = (int) DateUtil.getDurationBetween(cycleInfo.startDate, DateUtil.getCurrentDateTime(), DurationType.Days) + 1;
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
        if (cycleRepository.findFirstByAppUserOrderByStartDateDesc(newCycle.getAppUser()) == null) {
            if (newCycle.getLength() == 0)
                newCycle.setLength(CycleCalculationUtil.DEFAULT_CYCLE_LENGTH);
            if (newCycle.getPeriodLength() == 0)
                newCycle.setPeriodLength(CycleCalculationUtil.DEFAULT_PERIOD_LENGTH);
            if (newCycle.getStartDate() == null)
                newCycle.setStartDate(DateUtil.getCurrentDateTime());
            DateEntry ovulationRelatedDates = CycleCalculationUtil.getFertilityDates(newCycle.getStartDate(), Math.round(newCycle.getLength()));
            Map<StatusName, Integer> lengthMap = CycleCalculationUtil.getStatusLength(Math.round(newCycle.getLength()));
            newCycle.setOvulationDate(ovulationRelatedDates.getEntryDate());
            newCycle.setFertilityStartDate(ovulationRelatedDates.getStartDate());
            newCycle.setFertilityEndDate(ovulationRelatedDates.getEndDate());
            newCycle.setFollicularLength(lengthMap.get(StatusName.follicular));
            newCycle.setLutealLength(lengthMap.get(StatusName.luteal));

            cycleRepository.save(newCycle);
            return "First cycle created";
        } else return "ERROR : User already have a cycle";

    }

    //start new cycle (period start)
    public void startCycle(AppUser appUser, LocalDateTime startDate) {
        Cycle newCycle = new Cycle();
        /*TEST*/ //LocalDateTime startDate = DateUtil.parseDate("31-01-2018 10:00:00");
        LocalDateTime startDateToSave = DateUtil.getCurrentDateTime();
        if (startDate != null)
            startDateToSave = startDate;
        int cycleLength = (int) getAverageCycleLength(appUser);
        DateEntry ovulationRelatedDate = CycleCalculationUtil.getFertilityDates(startDateToSave, cycleLength);
        int periodLength = (int) getAveragePeriodLength(appUser);
        int follicularLength = (int) getAverageFollicularLength(appUser);
        int lutealLength = (int) getAverageLutealLength(appUser);

        newCycle.setStartDate(startDateToSave);
        newCycle.setLength(cycleLength);
        newCycle.setPeriodLength(periodLength);
        newCycle.setOvulationDate(ovulationRelatedDate.getEntryDate());
        newCycle.setFertilityStartDate(ovulationRelatedDate.getStartDate());
        newCycle.setFertilityEndDate(ovulationRelatedDate.getEndDate());
        newCycle.setFollicularLength(follicularLength);
        newCycle.setLutealLength(lutealLength);
        newCycle.setAppUser(appUser);

        cycleRepository.save(newCycle);
    }

    //------------------------------------------
    //---------------Operations---------------
    //end period of current cycle
    public String endCyclePeriod(AppUser appUser, LocalDateTime endDate) {
        Cycle cycle = getCycle(appUser);
        LocalDateTime actualEndDate = DateUtil.getCurrentDateTime();
        if (endDate != null)
            actualEndDate = endDate;
        if (cycle != null && statusService.getStatus(appUser) != null) {
            /*TEST*/ //float periodLength = DateUtil.getDurationBetween(cycle.getStartDate(),DateUtil.parseDate("03-02-2018 15:00:00"), DurationType.Days);
            float periodLength = DateUtil.getDurationBetween(cycle.getStartDate(), actualEndDate, DurationType.Days);

            cycle.setPeriodLength(Math.round(periodLength));

            cycleRepository.save(cycle);
            return "Period Finished";
        }
        return "No Status Or/And Cycle Created! Please call the addFirstCycle Service before adding other data for user of id " + appUser.getId();
    }

    public void updateFollicularLength(AppUser appUser, LocalDateTime date) {
        Cycle cycle = getCycle(appUser);
        LocalDateTime actualDate = DateUtil.getCurrentDateTime();
        if (date != null)
            actualDate = date;
        /*TEST*/ /*float follicularLength = DateUtil.getDurationBetween(
                cycle.getStartDate(),
                DateUtil.parseDate("16-01-2018 08:00:00"),
                DurationType.Days);*/
        float follicularLength = DateUtil.getDurationBetween(
                cycle.getStartDate(),
                actualDate,
                DurationType.Days);
        //if (follicularLength == 0) follicularLength = DEFAULT_FOLLICULAR_LENGTH;
        cycle.setFollicularLength(Math.round(follicularLength));
        cycle.setLutealLength(cycle.getLength() - cycle.getFollicularLength());
        DateEntry ovulationDates = CycleCalculationUtil.getFertilityDates(actualDate);
        cycle.setOvulationDate(ovulationDates.getEntryDate());
        cycle.setFertilityStartDate(ovulationDates.getStartDate());
        cycle.setFertilityEndDate(ovulationDates.getEndDate());

        cycleRepository.save(cycle);
    }

    public void updateLutealLengthOnCycleEnd(AppUser appUser, LocalDateTime date) {
        Cycle cycle = getCycle(appUser);
        LocalDateTime actualDate = DateUtil.getCurrentDateTime();
        if (date != null)
            actualDate = date;
        /*TEST*/ /*float lutealLength = DateUtil.getDurationBetween(
                DateUtil.addNumberOfDaysTo(cycle.getStartDate(), cycle.getFollicularLength()),
                DateUtil.parseDate("31-01-2018 10:00:00"),
                DurationType.Days);*/
        DebugUtil.logError("Doing calculations on cycle end of ID : " + cycle.getId());
        DebugUtil.logError("Old luteal length = " + cycle.getLutealLength());
        DebugUtil.logError("Doing checking on date : " + actualDate);
        DebugUtil.logError("Follicular length = " + cycle.getFollicularLength());
        DebugUtil.logError("Ovulation date (start+fl) = " + DateUtil.addNumberOfDaysTo(cycle.getStartDate(), cycle.getFollicularLength()));
        float lutealLength = DateUtil.getDurationBetween(
                DateUtil.addNumberOfDaysTo(cycle.getStartDate(), cycle.getFollicularLength()),
                actualDate,
                DurationType.Days);
        DebugUtil.logError("New calculated Luteal length = " + lutealLength);
        DebugUtil.logError("Old cycle length = " + cycle.getLength());

        if (lutealLength == 0) lutealLength = cycle.getLength() - cycle.getFollicularLength();
        cycle.setLutealLength(Math.round(lutealLength));
        cycle.setLength(cycle.getLutealLength() + cycle.getFollicularLength());
        DebugUtil.logError("new length = " + cycle.getLength());
        cycleRepository.save(cycle);
    }

    public boolean takeCycleIntoConsideration(Cycle cycle) {
        Cycle cycleToUpdate = cycleRepository.findOne(cycle.getId());
        if (cycleToUpdate != null) {
            cycleToUpdate.setConsiderForCalculation(cycle.isConsiderForCalculation());
            return true;
        } else
            return false;
    }

    public void checkCycleLength(AppUser appUser, LocalDateTime entryDate) {
        Cycle currentCycle = getCycle(appUser);

        int numberOfDaysSinceStartCurrentCycle = Math.round(DateUtil.getDurationBetween(currentCycle.getStartDate(),entryDate,DurationType.Days));
        if (numberOfDaysSinceStartCurrentCycle > currentCycle.getLength()){
            DebugUtil.logError("("+entryDate+")UPDATE-- cycle length " + currentCycle.getLength() + " --> " + numberOfDaysSinceStartCurrentCycle);
            currentCycle.setLength(numberOfDaysSinceStartCurrentCycle);
        }

        cycleRepository.save(currentCycle);
    }

    public void checkCycleLutealLength(AppUser appUser, LocalDateTime entryDate) {
        Cycle currentCycle = getCycle(appUser);
        LocalDateTime ovulationDate = DateUtil.addNumberOfDaysTo(currentCycle.getStartDate(),currentCycle.getFollicularLength());
        int numberOfDaysSinceCurrentOvulation = Math.round(DateUtil.getDurationBetween(ovulationDate,entryDate,DurationType.Days));
        if (numberOfDaysSinceCurrentOvulation > currentCycle.getLutealLength()){
            DebugUtil.logError("Changin luteal length value as " + currentCycle.getLutealLength() +" < " + numberOfDaysSinceCurrentOvulation );
            currentCycle.setLutealLength(numberOfDaysSinceCurrentOvulation);
        }

        cycleRepository.save(currentCycle);
    }

    //------------------------------------------
    //---------------Calculations---------------
    public float getAverageCycleLength(AppUser appUser) {
        float cycleLength;
        if (cycleRepository.findFirstByAppUserOrderByStartDateDesc(appUser) != null) {
            cycleLength = cycleRepository.getAverageCycleLenght(appUser);

        } else {
            cycleLength = 0;
        }
        cycleLength = Math.round(cycleLength);
        return cycleLength;
    }

    public float getAveragePeriodLength(AppUser appUser) {
        float periodLength;
        if (cycleRepository.findFirstByAppUserOrderByStartDateDesc(appUser) != null) {
            periodLength = cycleRepository.getAveragePeriodLenght(appUser);
        } else {
            periodLength = 0;
        }
        periodLength = Math.round(periodLength);
        return periodLength;
    }

    public float getAverageFollicularLength(AppUser appUser) {
        float follicularLength;
        if (cycleRepository.findFirstByAppUserOrderByStartDateDesc(appUser) != null) {
            follicularLength = cycleRepository.getAverageFollicularLength(appUser);
        } else {
            follicularLength = 0;
        }
        follicularLength = Math.round(follicularLength);
        return follicularLength;
    }

    public float getAverageLutealLength(AppUser appUser) {
        float lutealLength;
        if (cycleRepository.findFirstByAppUserOrderByStartDateDesc(appUser) != null) {
            lutealLength = cycleRepository.getAverageLutealLength(appUser);
        } else {
            lutealLength = 0;
        }
        lutealLength = Math.round(lutealLength);
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

