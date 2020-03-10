package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;


@Data
@Table("User")
public class UserDTO {
    /**
     * Unique ID from database.
     */
    @Id
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
    private Set<GruppenbelegungDTO> gruppen = new HashSet<>();

    /**
     * Standard AllArgsConstructor for import from the Database.
     *
     * @param idArg
     * @param vornameArg
     * @param nachnameArg
     * @param keycloaknameArg
     */
    public UserDTO(final long idArg,
                   final  String vornameArg,
                   final String nachnameArg,
                   final String keycloaknameArg) {
        this.id = idArg;
        this.vorname = vornameArg;
        this.nachname = nachnameArg;
        this.keycloakname = keycloaknameArg;
    }

    /**
     * test.
     * @param gruppe
     */
    public void addGruppe(final GruppeDTO gruppe) {
        this.gruppen.add(new GruppenbelegungDTO(gruppe.getGruppeID()));
    }

}
