package de.hhu.propra2.material2.mops.Exceptions;

public class FileNotPublishedYetException extends Exception {

    /**
     * Constructor
     */
    public FileNotPublishedYetException() {
        super("Something went wrong with the Download.");
    }

    /**
     * Constructor with message append
     *
     * @param message e.g. message from another error
     */
    public FileNotPublishedYetException(final String message) {
        super(message);
    }
}
