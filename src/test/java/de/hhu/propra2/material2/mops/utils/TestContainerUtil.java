package de.hhu.propra2.material2.mops.utils;

import org.testcontainers.containers.GenericContainer;

public class TestContainerUtil {
    public static final String MINIO_ACCESS_KEY = "admin";
    public static final String MINIO_SECRET_KEY = "12345678";
    public static final String MINIO_TEST_BUCKET_NAME = "testbucket";
    private static final int MINIO_PORT = 9000;

    public static GenericContainer getMinIOContainer() {
        return new GenericContainer("minio/minio")
                .withEnv("MINIO_ACCESS_KEY", MINIO_ACCESS_KEY)
                .withEnv("MINIO_SECRET_KEY", MINIO_SECRET_KEY)
                .withCommand("server /data")
                .withExposedPorts(MINIO_PORT);
    }
}
