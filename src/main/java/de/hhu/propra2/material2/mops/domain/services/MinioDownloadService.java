package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.Exceptions.MinioDownloadException;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.NoResponseException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public final class MinioDownloadService {

    private MinioClient minioClient;

    public MinioDownloadService(final MinioClient client) {
        this.minioClient = client;
    }

    /**
     * returns Downloadlink with expiration time 1h
     *
     * @param bucket     name of the minIO bucket
     * @param objectName name of the object in the miniIO bucket
     * @return url for the download
     * @throws MinioDownloadException if something wents wrong with the minIO download
     */
    public String getUrl(final String bucket, final String objectName) throws MinioDownloadException {
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
            throw new MinioDownloadException(e.getMessage());
        }
    }

    /**
     * return InputStream from object.
     * @param bucket
     * @param objectName
     * @return
     * @throws MinioDownloadException
     */
    public InputStream getObject(final String bucket, final String objectName) throws MinioDownloadException {
        try {
            return minioClient.getObject(bucket, objectName);
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
            throw new MinioDownloadException(e.getMessage());
        }
    }
}
