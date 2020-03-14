package de.hhu.propra2.material2.mops.domain.services;

public interface IUploadService {
    // the return value should be a Warning/Message Object
    void startUpload(UploadForm upForm, String uploader);
}
