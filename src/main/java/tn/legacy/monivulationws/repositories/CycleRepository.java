package tn.legacy.monivulationws.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.Cycle;

import java.time.LocalDateTime;
import java.util.List;

public interface CycleRepository extends CrudRepository<Cycle, Integer> {

    Cycle findFirstByAppUserOrderByStartDateDesc(AppUser appUser);

    List<Cycle> findAllByAppUser (AppUser appUser);

    Cycle findFirstByAppUserAndStartDate(AppUser appUser, LocalDateTime startDate);

    @Query("select count (c.id) from Cycle c where c.appUser = ?1")
    float getCycleCount(AppUser appUser);

    @Query("select c from Cycle c where c.startDate = (select max(cc.startDate) from Cycle cc where cc.appUser = ?1 and cc.startDate < ?2 )")
    List<Cycle> getFirstCycleBefore (AppUser appUser, LocalDateTime date);

    @Query("select c from Cycle c where c.startDate = (select max(cc.startDate) from Cycle cc where cc.appUser = ?1 and cc.startDate <= ?2)")
    List<Cycle> getFirstCycleBeforeOrAt (AppUser appUser, LocalDateTime date);

    @Query(value ="select c from Cycle c where c.startDate = (select max(cc.startDate) from Cycle cc where cc.appUser = :targetUser)")
    List<Cycle> getLastCycle(@Param("targetUser") AppUser appUser);

    @Query("select AVG(c.length) from Cycle c where c.appUser = ?1 and c.pregnancy = null and c.considerForCalculation = true")
    float getAverageCycleLenght(AppUser appUser);

    @Query("select AVG(c.periodLength) from Cycle c where c.appUser = ?1 and c.pregnancy = null and c.considerForCalculation = true")
    float getAveragePeriodLenght(AppUser appUser);

    @Query("select AVG(c.follicularLength) from Cycle c where c.appUser = ?1 and c.considerForCalculation = true")
    float getAverageFollicularLength(AppUser appUser);

    @Query("select AVG(c.lutealLength) from Cycle c where c.appUser = ?1 and c.pregnancy = null and c.considerForCalculation = true")
    float getAverageLutealLength(AppUser appUser);


}
