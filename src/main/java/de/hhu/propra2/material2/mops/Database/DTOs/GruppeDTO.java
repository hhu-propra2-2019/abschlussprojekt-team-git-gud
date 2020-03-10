package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("Gruppe")
public class GruppeDTO {
    /**
     * Unique ID from database.
     */
    @Id
    private final long gruppeID;
    /**
     * Groups name from database.
     */
    private final String titel;
    /**
     * Groups name from database.
     */
    private final String beschreibung;
}
