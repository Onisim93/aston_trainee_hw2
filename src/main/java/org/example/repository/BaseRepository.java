package org.example.repository;

import org.example.exception.EntityNotFoundException;
import org.example.model.BaseEntity;

import java.util.List;

/**
 * A generic interface for CRUD operations on a repository for a specific type.
 * @param <T> the type of entity to be handled by this repository.
 * @param <K> the type of entity ID field.
 */
public interface BaseRepository<T extends BaseEntity<K>, K> {
    /**
     * Creates a new entity in the repository.
     *
     * @param entity The entity object to create.
     * @return The created entity object.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    T create(T entity);

    /**
     * Updates an existing entity in the repository.
     *
     * @param entity The updated entity object.
     * @return The updated entity object.
     * @throws EntityNotFoundException If the entity with the specified ID is not found in the repository.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    T update(T entity);

    /**
     * Retrieves an entity from the repository by its ID.
     *
     * @param id The ID of the entity to retrieve.
     * @return The entity object if found, null otherwise.
     * @throws IllegalArgumentException If the provided ID is null or invalid.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    T findById(K id);

    /**
     * Retrieves all entities from the repository.
     *
     * @return A list of all entities in the repository.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    List<T> findAll();

    /**
     * Deletes an entity from the repository by its ID.
     *
     * @param id The ID of the entity to delete.
     * @throws EntityNotFoundException If the entity with the specified ID is not found in the repository.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    void deleteById(K id);
}
