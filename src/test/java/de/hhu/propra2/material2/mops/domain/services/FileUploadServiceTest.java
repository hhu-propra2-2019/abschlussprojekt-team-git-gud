package de.hhu.propra2.material2.mops.domain.services;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressFBWarnings(value = "HARD_CODE_PASSWORD", justification = "It's only for testing purposes")
@ExtendWith(MockitoExtension.class)
@Testcontainers
public class FileUploadServiceTest {
    private static final String ACCESS_KEY = "admin";
    private static final String SECRET_KEY = "12345678";
    private static final String TEST_BUCKET_NAME = "testbucket";
    private static final int MINIO_PORT = 9000;

    @Container
    public static GenericContainer minioServer = new GenericContainer("minio/minio")
            .withEnv("MINIO_ACCESS_KEY", ACCESS_KEY)
            .withEnv("MINIO_SECRET_KEY", SECRET_KEY)
            .withCommand("server /data")
            .withExposedPorts(MINIO_PORT);

    private static String minioServerUrl;
    private static FileUploadService fileUploadService;

    @BeforeAll
    static void setUp() throws Exception {
        minioServerUrl = String.format("http://%s:%s", minioServer.getContainerIpAddress(),
                minioServer.getFirstMappedPort());

        MinIOProperties minIOProperties = new MinIOProperties();
        minIOProperties.setEndpoint(minioServerUrl);
        minIOProperties.setAccesskey(ACCESS_KEY);
        minIOProperties.setSecretkey(SECRET_KEY);
        minIOProperties.setBucketname(TEST_BUCKET_NAME);

        fileUploadService = new FileUploadService(minIOProperties);
    }

    @Test
    public void uploadFile() {
        String dateiIdAsString = "123";
        MultipartFile file = new MockMultipartFile("test.txt",
                "test.txt",
                "text/plain",
                ("The first rule of Fight Club is: You do not talk about Fight Club."
                        + "The second rule of Fight Club is: You do not talk about Fight Club.")
                        .getBytes(StandardCharsets.UTF_8));

        boolean result = fileUploadService.upload(file, dateiIdAsString);

        assertTrue(result);
    }
}
