package tn.legacy.monivulationws.Util;

import tn.legacy.monivulationws.CustomClasses.DateEntry;
import tn.legacy.monivulationws.enumerations.StatusName;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CycleCalculationUtil {

    //********************* DEFAULTS
    public final static float DEFAULT_CYCLE_LENGTH = 28;
    public final static float DEFAULT_PERIOD_LENGTH = 5;
    public final static int DEFAULT_FERTILITY_START_BEFORE = 5;
    public final static int DEFAULT_FERTILITY_END_AFTER = 1;
    public final static int DEFAULT_LUTEAL_LENGTH = 13;
    public final static int DEFAULT_FOLLICULAR_LENGTH = 15;
    //********************* LIMITS
    public final static float MINIMUM_CYCLE_LENGTH = 18;

    public static DateEntry getFertilityDates(LocalDateTime cycleStartDate, int cycleLength){
        LocalDateTime cycleEndDate = cycleStartDate.plusDays(cycleLength);
        LocalDateTime ovulationDate = cycleEndDate.minusDays(DEFAULT_LUTEAL_LENGTH);
        LocalDateTime fertilityStartDate = ovulationDate.minusDays(DEFAULT_FERTILITY_START_BEFORE);
        LocalDateTime fertilityEndDate = ovulationDate.plusDays(DEFAULT_FERTILITY_END_AFTER);

        return new DateEntry(fertilityStartDate,fertilityEndDate,ovulationDate);
    }

    public static DateEntry getFertilityDates(LocalDateTime ovulationDate){
        LocalDateTime fertilityStartDate = ovulationDate.minusDays(DEFAULT_FERTILITY_START_BEFORE);
        LocalDateTime fertilityEndDate = ovulationDate.plusDays(DEFAULT_FERTILITY_END_AFTER);

        return new DateEntry(fertilityStartDate,fertilityEndDate,ovulationDate);
    }

    public static Map<StatusName, Integer> getStatusLength (int cycleLength){
        Map<StatusName, Integer> lengthMap = new HashMap<>();
        lengthMap.put(StatusName.luteal, DEFAULT_LUTEAL_LENGTH);
        lengthMap.put(StatusName.follicular, cycleLength-DEFAULT_LUTEAL_LENGTH);
        return lengthMap;
    }

}
