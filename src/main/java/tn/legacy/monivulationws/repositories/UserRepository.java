package tn.legacy.monivulationws.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.legacy.monivulationws.entities.AppUser;

public interface UserRepository extends CrudRepository<AppUser, Integer> {
    public AppUser findAppUserByEmail(String email);
}
