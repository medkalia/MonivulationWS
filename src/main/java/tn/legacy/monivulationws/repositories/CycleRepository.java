package tn.legacy.monivulationws.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.User;

import java.time.LocalDateTime;

public interface CycleRepository extends CrudRepository<Cycle, Integer> {

    Cycle findByUserAndStartDate(User user, LocalDateTime startDate);

    @Query("select c from Cycle c where c.startDate = (select max(cc.startDate) from Cycle cc where cc.user = ?1)")
    Cycle getLastCycle(User user);

    @Query("select AVG(c.length) from Cycle c where c.user = ?1")
    float getAverageCycleLenght(User user);

    @Query("select AVG(c.periodLength) from Cycle c where c.user = ?1")
    float getAveragePeriodLenght(User user);

    @Query("select AVG(c.follicularLength) from Cycle c where c.user = ?1")
    float getAverageFollicularLength(User user);

    @Query("select AVG(c.lutealLength) from Cycle c where c.user = ?1")
    float getAverageLutealLength(User user);
}
