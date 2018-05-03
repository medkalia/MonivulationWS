package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.AppUser;
import tn.legacy.monivulationws.entities.BpmData;

import java.time.LocalDateTime;
import java.util.List;

public interface BpmDataRepository extends CrudRepository<BpmData, Integer> {

    List<BpmData> findAllByAppUserAndAndEntryDateBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate);

    BpmData findFirstByAppUserAndAndEntryDateBetween(AppUser appUser, LocalDateTime startDate, LocalDateTime endDate);

    BpmData findFirstByAppUserAndEntryDate(AppUser appUser, LocalDateTime date);
}
