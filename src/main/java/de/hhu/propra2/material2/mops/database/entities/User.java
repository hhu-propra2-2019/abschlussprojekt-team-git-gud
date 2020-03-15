package de.hhu.propra2.material2.mops.database.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    public User() { }

    public User(final long id, final String nickname, final String familyName, final String dbName) {
        this.userID = id;
        this.vorname = nickname;
        this.nachname = familyName;
        this.keyCloakName = dbName;
    }
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
    /**
     * Unique name provided by
     * keycloak.
     */
    @Column(name = "keycloakname")
    private String keyCloakName;

    @ManyToMany
    @JoinTable(name = "gruppenbelegung",
               joinColumns = {@JoinColumn(name = "userid")}, inverseJoinColumns = {@JoinColumn(name = "gruppeid")})
    private List<Gruppe> gruppen;
}
