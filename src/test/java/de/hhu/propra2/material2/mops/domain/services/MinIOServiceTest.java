package de.hhu.propra2.material2.mops.domain.services;

import de.hhu.propra2.material2.mops.utils.TestContainerUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Rule;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SuppressFBWarnings(value = "HARD_CODE_PASSWORD", justification = "It's only for testing purposes")
@ExtendWith(MockitoExtension.class)
@Testcontainers
class MinIOServiceTest {

    @Rule
    @Container
    private static final GenericContainer MINIO_SERVER = TestContainerUtil.getMinIOContainer();

    private static MinIOService minIOService;

    @BeforeAll
    static void setUp() throws Exception {
        String minIOServerUrl = String.format("http://%s:%s", MINIO_SERVER.getContainerIpAddress(),
                MINIO_SERVER.getFirstMappedPort());

        MinIOProperties minIOProperties = new MinIOProperties();
        minIOProperties.setEndpoint(minIOServerUrl);
        minIOProperties.setAccesskey(TestContainerUtil.MINIO_ACCESS_KEY);
        minIOProperties.setSecretkey(TestContainerUtil.MINIO_SECRET_KEY);
        minIOProperties.setBucketname(TestContainerUtil.MINIO_TEST_BUCKET_NAME);

        minIOService = new MinIOService(minIOProperties);
    }

    /**
     * test generic upload method with generic file.
     */
    @Test
    void uploadFile() {
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

    @Test
    void deleteFile() {
        String dateiIdAsString = "22";
        MultipartFile file = new MockMultipartFile("test.txt",
                "test.txt",
                "text/plain",
                ("Hi")
                        .getBytes(StandardCharsets.UTF_8));

        assumeTrue(minIOService.upload(file, dateiIdAsString));

        boolean result = minIOService.deleteFile(dateiIdAsString);

        assertTrue(result);
    }

    @Test
    void deleteFileThatDoesntExist() {
        String dateiIdAsString = "33";
        MultipartFile file = new MockMultipartFile("test.txt",
                "test.txt",
                "text/plain",
                ("i do not exist")
                        .getBytes(StandardCharsets.UTF_8));

        assumeTrue(minIOService.upload(file, dateiIdAsString));
        assumeTrue(minIOService.deleteFile(dateiIdAsString));

        boolean result = minIOService.deleteFile(dateiIdAsString);

        assertFalse(result);
    }


}
