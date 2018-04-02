package tn.legacy.monivulationws.CustomClasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import tn.legacy.monivulationws.enumerations.StatusName;

import java.time.LocalDateTime;

public class CycleInfo {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime endDate;
    public float length;
    public float periodLength;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime fertilityStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime fertilityEndDate;
    public float follicularLength;
    public float lutealLength;
    public StatusName currentStatus;
    public int currentDayOfCycle;
}
