package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.WeightData;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightDataRepository extends CrudRepository<WeightData, Integer> {

    List<WeightData> findAllByAppUserAndAndEntryDateBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate);

    WeightData findFirstByAppUserAndAndEntryDateBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate);

    WeightData findFirstByAppUserAndEntryDate(AppUser appUser, LocalDateTime date);
}
