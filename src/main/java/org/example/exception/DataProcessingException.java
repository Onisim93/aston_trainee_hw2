package org.example.exception;

/**
 * Exception thrown when there is an error processing data in the database.
 */
public class DataProcessingException extends RuntimeException{
    public DataProcessingException() {

    }

    public DataProcessingException(String message) {
        super(message);
    }

    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProcessingException(Throwable cause) {
        super(cause);
    }
}
