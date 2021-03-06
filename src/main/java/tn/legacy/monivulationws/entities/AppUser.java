package tn.legacy.monivulationws.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name="email", unique=true)
    private String email;
    @Column(unique=true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime birthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime inscriptionDate;
    /*@OneToMany(mappedBy = "appUser")
    private List<TemperatureData> temperatureData;
    @OneToMany(mappedBy = "appUser")
    private List<WeightData> weightData;
    @OneToMany(mappedBy = "appUser")
    private List<Cycle> cycleData;*/
    @OneToOne(mappedBy = "appUser")
    @JsonIgnore
    private Status status;

    public AppUser() {
    }

    public AppUser(String email, String username, String password, String firstName, String lastName, LocalDateTime birthDate, LocalDateTime inscriptionDate, List<TemperatureData> temperatureData, List<WeightData> weightData, List<Cycle> cycleData, Status status) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.inscriptionDate = inscriptionDate;
        /*this.temperatureData = temperatureData;
        this.weightData = weightData;
        this.cycleData = cycleData;*/
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    /*public List<TemperatureData> getTemperatureData() {
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
    }*/

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

