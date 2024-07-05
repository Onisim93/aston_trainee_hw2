package org.example.service;

import org.example.dto.BaseDto;
import org.example.exception.EntityNotFoundException;

import java.util.List;

/**
 * BaseService interface providing standard CRUD operations.
 *
 * @param <T> the type of the entity
 * @param <K> the type of the entity's identifier
 */
public interface BaseService<T extends BaseDto<K>, K> {
    /**
     * Retrieves all entities.
     *
     * @return a list of all entities
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    List<T> findAll();

    /**
     * Retrieves an entity by its id.
     *
     * @param id the id of the entity to be retrieved
     * @return the entity with the specified id.
     * @throws IllegalArgumentException If the provided ID is null or invalid.
     * @throws EntityNotFoundException If the entity with the specified ID is not found in the repository.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    T findById(K id);

    /**
     * Creates a new entity.
     *
     * @param entity the entity to be created
     * @return the created entity with its new id.
     * @throws IllegalArgumentException If the provided required field data is null or invalid.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    T create(T entity);

    /**
     * Deletes an entity by its id.
     *
     * @param id the id of the entity to be deleted.
     * @throws IllegalArgumentException If the provided ID is null or invalid.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    void deleteById(K id);

    /**
     * Updates an existing entity.
     *
     * @param entity the entity to be updated
     * @return the updated entity.
     * @throws EntityNotFoundException If the entity with the specified ID is not found in the repository.
     * @throws IllegalArgumentException If the provided ID or required field data is null or invalid.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    T update(T entity, K id);
}
