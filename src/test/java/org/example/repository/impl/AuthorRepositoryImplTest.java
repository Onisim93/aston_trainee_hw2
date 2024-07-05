package org.example.repository.impl;

import org.example.MockTestData;
import org.example.exception.DataProcessingException;
import org.example.exception.EntityNotFoundException;
import org.example.model.AuthorEntity;
import org.example.repository.AuthorRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorRepositoryImplTest extends RepositoryTest{


    private final AuthorRepository authorRepository;


    public AuthorRepositoryImplTest() {
        authorRepository = new AuthorRepositoryImpl(connection);
    }

    @Test
    void create() {
        AuthorEntity expected = MockTestData.newAuthor;
        AuthorEntity actual = authorRepository.create(expected);

        expected.setId(MockTestData.newAuthorId);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createNotValidEntity() {
        AuthorEntity notValidEntity = MockTestData.notValidAuthor;
        assertThrows(DataProcessingException.class, () -> authorRepository.create(notValidEntity));
    }

    @Test
    void update() {
        AuthorEntity expected = MockTestData.updatedAuthor;
        AuthorEntity actual = authorRepository.update(expected);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateNotValidEntityId() {
        AuthorEntity notValidUpdatedEntity = MockTestData.notValidUpdatedAuthorId;
        assertThrows(EntityNotFoundException.class, () -> authorRepository.update(notValidUpdatedEntity));
    }

    @Test
    void updateNotValidEntityData() {
        AuthorEntity notValidUpdatedEntity = MockTestData.notValidUpdatedAuthorData;
        assertThrows(DataProcessingException.class, () -> authorRepository.update(notValidUpdatedEntity));
    }

    @Test
    void findById() {
        AuthorEntity expected = MockTestData.author1;

        AuthorEntity actual = authorRepository.findById(expected.getId());
        assertNotNull(actual);

        assertEquals(expected, actual);
    }

    @Test
    void findByNotExistsId() {
        assertThrowsExactly(EntityNotFoundException.class, () -> authorRepository.findById(-1));
    }

    @Test
    void findAll() {
        List<AuthorEntity> expected = MockTestData.allAuthors;
        List<AuthorEntity> actual = authorRepository.findAll();

        assertNotNull(actual);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void deleteById() {
        authorRepository.deleteById(6);
        assertThrows(EntityNotFoundException.class, () -> authorRepository.findById(6));
    }
}