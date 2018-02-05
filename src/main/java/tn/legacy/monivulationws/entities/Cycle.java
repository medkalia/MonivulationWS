package tn.legacy.monivulationws.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    private LocalDateTime fertilityStartDate;
    private LocalDateTime fertilityEndDate;

    @ManyToOne
    private User user;

    public Cycle() {
    }

    public Cycle(LocalDateTime startDate, float length, float periodLength, LocalDateTime fertilityStartDate, LocalDateTime fertilityEndDate) {
        this.startDate = startDate;
        this.length = length;
        this.periodLength = periodLength;
        this.fertilityStartDate = fertilityStartDate;
        this.fertilityEndDate = fertilityEndDate;
    }

    public Cycle(LocalDateTime startDate, float length, float periodLength, LocalDateTime fertilityStartDate, LocalDateTime fertilityEndDate, User user) {
        this.startDate = startDate;
        this.length = length;
        this.periodLength = periodLength;
        this.fertilityStartDate = fertilityStartDate;
        this.fertilityEndDate = fertilityEndDate;
        this.user = user;
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

    public LocalDateTime getFertilityStartDate() {
        return fertilityStartDate;
    }

    public void setFertilityStartDate(LocalDateTime fertilityStartDate) {
        this.fertilityStartDate = fertilityStartDate;
    }

    public LocalDateTime getFertilityEndDate() {
        return fertilityEndDate;
    }

    public void setFertilityEndDate(LocalDateTime fertilityEndDate) {
        this.fertilityEndDate = fertilityEndDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
