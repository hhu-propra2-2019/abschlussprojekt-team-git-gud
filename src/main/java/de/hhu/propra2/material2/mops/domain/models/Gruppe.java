package de.hhu.propra2.material2.mops.domain.models;

import lombok.Value;

import java.util.List;

@Value
public class Gruppe {
    /**
     * Unique ID from database.
     */
    private long id;
    /**
     * Groups name.
     */
    private String name;
    //final List<User> users; shouldn't be necessary
    // because from a group we never have to go back to the user
    /**
     * List of related files.
     */
    private List<Datei> dateien;
}
