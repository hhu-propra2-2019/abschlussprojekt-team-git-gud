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
        return dateiTyp == null ? null : dateiTyp.clone();
    }

    public final String[] getTags() {
        return tags == null ? null : tags.clone();
    }

    public final String[] getUploader() {
        return uploader == null ? null : uploader.clone();
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
                 final Long gruppenIdArg) {
        this.vonDatum = vonDatumArg;
        this.bisDatum = bisDatumArg;
        this.tags = tagsArg == null ? null : tagsArg.clone();
        this.uploader = uploaderArg == null ? null : uploaderArg.clone();
        this.gruppenID = gruppenIDArg;
        this.dateiName = dateiNameArg;
        this.gruppenId = gruppenIdArg;
    }
}
