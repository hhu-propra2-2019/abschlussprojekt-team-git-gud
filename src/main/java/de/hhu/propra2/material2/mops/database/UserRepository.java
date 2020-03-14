package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.database.entities.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("Select * from User u where u.keycloackname = keyclockname")
    User findByKeyCloakName(@Param("keycloackname") String keycloackname);
}
