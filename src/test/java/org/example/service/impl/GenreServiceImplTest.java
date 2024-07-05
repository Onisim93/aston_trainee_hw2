package org.example.service.impl;

import org.example.MockTestData;
import org.example.dto.GenreDto;
import org.example.model.GenreEntity;
import org.example.repository.GenreRepository;
import org.example.service.mapper.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class GenreServiceImplTest {

    @Mock
    private GenreRepository repository;

    @InjectMocks
    GenreServiceImpl service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<GenreEntity> source = MockTestData.allGenres;
        List<GenreDto> expected = GenreMapper.INSTANCE.toDtoList(source);

        when(repository.findAll()).thenReturn(source);

        List<GenreDto> actual = service.findAll();

        assertNotNull(actual);
        assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void findById() {
        GenreEntity source = MockTestData.genre1;
        GenreDto expected = GenreMapper.INSTANCE.toDto(source);

        when(repository.findById(source.getId())).thenReturn(source);
        GenreDto actual = service.findById(source.getId());
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void create() {
        GenreEntity source = MockTestData.newGenre;
        GenreEntity sourceCreated = MockTestData.newGenreCreated;

        when(repository.create(source)).thenReturn(sourceCreated);

        GenreDto expected = MockTestData.newGenreCreatedDto;
        GenreDto actual = service.create(GenreMapper.INSTANCE.toDto(source));

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
        GenreEntity source = MockTestData.updatedGenre;

        GenreDto expected = MockTestData.updatedGenreDto;

        when(repository.update(source)).thenReturn(source);

        GenreDto actual = service.update(GenreMapper.INSTANCE.toDto(source), source.getId());

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

}