package org.example.service;

import org.example.dto.BookDto;

import java.util.List;
/**
 * Service interface for managing books.
 * Extends the {@link BaseService} interface for CRUD operations.
 */
public interface BookService extends BaseService<BookDto, Integer>{
    List<BookDto> findByCriteria(Integer authorId, Integer genreId);
}
