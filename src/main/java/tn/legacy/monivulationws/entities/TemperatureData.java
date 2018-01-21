package tn.legacy.monivulationws.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TemperatureData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private Date entryDate;
    private float value;
    @ManyToOne
    private User temperatureUser;

    public TemperatureData() {
    }

    public TemperatureData(Date entryDate, float value, User temperatureUser) {
        this.entryDate = entryDate;
        this.value = value;
        this.temperatureUser = temperatureUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public User getTemperatureUser() {
        return temperatureUser;
    }

    public void setTemperatureUser(User temperatureUser) {
        this.temperatureUser = temperatureUser;
    }
}
