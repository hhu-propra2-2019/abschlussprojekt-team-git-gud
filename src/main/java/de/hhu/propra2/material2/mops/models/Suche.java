package de.hhu.propra2.material2.mops.models;


import de.hhu.propra2.material2.mops.domain.models.Gruppe;
import lombok.Value;

@Value
public class Suche {
    private final String vonDatum;
    private final String bisDatum;
    private final String[] tags;
    private final String[] dateiTyp;
    private final String[] uploader;
    private final String sortierung;
    private final Gruppe gruppe;
}
