package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


import java.util.Date;
import java.util.List;

@Data
@Table("Datei")
public final class DateiDTO {
    /**
     * Unique ID from database.
     */
    @Id
    private final long id;
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
    private final long dateigroesse;
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
    public DateiDTO(final long idArg,
                    final String nameArg,
                    final String pfadArg,
                    final UserDTO uploaderArg,
                    final List<TagDTO> tagDTOsArg,
                    final Date uploaddatumArg,
                    final Date veroeffentlichungsdatumArg,
                    final long dateigroesseArg,
                    final String dateitypArg,
                    final GruppeDTO gruppeArg) {
        this.id = idArg;
        this.name = nameArg;
        this.pfad = pfadArg;
        this.uploader = uploaderArg;
        this.tagDTOs = tagDTOsArg;
        this.gruppe = gruppeArg;
        this.uploaddatum = (Date) uploaddatumArg.clone();
        this.veroeffentlichungsdatum =
                (Date) veroeffentlichungsdatumArg.clone();
        this.dateigroesse = dateigroesseArg;
        this.dateityp = dateitypArg;
    }

    /**
     * Constructor for saving to database.
     *
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
    public DateiDTO(final String nameArg,
                    final String pfadArg,
                    final UserDTO uploaderArg,
                    final List<TagDTO> tagDTOsArg,
                    final Date uploaddatumArg,
                    final Date veroeffentlichungsdatumArg,
                    final long dateigroesseArg,
                    final String dateitypArg,
                    final GruppeDTO gruppeArg) {
        this.id = -1;
        this.name = nameArg;
        this.pfad = pfadArg;
        this.uploader = uploaderArg;
        this.tagDTOs = tagDTOsArg;
        this.gruppe = gruppeArg;
        this.uploaddatum = (Date) uploaddatumArg.clone();
        this.veroeffentlichungsdatum =
                (Date) veroeffentlichungsdatumArg.clone();
        this.dateigroesse = dateigroesseArg;
        this.dateityp = dateitypArg;
    }
    /**
     * Manual Date getter
     * because its mutable.
     * @return clone
     */
    public Date getUploaddatum() {
        return (Date) uploaddatum.clone();
    }

    /**
     * Manual Date getter
     * because its mutable.
     * @return clone
     */
    public Date getVeroeffentlichungsdatum() {
        return (Date) veroeffentlichungsdatum.clone();
    }

}
