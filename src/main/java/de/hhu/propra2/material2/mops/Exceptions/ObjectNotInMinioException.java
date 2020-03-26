package de.hhu.propra2.material2.mops.Exceptions;

public class ObjectNotInMinioException extends Exception {

    /**
     * Constructor
     */
    public ObjectNotInMinioException() {
        super("Something went wrong with the Download.");
    }

    /**
     * Constructor with message append
     *
     * @param message e.g. message from another error
     */
    public ObjectNotInMinioException(final String message) {
        super(message);
    }
}
