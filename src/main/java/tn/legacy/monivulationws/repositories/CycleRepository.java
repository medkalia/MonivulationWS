package tn.legacy.monivulationws.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;

import java.time.LocalDateTime;

public interface CycleRepository extends CrudRepository<Cycle, Integer> {

    Cycle findByAppUser (AppUser appUser);

    Cycle findByAppUserAndStartDate(AppUser appUser, LocalDateTime startDate);

    @Query("select count (c.id) from Cycle c where c.appUser = ?1")
    float getCycleCount(AppUser appUser);

    @Query("select c from Cycle c where c.startDate = (select max(cc.startDate) from Cycle cc where cc.appUser = ?1 and cc.startDate < ?2)")
    Cycle getFirstCycleBefore (AppUser appUser, LocalDateTime date);

    @Query(nativeQuery = true, value ="select * from cycle c where c.start_date = (select max(cc.start_date) from cycle cc where cc.app_user_id = 1) limit ?1")
    Cycle getLastCycle(int app_user_id);

    @Query("select AVG(c.length) from Cycle c where c.appUser = ?1 and c.pregnancy = null")
    float getAverageCycleLenght(AppUser appUser);

    @Query("select AVG(c.periodLength) from Cycle c where c.appUser = ?1 and c.pregnancy = null")
    float getAveragePeriodLenght(AppUser appUser);

    @Query("select AVG(c.follicularLength) from Cycle c where c.appUser = ?1")
    float getAverageFollicularLength(AppUser appUser);

    @Query("select AVG(c.lutealLength) from Cycle c where c.appUser = ?1 and c.pregnancy = null")
    float getAverageLutealLength(AppUser appUser);


}
