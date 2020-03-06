package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class GruppeDTO {
    /**
     * Unique ID from database.
     */
    private final int id;
    /**
     * Groups name from database.
     */
    private final String name;
    /**
     * List of participating users from database.
     */
    private final List<UserDTO> users;
    /**
     * List of related files from database.
     */
    private final List<DateiDTO> dateien;

    /**
     * Standard AllArgsConstructor for import from database.
     *
     * @param idArg
     * @param nameArg
     * @param userArgs
     * @param dateiArgs
     */
    public GruppeDTO(final int idArg,
                     final String nameArg,
                     final List<UserDTO> userArgs,
                     final List<DateiDTO> dateiArgs) {
        this.id = idArg;
        this.name = nameArg;
        this.users = userArgs;
        this.dateien = dateiArgs;
    }
}
