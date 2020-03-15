package de.hhu.propra2.material2.mops.database.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserDAO {
    public UserDAO() { }

    public UserDAO(final long id, final String nickname, final String familyName, final String dbName) {
        this.userID = id;
        this.vorname = nickname;
        this.nachname = familyName;
        this.keyCloakName = dbName;
    }
    /**
     * Unique name provided by
     * keycloak.
     */
    @Column(name = "keycloakname")
    private String keyCloakName;
    /**
     * Unique ID from database.
     */
    @Id
    private long userID;
    /**
     * Users first name.
     */
    private String vorname;
    /**
     * Users last name.
     */
    private String nachname;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GruppenbelegungDAO> gruppen;

    /**
     *
     * @param gruppe
     * @param berechtigung
     */
    public void addGroup(final GruppeDAO gruppe, final boolean berechtigung) {
        GruppenbelegungDAO gruppenbelegung = new GruppenbelegungDAO(gruppe, this, berechtigung);
        gruppen.add(gruppenbelegung);
        gruppe.getUser().add(gruppenbelegung);
    }

    /**
     *
     * @param gruppe
     */
    public void removeGruppe(final GruppeDAO gruppe) {
        for (Iterator<GruppenbelegungDAO> iterator = gruppen.iterator();
             iterator.hasNext();) {
            GruppenbelegungDAO gruppenbelegung = iterator.next();

            if (gruppenbelegung.getUser().equals(this) && gruppenbelegung.getGruppe().equals(gruppe)) {
                iterator.remove();
                gruppenbelegung.getGruppe().getUser().remove(gruppenbelegung);
                gruppenbelegung.setGruppe(null);
                gruppenbelegung.setUser(null);
            }
        }
    }

}
