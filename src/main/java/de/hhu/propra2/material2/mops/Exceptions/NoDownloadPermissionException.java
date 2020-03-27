package de.hhu.propra2.material2.mops.Exceptions;

public class NoDownloadPermissionException extends Exception {

    /**
     * Constructor
     */
    public NoDownloadPermissionException() {
        super("Something went wrong with the Download.");
    }

    /**
     * Constructor with message append
     *
     * @param message e.g. message from another error
     */
    public NoDownloadPermissionException(final String message) {
        super(message);
    }
}
