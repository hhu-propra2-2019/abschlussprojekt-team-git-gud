package de.hhu.propra2.material2.mops.Database;



import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public final class FileUploader {

    /**
     * Static MinIO client to upload
     * and download files.
     */
    private  static MinioClient minioClient;

    static {
        try {
            minioClient = new MinioClient("localhost:23307",
                    "minio",
                    "minio123");
            if (minioClient.bucketExists("materialsammlung")) {
                System.err.println("Bucket already exists.");
            } else {
                minioClient.makeBucket("materialsammlung");
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }

    private FileUploader() { }

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
    public static void uploadFile(final String name, final String localFilePath)
                            throws MinioException, XmlPullParserException,
                            NoSuchAlgorithmException,
                            InvalidKeyException, IOException {
        minioClient.putObject("materialsammlung", name, localFilePath, null,
                null, null, null);
    }
}
