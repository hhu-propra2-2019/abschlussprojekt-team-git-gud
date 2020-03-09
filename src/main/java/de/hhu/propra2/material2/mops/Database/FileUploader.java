package de.hhu.propra2.material2.mops.Database;


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
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public final class FileUploader {

    /**
     * Static MinIO client to upload
     * and download files.
     */
    private MinioClient minioClient;

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
    public FileUploader() throws InvalidPortException, InvalidEndpointException,
            IOException, InvalidKeyException,
            NoSuchAlgorithmException, InsufficientDataException,
            InvalidResponseException, InternalException,
            NoResponseException, InvalidBucketNameException,
            XmlPullParserException, ErrorResponseException,
            RegionConflictException {

        minioClient = new MinioClient("http://localhost:23307/",
                "minio",
                "minio123");

        if (minioClient.bucketExists("materialsammlung")) {
            log.info("Bucket already exists.");
        } else {
            minioClient.makeBucket("materialsammlung");
            log.info("materialsammlung created");
        }
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
        minioClient.putObject("materialsammlung", name, localFilePath, null,
                null, null, null);
    }
}
