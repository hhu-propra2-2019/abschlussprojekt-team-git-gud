package de.hhu.propra2.material2.mops.domain.services;

import com.google.common.base.Strings;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public final class FileUploadService {

    private static final String ENDPOINT = "http://localhost:23307/";
    private static final String ACCESS_KEY = "minio";
    private static final String SECRET_KEY = "minio123";
    private static final String BUCKETNAME = "materialsammlung";

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
    public FileUploadService() throws InvalidPortException,
            InvalidEndpointException,
            IOException, InvalidKeyException,
            NoSuchAlgorithmException, InsufficientDataException,
            InvalidResponseException, InternalException,
            NoResponseException, InvalidBucketNameException,
            XmlPullParserException, ErrorResponseException,
            RegionConflictException {

        minioClient = new MinioClient(ENDPOINT,
                ACCESS_KEY,
                SECRET_KEY);

        createBucketIfNotExists(BUCKETNAME);
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
        minioClient.putObject(BUCKETNAME, name, localFilePath, null,
                null, null, null);
    }

    /**
     * @param file   The file to upload
     * @param prefix A prefix to apply individual policies
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
    public String upload(final MultipartFile file,
                         final String prefix)
            throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException,
            InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException,
            ErrorResponseException, InvalidResponseException, RegionConflictException, InvalidArgumentException {
        createBucketIfNotExists(BUCKETNAME);
        String fullObjectName =
                Strings.isNullOrEmpty(prefix) ? file.getName() : FilenameUtils
                        .concat(prefix, file.getName());

        minioClient.putObject(BUCKETNAME, fullObjectName, file.getInputStream(),
                null, null, null, file.getContentType());
        return fullObjectName;
    }

    private void createBucketIfNotExists(final String bucketName) throws IOException,
            InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException,
            InvalidResponseException, InternalException, NoResponseException,
            InvalidBucketNameException, XmlPullParserException, ErrorResponseException,
            RegionConflictException {
        if (minioClient.bucketExists(bucketName)) {
            log.info("MinIO: Bucket '" + bucketName + "' already exists.");
        } else {
            minioClient.makeBucket(bucketName);
            log.info("MinIO: Bucket '" + bucketName + "' created");
        }
    }
}
