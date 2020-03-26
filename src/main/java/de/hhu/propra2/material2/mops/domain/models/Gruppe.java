package de.hhu.propra2.material2.mops.domain.models;

import lombok.Value;

import java.util.List;

@Value
public class Gruppe {
    /**
     * Unique ID from database.
     */
    private long id;
    /**
     * Groups name.
     */
    private String name;
    /**
     * List of related files.
     */
    private List<Datei> dateien;

    /**
     * returns datei by dateiId if found
     * @param dateiId id of file
     * @return returns Datei if found, otherwise null
     */
    public Datei getDateiById(final long dateiId) {
        for (Datei datei : this.getDateien()) {
            if (datei.getId() == dateiId) {
                return datei;
            }
        }
        return null;
    }
}
