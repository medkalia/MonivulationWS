package tn.legacy.monivulationws.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import tn.legacy.monivulationws.enumerations.AnomalyDegree;
import tn.legacy.monivulationws.enumerations.AnomalyName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Anomaly {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Enumerated(EnumType.STRING)
    private AnomalyName name;
    private boolean detected = true;
    private boolean confirmed = false;
    @Enumerated(EnumType.STRING)
    private AnomalyDegree anomalyDegree = AnomalyDegree.Problematic ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endDate;
    @Column(columnDefinition = "LONGTEXT")
    private String description ;
    @Column(columnDefinition = "LONGTEXT")
    private String advice;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Cycle cycle ;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.REMOVE)
    private AppUser appUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AnomalyName getName() {
        return name;
    }

    public void setName(AnomalyName name) {
        this.name = name;
    }

    public boolean isDetected() {
        return detected;
    }

    public void setDetected(boolean detected) {
        this.detected = detected;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
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

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public AnomalyDegree getAnomalyDegree() {
        return anomalyDegree;
    }

    public void setAnomalyDegree(AnomalyDegree anomalyDegree) {
        this.anomalyDegree = anomalyDegree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }
}
