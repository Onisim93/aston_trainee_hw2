package org.example.service.impl;

import org.example.dto.GenreDto;
import org.example.repository.GenreRepository;
import org.example.service.GenreService;
import org.example.service.mapper.GenreMapper;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link GenreService} interface.
 * Provides CRUD operations and additional methods to manage genres.
 */
public class GenreServiceImpl implements GenreService {

    /**
     * Repository for accessing genre data from the database.
     */
    private final GenreRepository genreRepository;

    /**
     * Constructs an instance of {@code GenreServiceImpl} with the specified {@code GenreRepository}.
     * @param genreRepository The {@link GenreRepository} implementation to be used by the service.
     */
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> findAll() {
        return GenreMapper.INSTANCE.toDtoList(genreRepository.findAll());
    }

    @Override
    public GenreDto findById(Integer id) {
        validateId(id);
        return GenreMapper.INSTANCE.toDto(genreRepository.findById(id));
    }

    @Override
    public GenreDto create(GenreDto entity) {
        validateGenre(entity);
        return GenreMapper.INSTANCE.toDto(genreRepository.create(GenreMapper.INSTANCE.toModel(entity)));
    }

    @Override
    public void deleteById(Integer id) {
        validateId(id);
        genreRepository.deleteById(id);
    }

    @Override
    public GenreDto update(GenreDto entity, Integer id) {
        validateId(entity.getId());
        validateId(id);
        validateEntityIdAndPathId(entity.getId(), id);
        validateGenre(entity);
        return GenreMapper.INSTANCE.toDto(GenreMapper.INSTANCE.toModel(entity));
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
    private void validateEntityIdAndPathId(Integer entityId, Integer urlId) {
        if (!Objects.equals(entityId, urlId)) {
            throw new IllegalArgumentException("Id in path and ID in body are different");
        }
    }

    /**
     * Validates if the provided entity is valid.
     *
     * @param genre the entity to validate
     * @throws IllegalArgumentException if the entity is not valid.
     */
    public void validateGenre(GenreDto genre) {
        StringBuilder errorMessage = new StringBuilder();

        if (genre.getName() == null || genre.getName().isEmpty()) {
            errorMessage.append("Genre name can't be null or empty.");
        }
        if (genre.getDescription() == null || genre.getDescription().isEmpty()) {
            if (!errorMessage.isEmpty()) {
                errorMessage.append(" ");
            }
            errorMessage.append("Genre description can't be null or empty.");
        }

        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }
}
