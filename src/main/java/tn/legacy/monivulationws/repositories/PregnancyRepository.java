package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.Pregnancy;

public interface PregnancyRepository extends CrudRepository<Pregnancy, Integer> {

    Pregnancy findByCycle(Cycle cycle);

    Pregnancy findByCycleAndIsFinished(Cycle cycle, boolean isFinished);

}
