package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.TemperatureData;

public interface TemperatureDataRepository extends CrudRepository<TemperatureData, Integer> {
}
