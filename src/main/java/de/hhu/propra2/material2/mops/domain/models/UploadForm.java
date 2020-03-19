package de.hhu.propra2.material2.mops.domain.models;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadForm {
    private Long gruppenId;
    private String dateiname;
    private String selectedTags;
    private String timedUpload;
    private MultipartFile datei;
}
