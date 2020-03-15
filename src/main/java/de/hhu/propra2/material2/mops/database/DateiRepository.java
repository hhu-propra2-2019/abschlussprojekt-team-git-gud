package de.hhu.propra2.material2.mops.database;

import de.hhu.propra2.material2.mops.database.entities.DateiDAO;
import org.springframework.data.repository.CrudRepository;

public interface DateiRepository extends CrudRepository<DateiDAO, Long> {
}
