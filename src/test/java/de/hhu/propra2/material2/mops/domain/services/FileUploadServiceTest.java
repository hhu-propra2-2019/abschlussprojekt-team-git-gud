package de.hhu.propra2.material2.mops.domain.services;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xmlpull.v1.XmlPullParserException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {

    @Mock
    private MultipartFile fileMock;

    private FileUploadService fileUploadService;

    @BeforeEach
    public void setUp() throws InvalidPortException,
            InvalidEndpointException,
            IOException, InvalidKeyException,
            NoSuchAlgorithmException, InsufficientDataException,
            InvalidResponseException, InternalException,
            NoResponseException, InvalidBucketNameException,
            XmlPullParserException, ErrorResponseException,
            RegionConflictException {

        MinIOProperties minIOProperties = new MinIOProperties();
        minIOProperties.setEndpoint("http://localhost:23307/");
        minIOProperties.setAccesskey("minio");
        minIOProperties.setSecretkey("minio123");
        minIOProperties.setBucketname("materialsammlungtest");

        this.fileUploadService = new FileUploadService(minIOProperties);
    }

    @Test
    public void testUploadNameMaking1() {
        String fileName = "newFileName";

        boolean result = fileUploadService.upload(fileMock, fileName);

        assertTrue(result);
    }
}
