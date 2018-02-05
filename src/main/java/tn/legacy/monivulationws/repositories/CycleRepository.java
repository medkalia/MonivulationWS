package tn.legacy.monivulationws.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.User;

import java.time.LocalDateTime;

public interface CycleRepository extends CrudRepository<Cycle, Integer> {

    Cycle findByUserAndStartDateBefore(User user, LocalDateTime currentDate);

    Cycle findByUserAndStartDate(User user, LocalDateTime startDate);

    @Query("select AVG(c.length) from Cycle c where c.user = ?1")
    float getAverageCycleLenght(User user);

    @Query("select AVG(c.periodLength) from Cycle c where c.user = ?1")
    float getAveragePeriodLenght(User user);
}
