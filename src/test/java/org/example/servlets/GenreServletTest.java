package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.MockTestData;
import org.example.dto.GenreDto;
import org.example.service.GenreService;
import org.example.service.mapper.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GenreServletTest {

    @Mock
    private GenreService service;
    @Mock
    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private GenreServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private final StringWriter stringWriter = new StringWriter();
    private final PrintWriter writer = new PrintWriter(stringWriter);

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(writer);
        servlet.setMapper(new ObjectMapper());
    }

    @Test
    void handlerGetAll() throws IOException {
        String expected = MockTestServletData.allGenres;

        when(service.findAll()).thenReturn(GenreMapper.INSTANCE.toDtoList(MockTestData.allGenres));
        when(request.getPathInfo()).thenReturn("/");

        servlet.doGet(request, response);

        writer.flush();
        String actual = stringWriter.toString().trim();

        assertEquals(expected, actual);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }

    @Test
    void handlerGet() throws IOException {
        GenreDto genreDto = GenreMapper.INSTANCE.toDto(MockTestData.genre1);
        String expected = MockTestServletData.genre1;

        when(service.findById(genreDto.getId())).thenReturn(genreDto);
        when(request.getPathInfo()).thenReturn("/" + genreDto.getId());

        servlet.doGet(request, response);
        writer.flush();

        String actual = stringWriter.toString().trim();

        assertEquals(expected, actual);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }

    @Test
    void handlerPost() throws IOException {
        GenreDto source = MockTestData.newGenreDto;
        GenreDto sourceCreated = MockTestData.newGenreCreatedDto;

        String sourceJson = MockTestServletData.newGenreDto;
        String expected = MockTestServletData.newGenreCreatedDto;

        when(mapper.readValue(any(BufferedReader.class), eq(GenreDto.class))).thenReturn(source);
        when(service.create(source)).thenReturn(sourceCreated);
        when(request.getPathInfo()).thenReturn("/");

        BufferedReader reader = new BufferedReader(new StringReader(sourceJson));
        when(request.getReader()).thenReturn(reader);

        servlet.doPost(request, response);

        writer.flush();
        String actual = stringWriter.toString().trim();

        assertEquals(expected, actual);
        verify(response).setStatus(HttpServletResponse.SC_CREATED);
        verify(response).setContentType("application/json");
    }

    @Test
    void handlerPut() throws IOException {
        GenreDto source = MockTestData.updatedGenreDto;

        String sourceJson = MockTestServletData.updatedGenreDto;

        when(mapper.readValue(any(BufferedReader.class), eq(GenreDto.class))).thenReturn(source);
        when(service.update(source, source.getId())).thenReturn(source);
        when(request.getPathInfo()).thenReturn("/" + source.getId());

        BufferedReader reader = new BufferedReader(new StringReader(sourceJson));
        when(request.getReader()).thenReturn(reader);

        servlet.doPut(request, response);

        writer.flush();
        String actual = stringWriter.toString().trim();

        assertEquals(sourceJson, actual);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }

    @Test
    void handlerDelete() {
        doNothing().when(service).deleteById(1);
        when(request.getPathInfo()).thenReturn("/1");
        servlet.doDelete(request, response);
        verify(service, times(1)).deleteById(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}