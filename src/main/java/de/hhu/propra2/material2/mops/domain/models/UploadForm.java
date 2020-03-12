package de.hhu.propra2.material2.mops.domain.models;

import lombok.Data;

import java.io.File;
import java.util.Date;

@Data
public class UploadForm {
    private String gruppenwahl;
    private String dateiname;
    private String selectedTags;
    private Date timedUpload;
    private File datei;
}
