package de.hhu.propra2.material2.mops.models;

import lombok.Value;

import java.util.List;

@Value
public class Gruppe {
    private long id;
    private String name;
    //final List<User> users; shouldn't be necessary because from a group we never have to go back to the user
    private List<Datei> dateien;
}
