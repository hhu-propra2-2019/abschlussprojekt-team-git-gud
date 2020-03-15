package de.hhu.propra2.material2.mops.domain.models;

import lombok.Value;

import java.util.List;

@Value
public class Gruppe {
    private long id;
    private String name;
    private List<Datei> dateien;
}
