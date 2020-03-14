package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.database.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where u.keyCloakName = ?1")
    User findByKeyCloakName(String keycloackname);
}
