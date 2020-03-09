package de.hhu.propra2.material2.mops.DTOs;

import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<TagDTO, Long> {
}
