package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class GruppeDTO {
    /**
     * Unique ID from database.
     */
    private final long id;
    /**
     * Groups name from database.
     */
    private final String name;
    /**
     * Groups description from database.
     */
    private final String description;
    /**
     * List of participating users from database.
     */
    private final List<UserDTO> users;
    /**
     * List of related files from database.
     */
    @Getter(AccessLevel.PUBLIC)
    private final List<DateiDTO> dateien;

    /**
     * Standard AllArgsConstructor for import from database.
     *  @param idArg
     * @param nameArg
     * @param description
     * @param userArgs
     * @param dateiArgs
     */
    public GruppeDTO(final long idArg,
                     final String nameArg,
                     String description,
                     final List<UserDTO> userArgs,
                     final List<DateiDTO> dateiArgs) {
        this.id = idArg;
        this.name = nameArg;
        this.description = description;
        this.users = userArgs;
        this.dateien = dateiArgs;
    }
}
