package de.hhu.propra2.material2.mops.database.DTOs;

import lombok.Data;


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
     */
    public TagDTO(final long idArg,
                  final String textArg) {
        this.id = idArg;
        this.text = textArg;
    }

    /**
     * Constructor without id for
     * saving to database.
     *
     * @param textArg
     */
    public TagDTO(final String textArg) {
        this.id = 0;
        this.text = textArg;
    }

}
