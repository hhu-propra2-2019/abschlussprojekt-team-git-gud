package de.hhu.propra2.material2.mops.models;

import lombok.Value;

import java.util.ArrayList;

@Value
public class Gruppe {
    private Long id;
    private ArrayList<Datei> material;
}
