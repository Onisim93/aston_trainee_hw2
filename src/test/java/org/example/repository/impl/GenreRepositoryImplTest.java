package org.example.repository.impl;

import org.example.MockTestData;
import org.example.exception.DataProcessingException;
import org.example.exception.EntityNotFoundException;
import org.example.model.GenreEntity;
import org.example.repository.GenreRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreRepositoryImplTest extends RepositoryTest{

    private final GenreRepository genreRepository;

    GenreRepositoryImplTest() {
        this.genreRepository = new GenreRepositoryImpl(connection);
    }


    @Test
    void create() {
        GenreEntity expected = MockTestData.newGenre;
        GenreEntity actual = genreRepository.create(expected);
        expected.setId(11);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createNotValidEntity() {
         GenreEntity notValidEntity = MockTestData.notValidGenre;
         assertThrows(DataProcessingException.class, () -> genreRepository.create(notValidEntity));
    }

    @Test
    void update() {
        GenreEntity expected = MockTestData.updatedGenre;
        GenreEntity actual = genreRepository.update(expected);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateNotValidEntityId() {
        GenreEntity notValidUpdatedEntity = MockTestData.notValidUpdatedGenreId;
        assertThrows(EntityNotFoundException.class, () -> genreRepository.update(notValidUpdatedEntity));
    }

    @Test
    void updateNotValidEntityData() {
        GenreEntity notValidUpdatedEntity = MockTestData.notValidUpdatedGenreData;
        assertThrows(DataProcessingException.class, () -> genreRepository.update(notValidUpdatedEntity));
    }

    @Test
    void findById() {
        GenreEntity expected = MockTestData.genre1;
        GenreEntity actual = genreRepository.findById(expected.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void findByNotExistsId() {
        assertThrows(EntityNotFoundException.class, () -> genreRepository.findById(-1));
    }

    @Test
    void findAll() {
        List<GenreEntity> expected = MockTestData.allGenres;
        List<GenreEntity> actual = genreRepository.findAll();

        assertNotNull(actual);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void deleteById() {
        Integer id = MockTestData.genre10.getId();
        genreRepository.deleteById(id);
        assertThrows(EntityNotFoundException.class, () -> genreRepository.findById(id));
    }
}