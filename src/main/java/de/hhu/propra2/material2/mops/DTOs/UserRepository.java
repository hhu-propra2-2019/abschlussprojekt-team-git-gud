package de.hhu.propra2.material2.mops.DTOs;

import de.hhu.propra2.material2.mops.Database.DTOs.UserDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserDTO, Long> {
}
