package de.hhu.propra2.material2.mops.domain.models;

import lombok.Data;

@Data
public class Suche {
    private final String vonDatum;
    private final String bisDatum;
    private final String tags;
    private final String dateiTyp;
    private final String uploader;
    private final String sortierKriterium;
    private final Long gruppenId;
    private final String dateiName;
    private final String reihenfolge;


    public final String[] getDateiTyp() {
        if (dateiTyp.isEmpty()) {
            return null;
        }
        return toArray(dateiTyp);
    }

    public final String[] getTags() {
        if (tags.isEmpty()) {
            return null;
        }
        return toArray(tags);
    }

    private String[] toArray(final String input) {
        String[] result  = input.split("\\s*,\\s*");
        return result;
    }

    public final String[] getUploader() {
        if (uploader.isEmpty()) {
            return null;
        }
        return toArray(uploader);
    }
    @SuppressWarnings("checkstyle:HiddenField")
    public Suche(final String vonDatum,
                 final String bisDatum,
                 final String tags,
                 final String dateiTyp,
                 final String uploader,
                 final String sortierKriterium,
                 final Long gruppenId,
                 final String dateiName,
                 final String reihenfolge) {
        this.dateiTyp = dateiTyp;
        this.vonDatum = vonDatum;
        this.bisDatum = bisDatum;
        this.tags = tags;
        this.uploader = uploader;
        this.sortierKriterium = sortierKriterium;
        this.gruppenId = gruppenId;
        this.dateiName = dateiName;
        this.reihenfolge = reihenfolge;
    }
}
