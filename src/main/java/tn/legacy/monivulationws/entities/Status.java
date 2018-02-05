package tn.legacy.monivulationws.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import tn.legacy.monivulationws.enumerations.StatusName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Status {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDate;
    @Enumerated(EnumType.STRING)
    private StatusName name;
    private boolean isConfirmed;

    @OneToOne
    private User user;

    public Status() {
    }

    public Status(LocalDateTime startDate, StatusName name, boolean isConfirmed) {
        this.startDate = startDate;
        this.name = name;
        this.isConfirmed = isConfirmed;
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

    public StatusName getName() {
        return name;
    }

    public void setName(StatusName name) {
        this.name = name;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}