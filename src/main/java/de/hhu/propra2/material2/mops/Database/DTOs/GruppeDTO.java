package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class GruppeDTO {
    /**
     * Unique ID from database.
     */
    private final String id;
    /**
     * Groups name from database.
     */
    private final String name;
    /**
     * Groups description from database.
     */
    private final String description;
    /**
     * List of related files from database.
     */
    @Getter(AccessLevel.PUBLIC)
    private final List<DateiDTO> dateien;

    /**
     * Standard AllArgsConstructor for import from database.
     *  @param idArg
     * @param nameArg
     * @param descriptionArg
     * @param dateiArgs
     */
    public GruppeDTO(final String idArg,
                     final String nameArg,
                     final String descriptionArg,
                     final List<DateiDTO> dateiArgs) {
        this.id = idArg;
        this.name = nameArg;
        this.description = descriptionArg;
        this.dateien = dateiArgs;
    }

    @SuppressWarnings({"checkstyle:FinalParameters"})
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o.getClass() == this.getClass()) {
            GruppeDTO gruppeDTO = (GruppeDTO) o;
            return gruppeDTO.hashCode() == this.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
