package de.hhu.propra2.material2.mops.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class TagDTO {
        final long id;
        final String text;
        final List<DateiDTO> dateien;

    public TagDTO(int id, String text, List<DateiDTO> dateien) {
        this.id = id;
        this.text = text;
        this.dateien = dateien;
    }
}
