package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.Status;
import tn.legacy.monivulationws.entities.AppUser;

public interface StatusRepository extends CrudRepository<Status, Integer> {

    Status findByAppUser(AppUser appUser);

}
