package org.example.service.impl;

import org.example.MockTestData;
import org.example.dto.AuthorDto;
import org.example.model.AuthorEntity;
import org.example.repository.AuthorRepository;
import org.example.service.mapper.AuthorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<AuthorDto> expected = AuthorMapper.INSTANCE.toDtoList(MockTestData.allAuthors);

        when(repository.findAll()).thenReturn(MockTestData.allAuthors);

        List<AuthorDto> actual = service.findAll();

        assertNotNull(actual);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void findById() {
        AuthorDto expected = AuthorMapper.INSTANCE.toDto(MockTestData.author1);
        int id = expected.getId();

        when(repository.findById(id)).thenReturn(MockTestData.author1);

        AuthorDto actual = service.findById(id);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void create() {
        AuthorEntity source = MockTestData.newAuthor;
        AuthorEntity sourceCreated = MockTestData.newAuthorCreated;

        when(repository.create(source)).thenReturn(sourceCreated);

        AuthorDto expected = MockTestData.newAuthorCreatedDto;
        AuthorDto actual = service.create(AuthorMapper.INSTANCE.toDto(source));

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
        AuthorEntity updatedAuthor = MockTestData.updatedAuthor;
        AuthorDto expected = MockTestData.updatedAuthorDto;

        when(repository.update(updatedAuthor)).thenReturn(updatedAuthor);

        AuthorDto actual = service.update(AuthorMapper.INSTANCE.toDto(updatedAuthor), updatedAuthor.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}