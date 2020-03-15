package de.hhu.propra2.material2.mops.domain.services.suchComparators;


import de.hhu.propra2.material2.mops.database.entities.DateiDAO;

import java.io.Serializable;
import java.util.Comparator;

public class DateiDatumComparator implements Comparator<DateiDAO>, Serializable {

    @Override
    public final int compare(final DateiDAO d1, final DateiDAO d2) {
        return d1.getUploaddatum().compareTo(d2.getUploaddatum());
    }
}
