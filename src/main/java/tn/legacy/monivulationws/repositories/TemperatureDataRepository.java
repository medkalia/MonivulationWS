package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.TemperatureData;

import java.time.LocalDateTime;
import java.util.List;

public interface TemperatureDataRepository extends CrudRepository<TemperatureData, Integer> {

    List<TemperatureData> findAllByAppUserAndAndEntryDateBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate);

    TemperatureData findFirstByAppUserAndAndEntryDateBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate);

    TemperatureData findFirstByAppUserAndEntryDate(AppUser appUser, LocalDateTime date);
}
