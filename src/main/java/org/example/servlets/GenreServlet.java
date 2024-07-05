package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.GenreDto;
import org.example.exception.ExceptionMessageHelper;
import org.example.service.GenreService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation for handling CRUD operations related to genres.
 * Extends the BaseServlet and provides implementations for the abstract handler methods.
 */
@WebServlet("/api/genre/*")
public class GenreServlet extends BaseServlet {

    private GenreService genreService;

    @Override
    public void init() throws ServletException {
        genreService = (GenreService) getServletContext().getAttribute("genreService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            handlerGetAll(resp);
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
    protected void handlerGetAll(HttpServletResponse resp) throws IOException {
        List<GenreDto> authors = genreService.findAll();
        String jsonResponse = mapper.writeValueAsString(authors);
        writeJsonToResponse(resp, jsonResponse);
    }

    @Override
    protected void handlerGet(HttpServletResponse resp, Integer genreId) throws IOException {
        GenreDto genreDto = genreService.findById(genreId);
        String jsonResponse = mapper.writeValueAsString(genreDto);
        writeJsonToResponse(resp, jsonResponse);
    }

    @Override
    protected void handlerPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GenreDto genreDto = mapper.readValue(req.getReader(), GenreDto.class);
        GenreDto created = genreService.create(genreDto);
        String json = mapper.writeValueAsString(created);
        writeJsonToResponse(resp, json);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void handlerPut(HttpServletRequest req, HttpServletResponse resp, Integer genreId) throws IOException {
        GenreDto genreDto = mapper.readValue(req.getReader(), GenreDto.class);
        GenreDto updated = genreService.update(genreDto, genreId);
        String json = mapper.writeValueAsString(updated);
        writeJsonToResponse(resp, json);
    }

    @Override
    protected void handlerDelete(HttpServletResponse resp, Integer genreId) {
        genreService.deleteById(genreId);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
