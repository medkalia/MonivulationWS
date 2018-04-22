package tn.legacy.monivulationws.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import tn.legacy.monivulationws.enumerations.AnomalyName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Anomaly {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private AnomalyName name;
    private boolean detected = true;
    private boolean confirmed = false;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime endDate;

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
}
