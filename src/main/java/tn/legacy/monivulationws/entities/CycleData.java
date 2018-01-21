package tn.legacy.monivulationws.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CycleData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private Date startDate;
    private int length;
    private int periodLength;
    @ManyToOne
    private User cycleUser;

    public CycleData() {
    }

    public CycleData(Date startDate, int length, int periodLength, User cycleUser) {
        this.startDate = startDate;
        this.length = length;
        this.periodLength = periodLength;
        this.cycleUser = cycleUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(int periodLength) {
        this.periodLength = periodLength;
    }

    public User getCycleUser() {
        return cycleUser;
    }

    public void setCycleUser(User cycleUser) {
        this.cycleUser = cycleUser;
    }
}
