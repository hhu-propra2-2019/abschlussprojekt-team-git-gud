package de.hhu.propra2.material2.mops.models;

import lombok.Value;

import java.util.List;

@Value
public class Gruppe {
    final long id;
    final String name;
    //final List<User> users; shouldn't be necessary because from a group we never have to go back to the user
    final List<Datei> dateien;
}
