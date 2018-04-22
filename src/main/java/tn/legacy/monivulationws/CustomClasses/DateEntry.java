package tn.legacy.monivulationws.CustomClasses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class DateEntry {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime startDate ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime endDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime entryDate;

    public DateEntry() {
    }

    public DateEntry(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime entryDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.entryDate = entryDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
}
