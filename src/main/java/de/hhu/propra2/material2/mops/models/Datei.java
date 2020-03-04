package de.hhu.propra2.material2.mops.models;

import lombok.Value;

import java.util.ArrayList;

@Value
public class Datei {
    private long id;
    private ArrayList<Tag> tags;
}
