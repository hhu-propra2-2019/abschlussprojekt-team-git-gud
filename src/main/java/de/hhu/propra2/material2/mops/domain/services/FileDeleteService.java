package de.hhu.propra2.material2.mops.domain.services;

import groovy.util.logging.Slf4j;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableConfigurationProperties(MinIOProperties.class)
public class FileDeleteService {
    private MinioClient minioClient;
    private MinIOProperties minIOProperties;

    public FileDeleteService(final MinIOProperties minIOPropertiesArg) throws InvalidPortException,
            InvalidEndpointException {
        this.minIOProperties = minIOPropertiesArg;
        System.out.println(minIOProperties.getBucketname());
        minioClient = new MinioClient(minIOProperties.getEndpoint(),
                minIOProperties.getAccesskey(),
                minIOProperties.getSecretkey());
    }

    public void deleteFile(long dateiID) {
        try {
            minioClient.removeObject(minIOProperties.getBucketname(), Long.toString(dateiID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
