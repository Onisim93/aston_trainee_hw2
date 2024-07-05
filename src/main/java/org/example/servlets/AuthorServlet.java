package org.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.AuthorDto;
import org.example.dto.BookDto;
import org.example.exception.ExceptionMessageHelper;
import org.example.service.AuthorService;
import org.example.service.BookService;

import java.io.IOException;
import java.util.List;
/**
 * Servlet implementation for handling CRUD operations related to authors.
 * Extends the BaseServlet and provides implementations for the abstract handler methods.
 */
@WebServlet("/api/author/*")
public class AuthorServlet extends BaseServlet {

    private AuthorService authorService;
    private BookService bookService;

    @Override
    public void init() {
        this.authorService = (AuthorService) getServletContext().getAttribute("authorService");
        this.bookService = (BookService) getServletContext().getAttribute("bookService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();


        if (pathInfo == null || pathInfo.equals("/")) {
            handlerGetAll(resp);
        }

        else {
            String[] paths = pathInfo.split("/");
            if (paths.length == 2) {
                handlerGet(resp, parseUrlId(paths[1]));
            } else if (paths.length == 3 && paths[2].equals("book")) {
                handlerGetBooksByAuthorId(resp, parseUrlId(paths[1]));
            }
            else {
                throw new IllegalArgumentException(ExceptionMessageHelper.invalidUrlMsg(pathInfo));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                handlerPost(req, resp);
            } else {
                String[] paths = pathInfo.split("/");
                if (paths.length == 3 && paths[2].equals("book")) {
                    handlerPostAuthorBook(req, resp, parseUrlId(paths[1]));
                } else {
                    throw new IllegalArgumentException(ExceptionMessageHelper.invalidUrlMsg(pathInfo));
                }
            }


    }


    @Override
    protected void handlerDelete(HttpServletResponse resp, Integer authorId) {
        authorService.deleteById(authorId);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void handlerPut(HttpServletRequest req, HttpServletResponse resp, Integer authorId) throws IOException {
        AuthorDto authorDto = mapper.readValue(req.getReader(), AuthorDto.class);
        AuthorDto updated = authorService.update(authorDto, authorId);
        String json = mapper.writeValueAsString(updated);
        writeJsonToResponse(resp, json);
    }

    @Override
    protected void handlerPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AuthorDto authorDto = mapper.readValue(req.getReader(), AuthorDto.class);
        AuthorDto created = authorService.create(authorDto);
        String json = mapper.writeValueAsString(created);
        writeJsonToResponse(resp, json);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    private void handlerPostAuthorBook(HttpServletRequest req, HttpServletResponse resp, int authorId) throws IOException {
        BookDto bookDto = mapper.readValue(req.getReader(), BookDto.class);
        bookDto.setAuthorId(authorId);
        BookDto created = bookService.create(bookDto);
        String json = mapper.writeValueAsString(created);
        writeJsonToResponse(resp, json);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }


    @Override
    protected void handlerGetAll(HttpServletResponse resp) throws IOException {
        List<AuthorDto> authors = authorService.findAll();
        String jsonResponse = mapper.writeValueAsString(authors);
        writeJsonToResponse(resp, jsonResponse);
    }

    private void handlerGetBooksByAuthorId(HttpServletResponse resp, int authorId) throws IOException {
        List<BookDto> books = bookService.findByCriteria(authorId, null);
        String json = mapper.writeValueAsString(books);
        writeJsonToResponse(resp, json);

    }

    @Override
    protected void handlerGet(HttpServletResponse resp, Integer authorId) throws IOException {
        AuthorDto authorDto = authorService.findById(authorId);
        String jsonResponse = mapper.writeValueAsString(authorDto);
        writeJsonToResponse(resp, jsonResponse);
    }
}
