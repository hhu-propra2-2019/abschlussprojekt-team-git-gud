package de.hhu.propra2.material2.mops.Database.DTOs;

import org.springframework.data.relational.core.mapping.Table;

@Table("Gruppenbelegung")
public class GruppenbelegungDTO {
    private Long gruppeID;

    public GruppenbelegungDTO(final Long gID) {
        this.gruppeID = gID;
    }

}
