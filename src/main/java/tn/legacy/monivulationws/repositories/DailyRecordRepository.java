package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.DailyRecord;

import java.util.Date;

public interface DailyRecordRepository extends CrudRepository<DailyRecord, Integer> {
    public DailyRecord findByUserIdAndRecordDate(int UserId, Date recordDate);
}
