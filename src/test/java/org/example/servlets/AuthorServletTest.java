package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.MockTestData;
import org.example.dto.AuthorDto;
import org.example.dto.BookDto;
import org.example.service.AuthorService;
import org.example.service.BookService;
import org.example.service.mapper.AuthorMapper;
import org.example.service.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthorServletTest {

    @Mock
    private AuthorService service;

    @Mock
    private BookService bookService;

    @Mock
    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private AuthorServlet servlet;

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
    void handlerDelete() {
        doNothing().when(service).deleteById(1);
        when(request.getPathInfo()).thenReturn("/1");
        servlet.doDelete(request, response);
        verify(service, times(1)).deleteById(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void handlerPut() throws IOException {
        AuthorDto source = MockTestData.updatedAuthorDto;

        String sourceJson = MockTestServletData.updatedAuthorDto;

        when(mapper.readValue(any(BufferedReader.class), eq(AuthorDto.class))).thenReturn(source);
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
    void handlerPost() throws IOException {
        AuthorDto source = MockTestData.newAuthorDto;
        AuthorDto sourceCreated = MockTestData.newAuthorCreatedDto;

        String sourceJson = MockTestServletData.newAuthorDto;
        String expected = MockTestServletData.newAuthorCreatedDto;

        when(mapper.readValue(any(BufferedReader.class), eq(AuthorDto.class))).thenReturn(source);
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
    void handlerPostAuthorBook() throws IOException {
        BookDto source = MockTestData.newAuthorBookDto;
        BookDto sourceCreated = MockTestData.newAuthorBookCreatedDto;
        int authorId = sourceCreated.getAuthorId();

        String sourceJson = MockTestServletData.newAuthorBookDto;
        String expected = MockTestServletData.newAuthorBookCreatedDto;

        when(mapper.readValue(any(BufferedReader.class), eq(BookDto.class))).thenReturn(source);
        when(bookService.create(source)).thenReturn(sourceCreated);
        when(request.getPathInfo()).thenReturn("/" + authorId + "/book");

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
    void handlerGetAll() throws IOException {
        String expected = MockTestServletData.allAuthors;

        when(service.findAll()).thenReturn(AuthorMapper.INSTANCE.toDtoList(MockTestData.allAuthors));
        when(request.getPathInfo()).thenReturn("/");

        servlet.doGet(request, response);

        writer.flush();
        String actual = stringWriter.toString().trim();

        assertEquals(expected, actual);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }

    @Test
    void handlerGetBooksByAuthor() throws IOException {
        String expected = MockTestServletData.booksForAuthor1;
        int authorId = 1;

        when(bookService.findByCriteria(authorId, null)).thenReturn(BookMapper.INSTANCE.toDtoList(MockTestData.booksForAuthor1));
        when(request.getPathInfo()).thenReturn("/" + authorId + "/book");

        servlet.doGet(request, response);

        writer.flush();
        String actual = stringWriter.toString().trim();

        assertEquals(expected, actual);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }

    @Test
    void handlerGet() throws IOException {
        AuthorDto authorDto = AuthorMapper.INSTANCE.toDto(MockTestData.author1);
        String expected = MockTestServletData.author1;

        when(service.findById(authorDto.getId())).thenReturn(authorDto);
        when(request.getPathInfo()).thenReturn("/" + authorDto.getId());

        servlet.doGet(request, response);
        writer.flush();

        String actual = stringWriter.toString().trim();

        assertEquals(expected, actual);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response).setContentType("application/json");
    }
}