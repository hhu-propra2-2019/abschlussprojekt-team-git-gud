package de.hhu.propra2.material2.mops.domain.models;


import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Value
public class User {

    private final long id;
    private final String vorname;
    private final String nachname;
    private final String keycloakname;
    private final HashMap<Gruppe, Boolean> belegungUndRechte;

    public List<Gruppe> getAllGruppen() {
       return new ArrayList<>(belegungUndRechte.keySet());
    }
}
