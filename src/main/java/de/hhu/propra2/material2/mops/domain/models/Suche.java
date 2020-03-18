package de.hhu.propra2.material2.mops.domain.models;

import lombok.Data;

@Data
public class Suche {
    private final String vonDatum;
    private final String bisDatum;
    private final String[] tags;
    private final String[] dateiTyp;
    private final String[] uploader;
    private final String sortierKriterium;
    private final Long gruppenId;
    private final String dateiName;
    private final String reihenfolge;


    public final String[] getDateiTyp() {
        return dateiTyp == null ? null : dateiTyp.clone();
    }

    public final String[] getTags() {
        return tags == null ? null : tags.clone();
    }

    public final String[] getUploader() {
        return uploader == null ? null : uploader.clone();
    }
    @SuppressWarnings("checkstyle:HiddenField")
    public Suche(final String vonDatum,
                 final String bisDatum,
                 final String[] tags,
                 final String[] dateiTyp,
                 final String[] uploader,
                 final String sortierKriterium,
                 final Long gruppenId,
                 final String dateiName,
                 final String reihenfolge) {
        this.dateiTyp = dateiTyp == null ? null : dateiTyp.clone();
        this.vonDatum = vonDatum;
        this.bisDatum = bisDatum;
        this.tags = tags == null ? null : tags.clone();
        this.uploader = uploader == null ? null : uploader.clone();
        this.sortierKriterium = sortierKriterium;
        this.gruppenId = gruppenId;
        this.dateiName = dateiName;
        this.reihenfolge = reihenfolge;
    }
}
