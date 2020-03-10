package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Table("Gruppe")
public class GruppeDTO {
    /**
     * Unique ID from database.
     */
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
