package de.hhu.propra2.material2.mops.domain.services.suchComparators;

import de.hhu.propra2.material2.mops.domain.models.Datei;

import java.util.Comparator;

public class DateiNamenComparator implements Comparator<Datei> {

    @Override
    public final int compare(final Datei d1, final Datei d2) {
        return d1.getName().compareTo(d2.getName());
    }
}
