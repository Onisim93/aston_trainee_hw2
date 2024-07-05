package org.example.service.impl;

import org.example.dto.AuthorDto;
import org.example.repository.AuthorRepository;
import org.example.service.AuthorService;
import org.example.service.mapper.AuthorMapper;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link AuthorService} interface.
 * Provides CRUD operations and additional methods to manage authors.
 */
public class AuthorServiceImpl implements AuthorService {

    /**
     * Repository for accessing author data from the database.
     */
    private final AuthorRepository authorRepository;

    /**
     * Constructs an instance of {@code AuthorServiceImpl} with the specified {@code AuthorRepository}.
     * @param authorRepository The {@link AuthorRepository} implementation to be used by the service.
     */
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> findAll() {
        return AuthorMapper.INSTANCE.toDtoList(authorRepository.findAll());
    }

    @Override
    public AuthorDto findById(Integer id) {
        validateId(id);
        return AuthorMapper.INSTANCE.toDto(authorRepository.findById(id));
    }

    @Override
    public AuthorDto create(AuthorDto entity) {
        validateAuthor(entity);
        return AuthorMapper.INSTANCE.toDto(authorRepository.create(AuthorMapper.INSTANCE.toModel(entity)));
    }

    @Override
    public void deleteById(Integer id) {
        validateId(id);
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDto update(AuthorDto entity, Integer id) {
        validateId(entity.getId());
        validateId(id);
        validateEntityIdAndUrlId(entity.getId(), id);
        validateAuthor(entity);
        return AuthorMapper.INSTANCE.toDto(authorRepository.update(AuthorMapper.INSTANCE.toModel(entity)));
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
     * @param author the entity to validate
     * @throws IllegalArgumentException if the entity is not valid.
     */
    private void validateAuthor(AuthorDto author) {
        StringBuilder errorMessage = new StringBuilder();

        if (author.getName() == null || author.getName().isEmpty()) {
            errorMessage.append("Author name can't be null or empty.");
        }
        if (author.getBio() == null || author.getBio().isEmpty()) {
            if (!errorMessage.isEmpty()) {
                errorMessage.append(" ");
            }
            errorMessage.append("Author bio can't be null or empty.");
        }

        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
