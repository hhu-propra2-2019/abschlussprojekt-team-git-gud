package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.domain.models.UploadForm;

public interface IUploadService {
    // the return value should be a Warning/Message Object
    void startUpload(UploadForm upForm, String uploader);
}
