package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.Anomaly;

public interface AnomalyRepository extends CrudRepository<Anomaly, Integer> {
}
