package de.hhu.propra2.material2.mops.database.DTOs;

import de.hhu.propra2.material2.mops.database.Repository;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public final class GruppeDTO {
    /**
     * Unique ID from database.
     */
    @Getter
    private final long id;
    /**
     * Groups name from database.
     */
    @Getter
    private final String name;
    /**
     * Groups description from database.
     */
    @Getter
    private final String description;
    /**
     * LinkedList of related files from database.
     */
    @Setter
    private List<DateiDTO> dateien;
    /**
     * Repository for lazy loading Files on demand only.
     * Repository also has cache capabilities.
     */
    private final Repository repository;

    /**
     * Standard AllArgsConstructor for saving to database.
     *
     * @param idArg
     * @param nameArg
     * @param descriptionArg
     * @param dateiArgs
     */
    public GruppeDTO(final long idArg,
                     final String nameArg,
                     final String descriptionArg,
                     final List<DateiDTO> dateiArgs) {
        this.id = idArg;
        this.name = nameArg;
        this.description = descriptionArg;
        this.dateien = dateiArgs;
        this.repository = null;
    }

    /**
     * Standard AllArgsConstructor for importing from database.
     *
     * @param idArg
     * @param nameArg
     * @param descriptionArg
     * @param dateiArgs
     */
    public GruppeDTO(final long idArg,
                     final String nameArg,
                     final String descriptionArg,
                     final LinkedList<DateiDTO> dateiArgs,
                     final Repository repositoryArg) {
        this.id = idArg;
        this.name = nameArg;
        this.description = descriptionArg;
        this.dateien = dateiArgs;
        this.repository = repositoryArg;
    }

    /**
     * Getter of a LinkedList of Datei for loading from repository.
     * Repository caches Files as needed.
     *
     * Returns dateien if dateien is not empty, repository is empty or
     * the database throws an error. Saves a LinkedList from
     * the database if dateien is empty and saves it locally.
     *
     * @return
     */
    public List<DateiDTO> getDateien() {
        if (!(dateien.isEmpty())) {
            return dateien;
        }

        if (repository == null) {
            return dateien;
        }

        try {
            dateien = repository.findAllDateiByGruppeDTO(this);
            return dateien;
        } catch (SQLException e) {
            return dateien;
        }
    }

    /**
     * Custom equals function for HashMaps etc.
     * @param o
     * @return
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }

        if (o.getClass() == this.getClass()) {
            GruppeDTO gruppeDTO = (GruppeDTO) o;
            return gruppeDTO.getId() == this.getId();
        }
        return false;
    }

    /**
     * Function that only exists to make checkstyle happy.
     * @return
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Function checks if dateien is empty.
     * @return
     */
    public boolean hasNoFiles() {
        return dateien.isEmpty();
    }
}
