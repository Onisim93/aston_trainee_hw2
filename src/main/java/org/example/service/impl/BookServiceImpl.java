package org.example.service.impl;

import org.example.dto.BookDto;
import org.example.model.BookEntity;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.example.service.mapper.BookMapper;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link BookService} interface.
 * Provides CRUD operations and additional methods to manage books.
 */
public class BookServiceImpl implements BookService {
    /**
     * Repository for accessing book data from the database.
     */
    private final BookRepository bookRepository;

    /**
     * Constructs an instance of {@code BookServiceImpl} with the specified {@code BookRepository}.
     * @param bookRepository The {@link BookRepository} implementation to be used by the service.
     */
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> findAll() {
        return BookMapper.INSTANCE.toDtoList(bookRepository.findAll());
    }

    @Override
    public List<BookDto> findByCriteria(Integer authorId, Integer genreId) {
        List<BookEntity> books;

        if (!Objects.isNull(authorId) && !Objects.isNull(genreId)) {
            books = bookRepository.findByCriteria(authorId, genreId);
        }
        else if (!Objects.isNull(authorId)) {
            books = bookRepository.findByAuthorId(authorId);
        }
        else {
            books = bookRepository.findByGenreId(genreId);
        }


        return BookMapper.INSTANCE.toDtoList(books);
    }

    @Override
    public BookDto findById(Integer id) {
        validateId(id);
        return BookMapper.INSTANCE.toDto(bookRepository.findById(id));
    }

    @Override
    public BookDto create(BookDto entity) {
        validateBook(entity);
        return BookMapper.INSTANCE.toDto(bookRepository.create(BookMapper.INSTANCE.toModel(entity)));
    }

    @Override
    public void deleteById(Integer id) {
        validateId(id);
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(BookDto entity, Integer id) {
        validateId(entity.getId());
        validateId(id);
        validateEntityIdAndUrlId(entity.getId(), id);
        validateBook(entity);
        return BookMapper.INSTANCE.toDto(bookRepository.update(BookMapper.INSTANCE.toModel(entity)));
    }

    /**
     * Validates if the provided ID is valid (i.e., not null and greater than zero).
     *
     * @param id the ID to validate
     * @throws IllegalArgumentException if the ID is null or less than or equal to zero
     */
    private void validateId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id can't be null or empty");
        }
    }

    /**
     * Validates if the provided entity ID matches the URL ID.
     *
     * @param entityId the ID of the entity to validate
     * @param urlId ID from the URL to compare against
     * @throws IllegalArgumentException if entityId is not equal to urlId
     */
    private void validateEntityIdAndUrlId(Integer entityId, Integer urlId) {
        if (!Objects.equals(entityId, urlId)) {
            throw new IllegalArgumentException("Id in path and ID in body are different");
        }
    }


    /**
     * Validates if the provided entity is valid.
     *
     * @param book the entity to validate
     * @throws IllegalArgumentException if the entity is not valid.
     */
    private void validateBook(BookDto book) {
        StringBuilder errorMessage = new StringBuilder();

       if (book.getTitle() == null || book.getTitle().isEmpty()) {
            errorMessage.append("Book title can't be null or empty.");
        }
        if (book.getDescription() == null || book.getDescription().isEmpty()) {
            if (!errorMessage.isEmpty()) {
                errorMessage.append(" ");
            }
            errorMessage.append("Book description can't be null or empty.");
        }
        if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
            if (!errorMessage.isEmpty()) {
                errorMessage.append(" ");
            }
            errorMessage.append("Book isbn can't be null or empty");
        }

        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException(errorMessage.toString());
        }

    }
}
