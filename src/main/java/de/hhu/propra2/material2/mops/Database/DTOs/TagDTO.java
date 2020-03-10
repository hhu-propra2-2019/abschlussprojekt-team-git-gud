package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
    /**
     * Unique ID from database.
     */
    private final long id;
    /**
     * The tags text that will be shown
     * and can be searched for.
     */
    private final String text;

    /**
     * Standard AllArgsConstructor for import from database.
     *
     * @param idArg
     * @param textArg
     * @param dateienArg
     */
    public TagDTO(final long idArg,
                  final String textArg,
                  final List<DateiDTO> dateienArg) {
        this.id = idArg;
        this.text = textArg;
    }

    /**
     * Constructor without id for
     * saving to database.
     *
     * @param textArg
     * @param dateienArg
     */
    public TagDTO(final String textArg,
                  final List<DateiDTO> dateienArg) {
        this.id = 0;
        this.text = textArg;
    }

}
