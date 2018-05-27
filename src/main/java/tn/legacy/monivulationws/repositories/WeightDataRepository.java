package tn.legacy.monivulationws.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.WeightData;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightDataRepository extends CrudRepository<WeightData, Integer> {

    List<WeightData> findAllByAppUserAndAndEntryDateBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate);

    WeightData findFirstByAppUserAndAndEntryDateBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate);

    WeightData findFirstByAppUserAndEntryDate(AppUser appUser, LocalDateTime date);

    @Query("select w from WeightData w where w.entryDate = (select max(ww.entryDate) from WeightData ww where ww.appUser = ?1 and ww.entryDate <= ?2 )")
    List<WeightData> getClosestWeightData (AppUser appUser, LocalDateTime date);

    WeightData findFirstByAppUserOrderByIdDesc (AppUser appUser);
}
