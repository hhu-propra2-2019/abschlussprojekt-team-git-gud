package de.hhu.propra2.material2.mops.DTOs;

import lombok.Data;

import java.util.HashMap;


@Data
public class UserDTO {
    final long id;
    final String vorname;
    final String nachname;
    final String keycloakname;
    final HashMap<GruppeDTO,Boolean> belegungUndRechte; //participating group + boolean if admin

    public UserDTO(int id, String vorname, String nachname, String keycloakname, HashMap<GruppeDTO, Boolean> belegungUndRechte) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.keycloakname = keycloakname;
        this.belegungUndRechte = belegungUndRechte;
    }

}
