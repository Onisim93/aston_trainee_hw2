package org.example.repository.impl;

import org.example.MockTestData;
import org.example.exception.DataProcessingException;
import org.example.exception.EntityNotFoundException;
import org.example.model.BookEntity;
import org.example.repository.BookRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryImplTest extends RepositoryTest{

    private final BookRepository bookRepository;

    BookRepositoryImplTest() {
        this.bookRepository = new BookRepositoryImpl(connection);
    }

    @Test
    void create() {
        BookEntity expected = MockTestData.newBook;
        BookEntity actual = bookRepository.create(expected);
        expected.setId(16);


        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void createNotValidEntity() {
        BookEntity notValidEntity = MockTestData.notValidBook;
        assertThrows(DataProcessingException.class, () -> bookRepository.create(notValidEntity));
    }

    @Test
    void update() {
        BookEntity expected = MockTestData.updatedBook;
        BookEntity actual = bookRepository.update(expected);
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void updateNotValidEntityId() {
        BookEntity notValidUpdatedEntity = MockTestData.notValidUpdatedBookId;
        assertThrows(EntityNotFoundException.class, () -> bookRepository.update(notValidUpdatedEntity));
    }

    @Test
    void updateNotValidEntityData() {
        BookEntity notValidUpdatedEntity = MockTestData.notValidUpdatedBookData;
        assertThrows(DataProcessingException.class, () -> bookRepository.update(notValidUpdatedEntity));
    }

    @Test
    void findById() {
        BookEntity expected = MockTestData.book1;
        BookEntity actual = bookRepository.findById(expected.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void findByNotExistsId() {
      assertThrows(EntityNotFoundException.class, () -> bookRepository.findById(-1));
    }

    @Test
    void findAll() {
        List<BookEntity> expected = MockTestData.allBooks;
        List<BookEntity> actual = bookRepository.findAll();

        assertNotNull(actual);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void deleteById() {
        Integer id = MockTestData.book1.getId();
        bookRepository.deleteById(id);
        assertThrows(EntityNotFoundException.class, () -> bookRepository.findById(id));
    }

}