package de.hhu.propra2.material2.mops.domain.services.suchComparators;



import de.hhu.propra2.material2.mops.domain.models.Datei;

import java.io.Serializable;
import java.util.Comparator;

public class DateiDateiTypComparator implements Comparator<Datei>, Serializable {

    @Override
    public final int compare(final Datei d1, final Datei d2) {
        return d1.getDateityp().compareTo(d2.getDateityp());
    }
}
