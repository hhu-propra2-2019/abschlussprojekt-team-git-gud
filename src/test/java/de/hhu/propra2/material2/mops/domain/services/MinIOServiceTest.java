package de.hhu.propra2.material2.mops.domain.services;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.NoResponseException;
import io.minio.errors.RegionConflictException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressFBWarnings(value = "HARD_CODE_PASSWORD", justification = "It's only for testing purposes")
@ExtendWith(MockitoExtension.class)
public class MinIOServiceTest {
    private static final String ACCESS_KEY = "admin";
    private static final String SECRET_KEY = "12345678";
    private static final String TEST_BUCKET_NAME = "testbucket";
    private static final int MINIO_PORT = 9000;
    private static final int STARTUP_TIMEOUT = 10;

    private static GenericContainer minioServer;
    private static String minioServerUrl;

    private static MinIOService minIOService;

    @BeforeAll
    static void setUp() throws IOException, InvalidKeyException,
            NoSuchAlgorithmException, XmlPullParserException,
            InvalidPortException, InvalidResponseException,
            ErrorResponseException, InternalException,
            NoResponseException, InvalidBucketNameException,
            InsufficientDataException, InvalidEndpointException,
            RegionConflictException {
        minioServer = new GenericContainer("minio/minio")
                .withEnv("MINIO_ACCESS_KEY", ACCESS_KEY)
                .withEnv("MINIO_SECRET_KEY", SECRET_KEY)
                .withCommand("server /data")
                .withExposedPorts(MINIO_PORT)
                .waitingFor(new HttpWaitStrategy()
                        .forPath("/minio/health/ready")
                        .forPort(MINIO_PORT)
                        .withStartupTimeout(Duration.ofSeconds(STARTUP_TIMEOUT)));
        minioServer.start();

        Integer mappedPort = minioServer.getFirstMappedPort();
        Testcontainers.exposeHostPorts(mappedPort);
        minioServerUrl = String.format("http://%s:%s", minioServer.getContainerIpAddress(), mappedPort);

        MinIOProperties minIOProperties = new MinIOProperties();
        minIOProperties.setEndpoint(minioServerUrl);
        minIOProperties.setAccesskey(ACCESS_KEY);
        minIOProperties.setSecretkey(SECRET_KEY);
        minIOProperties.setBucketname(TEST_BUCKET_NAME);

        minIOService = new MinIOService(minIOProperties);
    }

    /**
     * test generic upload method with generic file.
     */
    @Test
    public void uploadFile() {
        String dateiIdAsString = "123";
        MultipartFile file = new MockMultipartFile("test.txt",
                "test.txt",
                "text/plain",
                ("The first rule of Fight Club is: You do not talk about Fight Club."
                        + "The second rule of Fight Club is: You do not talk about Fight Club.")
                        .getBytes(StandardCharsets.UTF_8));

        boolean result = minIOService.upload(file, dateiIdAsString);

        assertTrue(result);
    }
}
