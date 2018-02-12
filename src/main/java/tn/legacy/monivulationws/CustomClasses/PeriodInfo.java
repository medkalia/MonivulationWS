package tn.legacy.monivulationws.CustomClasses;

import com.fasterxml.jackson.annotation.JsonFormat;
import tn.legacy.monivulationws.enumerations.PeriodInfoDescription;

import java.time.LocalDateTime;

public class PeriodInfo {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    public LocalDateTime endDate;
    public boolean isDelayed;
    public float delay;
    public PeriodInfoDescription description;

}
