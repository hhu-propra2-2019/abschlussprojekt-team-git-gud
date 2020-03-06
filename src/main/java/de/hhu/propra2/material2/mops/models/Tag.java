package de.hhu.propra2.material2.mops.models;

import de.hhu.propra2.material2.mops.DTOs.DateiDTO;
import lombok.Value;

import java.util.List;

@Value
public class Tag {
    /**
     * Unique ID from database.
     */
    private final int id;
    /**
     * The tags text that will be shown
     * and can be searched for.
     */
    private final String text;
}
