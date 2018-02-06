package tn.legacy.monivulationws.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class User{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime birthDate;
    private LocalDateTime inscriptionDate;
    @OneToMany(mappedBy = "user")
    private List<TemperatureData> temperatureData;
    @OneToMany(mappedBy = "user")
    private List<WeightData> weightData;
    @OneToMany(mappedBy = "user")
    private List<Cycle> cycleData;

    public User() {
    }

    public User(String email, String password, String firstName, String lastName, LocalDateTime birthDate, LocalDateTime inscriptionDate) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.inscriptionDate = inscriptionDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDateTime getInscriptionDate() {
        return inscriptionDate;
    }

    public void setInscriptionDate(LocalDateTime inscriptionDate) {
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

    public List<Cycle> getCycleData() {
        return cycleData;
    }

    public void setCycleData(List<Cycle> cycleData) {
        this.cycleData = cycleData;
    }
}
