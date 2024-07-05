package org.example.model;

/**
 * BaseEntity is a generic interface for entities that have an identifier.
 * This interface provides methods to get and set the identifier of the entity.
 *
 * @param <T> the type of the identifier
 */
public interface BaseEntity<T> {
    /**
     * Gets the identifier of the entity.
     *
     * @return the identifier of the entity
     */
    T getId();

    /**
     * Sets the identifier of the entity.
     *
     * @param id the identifier of the entity
     */
    void setId(T id);
}
