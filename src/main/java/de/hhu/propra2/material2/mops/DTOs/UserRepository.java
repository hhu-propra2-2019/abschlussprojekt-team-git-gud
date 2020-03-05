package de.hhu.propra2.material2.mops.DTOs;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserDTO,Long> {
    UserDTO findFirstByKeycloakname(String name);
}
