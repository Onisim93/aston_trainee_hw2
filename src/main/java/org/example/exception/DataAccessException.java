package org.example.exception;

/**
 * Exception thrown when there is an error accessing data from the database.
 */
public class DataAccessException extends RuntimeException{

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
