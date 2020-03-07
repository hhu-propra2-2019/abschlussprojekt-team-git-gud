package de.hhu.propra2.material2.mops.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class GruppeDTO {
    final int id;
    final String name;
    final List<UserDTO> users;
    final List<DateiDTO> dateien;

    public GruppeDTO(int id, String name, List<UserDTO> users, List<DateiDTO> dateien) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.dateien = dateien;
    }
}
