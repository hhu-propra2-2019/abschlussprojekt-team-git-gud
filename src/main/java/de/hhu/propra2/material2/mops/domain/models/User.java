package de.hhu.propra2.material2.mops.domain.models;


import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Value
public class User {
    /**
     * Unique ID from database.
     */
    private final long id;

    /**
     * Users first name.
     */
    private final String vorname;

    /**
     * Users last name.
     */
    private final String nachname;

    /**
     * .
     * Unique name provided by
     * keycloak.
     */
    private final String keycloakname;

    /**
     * All participating groups and a mapped Boolean
     * specifying if the user is a group admin.
     */
    private final HashMap<Gruppe, Boolean> belegungUndRechte;

    /**
     * returns the groups the student participates in as a List
     */
    public List<Gruppe> getAllGruppen() {
        return new ArrayList<>(belegungUndRechte.keySet());
    }

    public Gruppe getGruppeById(final long gruppenId) {
        List<Gruppe> gruppen = this.getAllGruppen();
        for (Gruppe gruppe : gruppen) {
            if (gruppe.getId() == gruppenId) {
                return gruppe;
            }
        }
        return new Gruppe(-1, "", new ArrayList<>());
    }
}
