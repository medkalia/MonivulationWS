package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.User;
import tn.legacy.monivulationws.entities.WeightData;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightDataRepository extends CrudRepository<WeightData, Integer> {

    List<WeightData> findAllByUserAndAndEntryDateBetween (User user, LocalDateTime startDate, LocalDateTime endDate);

    WeightData  findFirstByUserAndAndEntryDateBetween (User user, LocalDateTime startDate, LocalDateTime endDate);

    WeightData findFirstByUserAndEntryDate(User user,LocalDateTime date);
}
