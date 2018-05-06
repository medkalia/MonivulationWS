package tn.legacy.monivulationws.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Cycle {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDate;
    private float length;
    private float periodLength;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime ovulationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fertilityStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime fertilityEndDate;
    private float follicularLength;
    private float lutealLength;
    private boolean considerForCalculation = true;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private AppUser appUser;

    @OneToOne
    private Pregnancy pregnancy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Transient
    private LocalDateTime entryDate;

    public Cycle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(float periodLength) {
        this.periodLength = periodLength;
    }

    public LocalDateTime getOvulationDate() {
        return ovulationDate;
    }

    public void setOvulationDate(LocalDateTime ovulationDate) {
        this.ovulationDate = ovulationDate;
    }

    public LocalDateTime getFertilityStartDate() {
        return fertilityStartDate;
    }

    public void setFertilityStartDate(LocalDateTime fertilityStartDate) {
        this.fertilityStartDate = fertilityStartDate;
    }

    public LocalDateTime getFertilityEndDate() {
        return fertilityEndDate;
    }

    public float getFollicularLength() {
        return follicularLength;
    }

    public void setFollicularLength(float follicularLength) {
        this.follicularLength = follicularLength;
    }

    public float getLutealLength() {
        return lutealLength;
    }

    public void setLutealLength(float lutealLength) {
        this.lutealLength = lutealLength;
    }

    public void setFertilityEndDate(LocalDateTime fertilityEndDate) {
        this.fertilityEndDate = fertilityEndDate;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Pregnancy getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(Pregnancy pregnancy) {
        this.pregnancy = pregnancy;
    }

    public boolean isConsiderForCalculation() {
        return considerForCalculation;
    }

    public void setConsiderForCalculation(boolean considerForCalculation) {
        this.considerForCalculation = considerForCalculation;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }
}
