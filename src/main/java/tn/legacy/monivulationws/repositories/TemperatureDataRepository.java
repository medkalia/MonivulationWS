package tn.legacy.monivulationws.repositories;

import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.TemperatureData;
import tn.legacy.monivulationws.entities.User;

import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.List;

public interface TemperatureDataRepository extends CrudRepository<TemperatureData, Integer> {

    List<TemperatureData>  findAllByUserAndAndEntryDateBetween (User user, LocalDateTime startDate, LocalDateTime endDate);

    TemperatureData  findFirstByUserAndAndEntryDateBetween (User user, LocalDateTime startDate, LocalDateTime endDate);

    TemperatureData findFirstByUserAndEntryDate(User user,LocalDateTime date);
}
