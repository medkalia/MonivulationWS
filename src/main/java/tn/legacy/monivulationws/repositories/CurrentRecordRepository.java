package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.CurrentRecord;

import java.util.Date;

public interface CurrentRecordRepository extends CrudRepository<CurrentRecord, Integer> {
    public CurrentRecord findByUserIdAndRecordDate(int UserId, Date recordDate);
}
