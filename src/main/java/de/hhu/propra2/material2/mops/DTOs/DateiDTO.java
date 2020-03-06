package de.hhu.propra2.material2.mops.DTOs;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DateiDTO {
    /**
     * Unique ID from database.
     */
    private final int id;
    /**
     * Name of file.
     */
    private final String name;
    /**
     * Path of file.
     */
    private final String pfad;
    /**
     *  User that uploaded the file.
     */
    private final UserDTO uploader;
    /**
     * All assigned tags.
     */
    private final List<TagDTO> tagDTOs;
    /**
     * Assigned group.
     */
    private final GruppeDTO gruppe;
    /**
     * Upload date.
     */
    private final Date uploaddatum;
    /**
     * Date for when the file
     * will be visible to non-uploaders
     * of its group.
     */
    private final Date veroeffentlichungsdatum;
    /**
     * File size.
     */
    private final double dateigroesse;
    /**
     * File type.
     */
    private final String dateityp;

    /**
     * Standard AllArgsConstructor for import from database.
     *
     * @param idArg
     * @param nameArg
     * @param pfadArg
     * @param uploaderArg
     * @param tagDTOsArg
     * @param uploaddatumArg
     * @param veroeffentlichungsdatumArg
     * @param dateigroesseArg
     * @param dateitypArg
     * @param gruppeArg
     */
    public DateiDTO(final int idArg,
                    final String nameArg,
                    final String pfadArg,
                    final UserDTO uploaderArg,
                    final List<TagDTO> tagDTOsArg,
                    final Date uploaddatumArg,
                    final Date veroeffentlichungsdatumArg,
                    final double dateigroesseArg,
                    final String dateitypArg,
                    final GruppeDTO gruppeArg) {
        this.id = idArg;
        this.name = nameArg;
        this.pfad = pfadArg;
        this.uploader = uploaderArg;
        this.tagDTOs = tagDTOsArg;
        this.gruppe = gruppeArg;
        this.uploaddatum = uploaddatumArg;
        this.veroeffentlichungsdatum = veroeffentlichungsdatumArg;
        this.dateigroesse = dateigroesseArg;
        this.dateityp = dateitypArg;
    }
}
