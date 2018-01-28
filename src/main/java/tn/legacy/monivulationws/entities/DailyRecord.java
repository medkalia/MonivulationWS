package tn.legacy.monivulationws.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class DailyRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private Date recordDate;
    private float temperature;
    private float weight;
    private float bpm;
    @ManyToOne
    private User user;

    public DailyRecord() {
    }

    public DailyRecord(Date recordDate, float temperature, float weight) {
        this.recordDate = recordDate;
        this.temperature = temperature;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
