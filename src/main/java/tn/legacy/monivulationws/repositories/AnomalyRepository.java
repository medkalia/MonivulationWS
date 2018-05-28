package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.Anomaly;
import tn.legacy.monivulationws.entities.AppUser;

import java.util.List;

public interface AnomalyRepository extends CrudRepository<Anomaly, Integer> {

    List<Anomaly> getAllByAppUser (AppUser appUser);
}
