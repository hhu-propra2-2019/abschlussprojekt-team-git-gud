package de.hhu.propra2.material2.mops.domain.services;

import io.minio.MinioClient;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {

    @Mock
    private MinioClient minioClientMock;
    @Mock
    private MinIOProperties minIOPropertiesMock;
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
        this.fileUploadService = new FileUploadService(minIOPropertiesMock);
    }

    @Test
    public void testUploadNameMaking1() {
        String fileName = "newFileName";
        String prefix = "";
        String result = "";
        try{
            result = fileUploadService.upload(fileMock, fileName, prefix);
        }
        catch (Exception e) {
            System.out.println("Exception thrown");
        }

        assertEquals(result, "newFileName");
    }
}
