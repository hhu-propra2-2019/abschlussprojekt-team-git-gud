package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.database.entities.UserDAO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDAO, Long> {
    @Query("select u from UserDAO u where u.keyCloakName = ?1")
    UserDAO findByKeyCloakName(String keycloackname);
}
