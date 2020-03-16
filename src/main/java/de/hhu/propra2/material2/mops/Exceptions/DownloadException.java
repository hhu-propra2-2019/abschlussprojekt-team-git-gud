package de.hhu.propra2.material2.mops.Exceptions;

public class DownloadException extends Exception {

    /**
     * Constructor
     */
    public DownloadException() {
        super("Something went wrong with the Download.");
    }

    /**
     * Constructor with message append
     * @param message e.g. message from another error
     */
    public DownloadException(final String message) {
        super(message);
    }
}
