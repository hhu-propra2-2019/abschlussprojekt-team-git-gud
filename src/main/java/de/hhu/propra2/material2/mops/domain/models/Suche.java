package de.hhu.propra2.material2.mops.domain.models;


import lombok.Value;

@Value
public class Suche {
    private final String vonDatum;
    private final String bisDatum;
    private final String[] tags;
    private final String[] dateiTyp;
    private final String[] uploader;
    private final String sortierung;
    private final Long gruppenID;
    private final String dateiName;

    public final String[] getDateiTyp() {
        return dateiTyp.clone();
    }

    public final String[] getTags() {
        return tags.clone();
    }

    public final String[] getUploader() {
        return uploader.clone();
    }

    public Suche(final String vonDatumArg,
                 final String bisDatumArg,
                 final String[] tagsArg,
                 final String[] dateiTypArg,
                 final String[] uploaderArg,
                 final String sortierungArg,
                 final Long gruppenIDArg,
                 final String dateiNameArg) {
        this.dateiTyp = dateiTypArg.clone();
        this.vonDatum = vonDatumArg;
        this.bisDatum = bisDatumArg;
        this.tags = tagsArg.clone();
        this.uploader = uploaderArg.clone();
        this.sortierung = sortierungArg;
        this.gruppenID = gruppenIDArg;
        this.dateiName = dateiNameArg;
    }
}
