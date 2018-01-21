package tn.legacy.monivulationws.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WeightData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private Date entryDate;
    private float value;
    @ManyToOne
    private User weightUser;

    public WeightData() {
    }

    public WeightData(Date entryDate, float value, User weightUser) {
        this.entryDate = entryDate;
        this.value = value;
        this.weightUser = weightUser;
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

    public User getWeightUser() {
        return weightUser;
    }

    public void setWeightUser(User weightUser) {
        this.weightUser = weightUser;
    }
}
