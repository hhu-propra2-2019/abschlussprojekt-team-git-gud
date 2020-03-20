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
import io.minio.errors.RegionConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class MinIOService implements IMinIOService {

    private MinioClient minioClient;
    private MinIOProperties minIOProperties;

    public MinIOService(MinIOProperties minIOPropertiesArg) throws InvalidPortException,
            InvalidEndpointException, IOException,
            XmlPullParserException, NoSuchAlgorithmException,
            RegionConflictException, InvalidKeyException,
            InvalidResponseException, ErrorResponseException,
            NoResponseException, InvalidBucketNameException,
            InsufficientDataException, InternalException {
        this.minIOProperties = minIOPropertiesArg;
        System.out.println(minIOProperties.getBucketname());
        minioClient = new MinioClient(minIOProperties.getEndpoint(),
                minIOProperties.getAccesskey(),
                minIOProperties.getSecretkey());

        createBucketIfNotExists(minIOProperties.getBucketname());

    }

    private void createBucketIfNotExists(final String bucket) throws IOException,
            InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException,
            InvalidResponseException, InternalException, NoResponseException,
            InvalidBucketNameException, XmlPullParserException, ErrorResponseException,
            RegionConflictException {
        if (minioClient.bucketExists(bucket)) {
            log.info("MinIO: Bucket '" + bucket + "' already exists.");
        } else {
            minioClient.makeBucket(bucket);
            log.info("MinIO: Bucket '" + bucket + "' created");
        }
    }

    /**
     * returns Downloadlink with expiration time 1h
     *
     * @param datei datei for download
     * @return url for the download
     * @throws DownloadException Exception if anything went wrong with the minIO download
     */
    public String getUrl(final Datei datei) throws DownloadException {
        final String objectName = Long.toString(datei.getId());
        final int expiration = 3600;

        try {
            return minioClient.presignedGetObject(minIOProperties.getBucketname(), objectName, expiration);
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
    public InputStream getObject(final Long fileId) throws DownloadException {
        final String objectName = Long.toString(fileId);

        try {
            return minioClient.getObject(minIOProperties.getBucketname(), objectName);
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

    /**
     * @param file The file to upload
     * @return successrate of uploading
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InsufficientDataException
     * @throws InternalException
     * @throws NoResponseException
     * @throws InvalidBucketNameException
     * @throws XmlPullParserException
     * @throws ErrorResponseException
     */
    public boolean upload(final MultipartFile file, final String fileName) {
        try {
            createBucketIfNotExists(minIOProperties.getBucketname());
            minioClient.putObject(minIOProperties.getBucketname(), fileName, file.getInputStream(),
                    null, null, null, file.getContentType());
            return true;
        } catch (Exception e) {
            log.info("Exception: " + e);
            return false;
        }
    }

    /**
     * removes file from minio-server.
     * @param dateiID
     */
    public void deleteFile(long dateiID) {
        try {
            minioClient.removeObject(minIOProperties.getBucketname(), Long.toString(dateiID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
