package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.DailyRecord;
import tn.legacy.monivulationws.entities.User;

public interface DailyRecordRepository extends CrudRepository<DailyRecord, Integer> {
}
