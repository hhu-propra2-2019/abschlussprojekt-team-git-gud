package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.DownloadException;
import de.hhu.propra2.material2.mops.domain.models.Datei;

import java.io.InputStream;

public interface IDownloadService {

    String getUrl(Datei datei) throws DownloadException;
    InputStream getObject(String id) throws DownloadException;

}
