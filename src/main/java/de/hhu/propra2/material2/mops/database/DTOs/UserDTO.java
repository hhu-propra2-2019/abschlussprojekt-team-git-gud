package de.hhu.propra2.material2.mops.database.DTOs;

import lombok.Data;
import java.util.HashMap;


@Data
public class UserDTO {
    /**
     * Unique ID from database.
     */
    private final long id;

    /**
     * Users first name.
     */
    private final String vorname;

    /**
     * Users last name.
     */
    private final String nachname;

    /**
     * Unique name provided by
     * keycloak.
     */
    private final String keycloakname;

    /**
     * All participating groups and a mapped Boolean
     * if the user is a group admin.
     */
    private final HashMap<GruppeDTO, Boolean> belegungUndRechte;

    /**
     * Standard AllArgsConstructor for import from the database.
     *
     * @param idArg
     * @param vornameArg
     * @param nachnameArg
     * @param keycloaknameArg
     * @param belegungUndRechteArg
     */
    public UserDTO(final long idArg,
                   final  String vornameArg,
                   final String nachnameArg,
                   final String keycloaknameArg,
                   final HashMap<GruppeDTO, Boolean> belegungUndRechteArg) {
        this.id = idArg;
        this.vorname = vornameArg;
        this.nachname = nachnameArg;
        this.keycloakname = keycloaknameArg;
        this.belegungUndRechte = belegungUndRechteArg;
    }

    /**
     * @param vornameArg
     * @param nachnameArg
     * @param keycloaknameArg
     * @param belegungUndRechteArg
     */
    public UserDTO(final  String vornameArg,
                   final String nachnameArg,
                   final String keycloaknameArg,
                   final HashMap<GruppeDTO, Boolean> belegungUndRechteArg) {
        this.id = 0;
        this.vorname = vornameArg;
        this.nachname = nachnameArg;
        this.keycloakname = keycloaknameArg;
        this.belegungUndRechte = belegungUndRechteArg;
    }
}
