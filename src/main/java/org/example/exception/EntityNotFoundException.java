package org.example.exception;

/**
 * Exception thrown when an entity (e.g., book, author, genre) is not found in the database.
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
