package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.Cycle;
import tn.legacy.monivulationws.entities.Pregnancy;
import tn.legacy.monivulationws.entities.Status;
import tn.legacy.monivulationws.entities.User;

public interface PregnancyRepository extends CrudRepository<Pregnancy, Integer> {

    Pregnancy findByCycle(Cycle cycle);

    Pregnancy findByCycleAndIsFinished(Cycle cycle, boolean isFinished);

}
