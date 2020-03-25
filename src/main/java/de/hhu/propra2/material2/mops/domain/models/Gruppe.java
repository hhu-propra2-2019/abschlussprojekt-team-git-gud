package de.hhu.propra2.material2.mops.domain.models;

import lombok.Value;

import java.util.List;

@Value
public class Gruppe {
    /**
     * Unique ID from database.
     */
    private String id;
    /**
     * Groups name.
     */
    private String name;
    /**
     * List of related files.
     */
    private List<Datei> dateien;
}
