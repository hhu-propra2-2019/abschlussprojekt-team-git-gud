package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    /**
     * Unique ID from database.
     */
    private final int id;
    /**
     * The tags text that will be shown
     * and can be searched for.
     */
    private final String text;
    /**
     * Related files to this tag.
     */
    private final List<DateiDTO> dateien;

    /**
     * Standard AllArgsConstructor for import from database.
     *
     * @param idArg
     * @param textArg
     * @param dateienArg
     */
    public TagDTO(final int idArg,
                  final String textArg,
                  final List<DateiDTO> dateienArg) {
        this.id = idArg;
        this.text = textArg;
        this.dateien = dateienArg;
    }
}
