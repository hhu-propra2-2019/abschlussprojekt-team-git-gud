package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.DownloadException;
import de.hhu.propra2.material2.mops.domain.models.Datei;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.NoResponseException;
import okhttp3.HttpUrl;
import org.springframework.stereotype.Service;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public final class MinioDownloadService implements IDownloadService {

    private final MinioClient minioClient;
    @SuppressWarnings("checkStyle:magicnumber")
    public MinioDownloadService() throws InvalidPortException, InvalidEndpointException {
        this.minioClient = new MinioClient(HttpUrl.parse("http://localhost:23307"), "minio", "minio123");
        minioClient.
    }

    /**
     * returns Downloadlink with expiration time 1h
     *
     * @param datei datei for download
     * @return url for the download
     * @throws DownloadException Exception if anything went wrong with the minIO download
     */
    public String getUrl(final Datei datei) throws DownloadException {
        final String bucket = "materialsammlung";
        final String objectName = Long.toString(datei.getId());

        final int expiration = 3600;

        try {
            return minioClient.presignedGetObject(bucket, objectName, expiration);
        } catch (IOException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | InsufficientDataException
                | InvalidExpiresRangeException
                | InvalidResponseException
                | InternalException
                | NoResponseException
                | InvalidBucketNameException
                | XmlPullParserException
                | ErrorResponseException e) {
            throw new DownloadException("Could not download from MinIO: " + e.getMessage());
        }
    }

    /**
     * return InputStream from object.
     *
     * @param fileId datei for download
     * @return downloadDatei as InputStream
     * @throws DownloadException Exception if anything went wrong with the minIO Download
     */
    public InputStream getObject(final String fileId) throws DownloadException {
        final String bucket = "materialsammlung";
        //final String objectName = Long.toString(fileId);

        try {
            return minioClient.getObject(bucket, fileId);
        } catch (IOException
                | InvalidKeyException
                | NoSuchAlgorithmException
                | InsufficientDataException
                | InvalidArgumentException
                | InvalidResponseException
                | InternalException
                | NoResponseException
                | InvalidBucketNameException
                | XmlPullParserException
                | ErrorResponseException e) {
            throw new DownloadException("Could not download from MinIO: " + e.getMessage());
        }
    }
}
