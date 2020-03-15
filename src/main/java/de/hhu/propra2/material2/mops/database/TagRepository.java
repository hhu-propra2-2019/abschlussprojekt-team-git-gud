package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.database.entities.TagDAO;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<TagDAO, Long> {
}
