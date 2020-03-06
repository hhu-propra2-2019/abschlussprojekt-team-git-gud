package de.hhu.propra2.material2.mops.models;

import lombok.Value;

@Value
public class Tag {
    /**
     * Unique ID from database.
     */
    private final long id;
    /**
     * The tags text that will be shown
     * and can be searched for.
     */
    private final String text;
}
