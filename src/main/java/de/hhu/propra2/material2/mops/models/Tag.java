package de.hhu.propra2.material2.mops.models;

import de.hhu.propra2.material2.mops.DTOs.DateiDTO;
import lombok.Value;

import java.util.List;

@Value
public class Tag {
    private long id;
    private String text;
}
