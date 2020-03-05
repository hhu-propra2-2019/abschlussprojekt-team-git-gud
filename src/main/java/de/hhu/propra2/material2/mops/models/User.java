package de.hhu.propra2.material2.mops.models;


import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class User {
    private long id;
    private String vorname;
    private String nachname;
    private String keycloakname;
    private HashMap<Gruppe,Boolean> belegungUndRechte;

    public List<Gruppe> getAllGruppen(){
        return new ArrayList<Gruppe>(belegungUndRechte.keySet());
    }
}
