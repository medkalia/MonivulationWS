package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
