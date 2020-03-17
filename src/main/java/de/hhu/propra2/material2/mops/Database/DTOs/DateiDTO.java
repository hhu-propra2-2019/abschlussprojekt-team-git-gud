package de.hhu.propra2.material2.mops.Database.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public final class DateiDTO {
    /**
     * Unique ID from database.
     */
    @Getter
    private long id;
    /**
     * Name of file.
     */
    @Getter
    private String name;
    /**
     * Path of file.
     */
    @Getter
    private String pfad;
    /**
     * User that uploaded the file.
     */
    @Getter
    private UserDTO uploader;
    /**
     * All assigned tags.
     */
    @Getter
    private List<TagDTO> tagDTOs;
    /**
     * Assigned group.
     */
    @Getter
    @Setter
    private GruppeDTO gruppe;
    /**
     * Upload date.
     */
    @Getter
    private LocalDate uploaddatum;
    /**
     * Date for when the file
     * will be visible to non-uploaders
     * of its group.
     */
    @Getter
    private LocalDate veroeffentlichungsdatum;
    /**
     * File size.
     */
    @Getter
    private long dateigroesse;
    /**
     * File type.
     */
    @Getter
    private String dateityp;
    /**
     * Category for gui.
     */
    @Getter
    private String kategorie;

    /**
     * Standard Constructor for import from database
     * and for saving changes to existing Datei.
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
     * @param kategorieArg
     */
    public DateiDTO(final long idArg,
                    final String nameArg,
                    final String pfadArg,
                    final UserDTO uploaderArg,
                    final List<TagDTO> tagDTOsArg,
                    final LocalDate uploaddatumArg,
                    final LocalDate veroeffentlichungsdatumArg,
                    final long dateigroesseArg,
                    final String dateitypArg,
                    final GruppeDTO gruppeArg,
                    final String kategorieArg) {
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
        this.kategorie = kategorieArg;
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
     * @param kategorieArg
     */
    public DateiDTO(final String nameArg,
                    final String pfadArg,
                    final UserDTO uploaderArg,
                    final List<TagDTO> tagDTOsArg,
                    final LocalDate uploaddatumArg,
                    final LocalDate veroeffentlichungsdatumArg,
                    final long dateigroesseArg,
                    final String dateitypArg,
                    final GruppeDTO gruppeArg,
                    final String kategorieArg) {
        this.id = -1;
        this.name = nameArg;
        this.pfad = pfadArg;
        this.uploader = uploaderArg;
        this.tagDTOs = tagDTOsArg;
        this.gruppe = gruppeArg;
        this.uploaddatum = uploaddatumArg;
        this.veroeffentlichungsdatum = veroeffentlichungsdatumArg;
        this.dateigroesse = dateigroesseArg;
        this.dateityp = dateitypArg;
        this.kategorie = kategorieArg;
    }
}
