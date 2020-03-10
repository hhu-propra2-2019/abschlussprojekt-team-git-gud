package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


public final class DateiDTO {
    /**
     * Unique ID from database.
     */
    @Getter
    private final long id;
    /**
     * Name of file.
     */
    @Getter
    private final String name;
    /**
     * Path of file.
     */
    @Getter
    private final String pfad;
    /**
     *  User that uploaded the file.
     */
    @Getter
    private final UserDTO uploader;
    /**
     * All assigned tags.
     */
    @Getter
    private final List<TagDTO> tagDTOs;
    /**
     * Assigned group.
     */
    @Getter
    @Setter
    private GruppeDTO gruppe;
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
    @Getter
    private final long dateigroesse;
    /**
     * File type.
     */
    @Getter
    private final String dateityp;
    /**
     * Category for gui.
     */
    @Getter
    private final String kategorie;

    /**
     * Standard AllArgsConstructor for import from database.
     *  @param idArg
     * @param nameArg
     * @param pfadArg
     * @param uploaderArg
     * @param tagDTOsArg
     * @param uploaddatumArg
     * @param veroeffentlichungsdatumArg
     * @param dateigroesseArg
     * @param dateitypArg
     * @param gruppeArg
     * @param kategorie
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
                    final GruppeDTO gruppeArg,
                    final String kategorie) {
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
        this.kategorie = kategorie;
    }

    /**
     * Constructor for saving to database.
     * @param nameArg
     * @param pfadArg
     * @param uploaderArg
     * @param tagDTOsArg
     * @param uploaddatumArg
     * @param veroeffentlichungsdatumArg
     * @param dateigroesseArg
     * @param dateitypArg
     * @param gruppeArg
     * @param kategorie
     */
    public DateiDTO(final String nameArg,
                    final String pfadArg,
                    final UserDTO uploaderArg,
                    final List<TagDTO> tagDTOsArg,
                    final Date uploaddatumArg,
                    final Date veroeffentlichungsdatumArg,
                    final long dateigroesseArg,
                    final String dateitypArg,
                    final GruppeDTO gruppeArg,
                    final String kategorie) {
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
        this.kategorie = kategorie;
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
