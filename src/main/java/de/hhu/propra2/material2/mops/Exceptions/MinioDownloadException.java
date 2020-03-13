package de.hhu.propra2.material2.mops.Exceptions;

public class MinioDownloadException extends Exception {

    /**
     * Constructor
     */
    public MinioDownloadException() {
        super("Could not download from MinIO.");
    }

    /**
     * Constructor with message append
     * @param message e.g. message from another error
     */
    public MinioDownloadException(final String message) {
        super("Could not download from MinIO: " + message);
    }
}
