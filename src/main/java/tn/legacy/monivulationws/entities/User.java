package tn.legacy.monivulationws.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class User{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Date inscriptionDate;
    @OneToMany(mappedBy = "temperatureUser")
    private List<TemperatureData> temperatureData;
    @OneToMany(mappedBy = "weightUser")
    private List<WeightData> weightData;
    @OneToMany(mappedBy = "cycleUser")
    private List<CycleData> cycleData;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, Date birthDate, Date inscriptionDate, List<TemperatureData> temperatureData, List<WeightData> weightData, List<CycleData> cycleData) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.inscriptionDate = inscriptionDate;
        this.temperatureData = temperatureData;
        this.weightData = weightData;
        this.cycleData = cycleData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getInscriptionDate() {
        return inscriptionDate;
    }

    public void setInscriptionDate(Date inscriptionDate) {
        this.inscriptionDate = inscriptionDate;
    }

    public List<TemperatureData> getTemperatureData() {
        return temperatureData;
    }

    public void setTemperatureData(List<TemperatureData> temperatureData) {
        this.temperatureData = temperatureData;
    }

    public List<WeightData> getWeightData() {
        return weightData;
    }

    public void setWeightData(List<WeightData> weightData) {
        this.weightData = weightData;
    }

    public List<CycleData> getCycleData() {
        return cycleData;
    }

    public void setCycleData(List<CycleData> cycleData) {
        this.cycleData = cycleData;
    }
}
