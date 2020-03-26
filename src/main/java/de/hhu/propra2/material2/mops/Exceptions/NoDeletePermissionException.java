package de.hhu.propra2.material2.mops.Exceptions;

public class NoDeletePermissionException extends Exception {

    /**
     * Constructor
     */
    public NoDeletePermissionException() {
        super("Something went wrong with the Download.");
    }

    /**
     * Constructor with message append
     *
     * @param message e.g. message from another error
     */
    public NoDeletePermissionException(final String message) {
        super(message);
    }
}
