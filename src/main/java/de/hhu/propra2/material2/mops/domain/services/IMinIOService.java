package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.DownloadException;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface IMinIOService {

    String getUrl(Datei datei) throws DownloadException;

    InputStream getObject(Long id) throws DownloadException;

    boolean upload(MultipartFile file, String fileName);

    boolean deleteFile(long dateiID);
}

