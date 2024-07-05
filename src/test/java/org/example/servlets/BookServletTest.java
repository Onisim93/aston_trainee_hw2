package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.MockTestData;
import org.example.dto.BookDto;
import org.example.service.BookService;
import org.example.service.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookServletTest {

    @Mock
    private BookService service;
    @Mock
    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private BookServlet servlet;

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
    void handlerPost() throws IOException {
        BookDto source = MockTestData.newBookDto;
        BookDto sourceCreated = MockTestData.newBookCreatedDto;

        String sourceJson = MockTestServletData.newBookDto;
        String expected = MockTestServletData.newBookCreatedDto;

        when(mapper.readValue(any(BufferedReader.class), eq(BookDto.class))).thenReturn(source);
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
        BookDto source = MockTestData.updatedBookDto;

        String sourceJson = MockTestServletData.updatedBookDto;

        when(mapper.readValue(any(BufferedReader.class), eq(BookDto.class))).thenReturn(source);
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

    @Test
    void handlerGetAllByCriteria() throws IOException {
        String expected = MockTestServletData.booksForGenre3AndAuthor2;
        int authorId = 2;
        int genreId = 3;

        when(service.findByCriteria(authorId, genreId)).thenReturn(BookMapper.INSTANCE.toDtoList(MockTestData.booksForGenre3AndAuthor2));
        when(request.getPathInfo()).thenReturn("/");
        when(request.getParameter("authorId")).thenReturn(String.valueOf(authorId));
        when(request.getParameter("genreId")).thenReturn(String.valueOf(genreId));

        servlet.doGet(request, response);

        writer.flush();
        String actual = stringWriter.toString().trim();

        assertEquals(expected, actual);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }

    @Test
    void handlerGetAll() throws IOException {
        String expected = MockTestServletData.allBooks;

        when(service.findAll()).thenReturn(BookMapper.INSTANCE.toDtoList(MockTestData.allBooks));
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
        BookDto bookDto = BookMapper.INSTANCE.toDto(MockTestData.book1);
        String expected = MockTestServletData.book1;

        when(service.findById(bookDto.getId())).thenReturn(bookDto);
        when(request.getPathInfo()).thenReturn("/" + bookDto.getId());

        servlet.doGet(request, response);
        writer.flush();

        String actual = stringWriter.toString().trim();

        assertEquals(expected, actual);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }
}