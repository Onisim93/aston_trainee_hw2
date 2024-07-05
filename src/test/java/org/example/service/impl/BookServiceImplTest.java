package org.example.service.impl;

import org.example.MockTestData;
import org.example.dto.BookDto;
import org.example.model.BookEntity;
import org.example.repository.BookRepository;
import org.example.service.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    BookRepository repository;

    @InjectMocks
    BookServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByCriteria() {
        List<BookEntity> source = MockTestData.booksForGenre3AndAuthor2;
        Integer authorId = 2;
        Integer genreId = 3;

        when(repository.findByCriteria(authorId, genreId)).thenReturn(source);

        List<BookDto> expected = BookMapper.INSTANCE.toDtoList(source);

        List<BookDto> actual = service.findByCriteria(authorId, genreId);

        assertNotNull(actual);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));

    }

    @Test
    void findAll() {
        List<BookDto> expected = BookMapper.INSTANCE.toDtoList(MockTestData.allBooks);

        when(repository.findAll()).thenReturn(MockTestData.allBooks);

        List<BookDto> actual = service.findAll();

        assertNotNull(actual);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void findById() {
        BookDto expected = BookMapper.INSTANCE.toDto(MockTestData.book1);
        int id = expected.getId();

        when(repository.findById(id)).thenReturn(MockTestData.book1);

        BookDto actual = service.findById(id);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void create() {
        BookEntity source = MockTestData.newBook;
        BookEntity sourceCreated = MockTestData.newBookCreated;

        BookDto expected = MockTestData.newBookCreatedDto;

        when(repository.create(source)).thenReturn(sourceCreated);

        BookDto actual = service.create(BookMapper.INSTANCE.toDto(source));

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void deleteById() {
        doNothing().when(repository).deleteById(1);
        service.deleteById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void update() {
        BookEntity updatedBook = MockTestData.updatedBook;
        BookDto expected = MockTestData.updatedBookDto;

        when(repository.update(updatedBook)).thenReturn(updatedBook);

        BookDto actual = service.update(BookMapper.INSTANCE.toDto(updatedBook), updatedBook.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}