package de.hhu.propra2.material2.mops.Database;

import de.hhu.propra2.material2.mops.Database.DTOs.TagDTO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends CrudRepository<TagDTO, Long> {

    @Query("SELECT id, name FROM Tags t WHERE t.text IN :texts")
    Iterable<TagDTO> findAllByText(@Param("texts") Iterable<String> texts);
}
