package de.hhu.propra2.material2.mops.models;

import lombok.Value;

import java.util.ArrayList;

@Value
public class User {
    private long nr;
    private ArrayList<Gruppe> gruppen;
}
