package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserDTO, Long> {
    @Query("Select * from User u where u.keycloackname = keyclockname")
    UserDTO findByKeycloakname(@Param("keycloackname") String keycloackname);
}
