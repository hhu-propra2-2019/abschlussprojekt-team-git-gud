package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Data;

//@Table("Tagnutzung")
@Data
public class TagnutzungDTO {
    private Long tagID;

    public TagnutzungDTO(final Long tag) {
        this.tagID = tag;
    }
}
