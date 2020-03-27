package de.hhu.propra2.material2.mops.Exceptions;

public class NoAccessPermissionException extends Exception {

    /**
     * Constructor.
     */
    public NoAccessPermissionException() {
        super("User has no Access to this file.");
    }

    /**
     * Constructor with message append.
     *
     * @param message e.g. message from another error
     */
    public NoAccessPermissionException(final String message) {
        super(message);
    }
}
