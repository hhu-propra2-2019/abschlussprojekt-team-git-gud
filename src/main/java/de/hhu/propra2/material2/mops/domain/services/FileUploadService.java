package de.hhu.propra2.material2.mops.domain.services;

import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@EnableConfigurationProperties(MinIOProperties.class)
public final class FileUploadService {
    /**
     * Static MinIO client to upload
     * and download files.
     */
    private MinioClient minioClient;

    private MinIOProperties minIOProperties;

    /**
     * Used to upload Files to
     * the MinIO storage.
     *
     * @throws InvalidPortException
     * @throws InvalidEndpointException
     * @throws IOException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InsufficientDataException
     * @throws InvalidResponseException
     * @throws InternalException
     * @throws NoResponseException
     * @throws InvalidBucketNameException
     * @throws XmlPullParserException
     * @throws ErrorResponseException
     * @throws RegionConflictException
     */
    public FileUploadService(final MinIOProperties minIOPropertiesArg) throws InvalidPortException,
            InvalidEndpointException,
            IOException, InvalidKeyException,
            NoSuchAlgorithmException, InsufficientDataException,
            InvalidResponseException, InternalException,
            NoResponseException, InvalidBucketNameException,
            XmlPullParserException, ErrorResponseException,
            RegionConflictException {
        this.minIOProperties = minIOPropertiesArg;
        System.out.println(minIOProperties.getBucketname());
        minioClient = new MinioClient(minIOProperties.getEndpoint(),
                minIOProperties.getAccesskey(),
                minIOProperties.getSecretkey());

        createBucketIfNotExists(minIOProperties.getBucketname());
    }

    /**
     * Uploads a file with the given name and
     * it's local file path.
     *
     * @param name
     * @param localFilePath
     * @throws MinioException
     * @throws XmlPullParserException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     */
    public void uploadFile(final String name, final String localFilePath)
            throws MinioException, XmlPullParserException,
            NoSuchAlgorithmException,
            InvalidKeyException, IOException {
        minioClient.putObject(minIOProperties.getBucketname(), name, localFilePath, null,
                null, null, null);
    }

    /**
     * @param file The file to upload
     * @return The object name
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
            return false;
        }
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
}
