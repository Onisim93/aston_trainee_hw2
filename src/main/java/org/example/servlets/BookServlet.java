package org.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.BookDto;
import org.example.exception.ExceptionMessageHelper;
import org.example.service.BookService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation for handling CRUD operations related to books.
 * Extends the BaseServlet and provides implementations for the abstract handler methods.
 */
@WebServlet("/api/book/*")
public class BookServlet extends BaseServlet {

    private BookService bookService;

    @Override
    public void init() {
        this.bookService = (BookService) getServletContext().getAttribute("bookService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String authorId = req.getParameter("authorId");
        String genreId = req.getParameter("genreId");

        if (pathInfo == null || pathInfo.equals("/")) {
            if (authorId == null && genreId == null) {
                handlerGetAll(resp);
            } else {
                handlerGetAllByCriteria(resp, authorId, genreId);
            }
        } else {
            String[] paths = pathInfo.split("/");
            if (paths.length == 2) {
                handlerGet(resp, parseUrlId(paths[1]));
            } else {
                throw new IllegalArgumentException(ExceptionMessageHelper.invalidUrlMsg(pathInfo));
            }
        }
    }

    @Override
    protected void handlerPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BookDto bookDto = mapper.readValue(req.getReader(), BookDto.class);
        BookDto created = bookService.create(bookDto);
        String json = mapper.writeValueAsString(created);
        writeJsonToResponse(resp, json);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void handlerPut(HttpServletRequest req, HttpServletResponse resp, Integer bookId) throws IOException {
        BookDto bookDto = mapper.readValue(req.getReader(), BookDto.class);
        BookDto updated = bookService.update(bookDto, bookId);
        String json = mapper.writeValueAsString(updated);
        writeJsonToResponse(resp, json);
    }

    @Override
    protected void handlerDelete(HttpServletResponse resp, Integer bookId) {
        bookService.deleteById(bookId);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * Handles HTTP GET requests to retrieve a list of books based on specified criteria.
     * This method extracts criteria from the request parameters and uses them to filter
     * the list of books returned.
     *
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @param authorId the id of the author to filter books by.
     * @param genreId the id of the genre to filter books by.
     * @throws IOException if an input or output error occurs while the servlet is handling the GET request
     */
    protected void handlerGetAllByCriteria(HttpServletResponse resp, String authorId, String genreId) throws IOException {
        Integer aId = authorId != null ? Integer.parseInt(authorId) : null;
        Integer gId = genreId != null ? Integer.parseInt(genreId) : null;
        List<BookDto> books = bookService.findByCriteria(aId, gId);

        String json = mapper.writeValueAsString(books);
        writeJsonToResponse(resp, json);
    }

    @Override
    protected void handlerGetAll(HttpServletResponse resp) throws IOException {
        List<BookDto> books = bookService.findAll();
        String json = mapper.writeValueAsString(books);
        writeJsonToResponse(resp, json);
    }


    @Override
    protected void handlerGet(HttpServletResponse resp, Integer id) throws IOException {
        BookDto book = bookService.findById(id);
        String json = mapper.writeValueAsString(book);
        writeJsonToResponse(resp, json);
    }

}
