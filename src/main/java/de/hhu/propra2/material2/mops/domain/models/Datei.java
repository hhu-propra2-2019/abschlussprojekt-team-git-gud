package de.hhu.propra2.material2.mops.domain.models;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class Datei {

    private long id;
    private String name;
    private final String pfad;
    private final User uploader;
    private final List<Tag> tags;
    private final LocalDate uploaddatum;
    private final LocalDate veroeffentlichungsdatum;
    private final double dateigroesse;
    private final String dateityp;


    public Datei(
            final long idArgs,
            final String nameArgs,
            final String pfadArgs,
            final User uploaderArgs,
            final List<Tag> tagsArgs,
            final LocalDate uploaddatumArgs,
            final LocalDate veroeffentlichungsdatumArgs,
            final double dateigroesseArgs,
            final String dateitypArgs) {
        this.id = idArgs;
        this.name = nameArgs;
        this.pfad = pfadArgs;
        this.uploader = uploaderArgs;
        this.tags = tagsArgs;
        this.uploaddatum = uploaddatumArgs;
        this.veroeffentlichungsdatum =
                veroeffentlichungsdatumArgs;
        this.dateigroesse = dateigroesseArgs;
        this.dateityp = dateitypArgs;
    }

    private List<String> getTagNames() {
        return tags.stream()
                .map(Tag::getText)
                .collect(Collectors.toList());
    }

    private boolean hatTag(final String tag) {
        for (String tempTag : this.getTagNames()) {
            if (tempTag.equalsIgnoreCase(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if an object of Datei has a specific Tags. Returns false if thats not the case.
     * @param tagsToCheckFor
     * @return
     */
    public boolean hatTags(final String[] tagsToCheckFor) {
        for (String tag : tagsToCheckFor) {
            if (!this.hatTag(tag)) {
                return false;
            }
        }
        return true;
    }
}
