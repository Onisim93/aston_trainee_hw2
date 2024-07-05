package org.example.repository;

import org.example.model.BookEntity;

import java.util.List;

/**
 * Repository class for managing {@link BookEntity} entities.
 */
public interface BookRepository extends BaseRepository<BookEntity, Integer>{
    /**
     * Find a list of books based on the provided criteria.
     * @param authorId The id of the author. NOT NULL.
     * @param genreId The id of the genre.  NOT NULL.
     * @return a list of books matching the given criteria.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    List<BookEntity> findByCriteria(Integer authorId, Integer genreId);

    /**
     * Find a list of books written by the author with the specified ID.
     * @param authorId The id of the author.
     * @return the list of books written by the author with the specified ID
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    List<BookEntity> findByAuthorId(Integer authorId);
    /**
     * Find a list of books that belong to the genre with the specified ID.
     * @param genreId The id of the genre.
     * @return the list of books that belong to the genre with the specified ID.
     * @throws org.example.exception.DataProcessingException If an exception occurs while accessing the data source.
     */
    List<BookEntity> findByGenreId(Integer genreId);
}
