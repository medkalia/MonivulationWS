package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.DailyRecord;
import tn.legacy.monivulationws.entities.Status;
import tn.legacy.monivulationws.entities.User;

import java.time.LocalTime;

public interface StatusRepository extends CrudRepository<Status, Integer> {

    Status findByUser(User user);

}
