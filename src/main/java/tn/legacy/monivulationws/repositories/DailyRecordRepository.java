package tn.legacy.monivulationws.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.DailyRecord;
import tn.legacy.monivulationws.entities.User;

import java.time.LocalTime;
import java.util.Date;

public interface DailyRecordRepository extends CrudRepository<DailyRecord, Integer> {


    DailyRecord findByUserAndRecordDate(User user, LocalTime recordDate);

}
