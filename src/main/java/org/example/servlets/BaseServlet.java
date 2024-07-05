package org.example.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ExceptionMessageHelper;

import java.io.IOException;

/**
 * Abstract base servlet class that provides common handling for HTTP methods
 * (GET, POST, PUT, DELETE) and defines abstract handler methods to be implemented
 * by subclasses.
 */
@Setter
@Slf4j
public abstract class BaseServlet extends HttpServlet {

    protected ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles HTTP GET requests. Determines whether to retrieve a single entity by ID
     * or to retrieve all entities.
     *
     * @param req the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the GET request
     */
    @Override
    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    /**
     * Handles HTTP PUT requests.
     *
     * @param req the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the PUT request
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String[] paths = pathInfo.split("/");
        if (paths.length == 2) {
            handlerPut(req, resp, parseUrlId(paths[1]));
        } else {
            throw new IllegalArgumentException(ExceptionMessageHelper.invalidUrlMsg(pathInfo));
        }
    }

    /**
     * Handles HTTP DELETE requests.
     *
     * @param req the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        String[] paths = pathInfo.split("/");
        if (paths.length == 2) {
            handlerDelete(resp, parseUrlId(paths[1]));
        } else {
            throw new IllegalArgumentException(ExceptionMessageHelper.invalidUrlMsg(pathInfo));
        }
    }

    /**
     * Handles HTTP POST requests.
     *
     * @param req the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the POST request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            handlerPost(req, resp);
        }
        else {
            throw new IllegalArgumentException(ExceptionMessageHelper.invalidUrlMsg(pathInfo));
        }
    }

    /**
     * Abstract method to handle HTTP POST requests. To be implemented by subclasses.
     *
     * @param req the HttpServletRequest containing the entity data
     * @param resp the HttpServletResponse for sending the response
     * @throws IOException if an I/O error occurs while handling the request
     */
    protected abstract void handlerPost(HttpServletRequest req, HttpServletResponse resp) throws IOException;

    /**
     * Abstract method to handle HTTP GET requests. To be implemented by subclasses.
     *
     * @param resp the HttpServletResponse for sending the response
     * @throws IOException if an I/O error occurs while handling the request
     */
    protected abstract void handlerGet(HttpServletResponse resp, Integer entityId) throws IOException;

    /**
     * Abstract method to handle HTTP PUT requests. To be implemented by subclasses.
     *
     * @param req the HttpServletRequest containing the updated entity data
     * @param resp the HttpServletResponse for sending the response
     * @param entityId id of an existing entity
     * @throws IOException if an I/O error occurs while handling the request
     */
    protected abstract void handlerPut(HttpServletRequest req, HttpServletResponse resp, Integer entityId) throws IOException;

    /**
     * Abstract method to handle HTTP DELETE requests. To be implemented by subclasses.
     *
     * @param resp the HttpServletResponse for sending the response
     * @param entityId id of the entity to delete
     */
    protected abstract void handlerDelete(HttpServletResponse resp, Integer entityId);

    /**
     * Abstract method to handle HTTP GET requests to retrieve all entities. To be implemented by subclasses.
     *
     * @param resp the HttpServletResponse for sending the response
     * @throws IOException if an I/O error occurs while handling the request
     */
    protected abstract void handlerGetAll(HttpServletResponse resp) throws IOException;

    /**
     * Writes the given JSON string to the HttpServletResponse with appropriate headers.
     *
     * @param resp the HttpServletResponse to which the JSON will be written
     * @param json the JSON string to write to the response
     * @throws IOException if an input or output error occurs while writing the JSON to the response
     */
    protected void writeJsonToResponse(HttpServletResponse resp, String json) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
    }

    /**
     * Parses the given URL ID string and converts it to an Integer.
     * If the input is not a valid integer, an IllegalArgumentException is thrown.
     *
     * @param urlId the ID string from the URL to be parsed
     * @return the parsed integer value
     * @throws IllegalArgumentException if the input string is not a valid integer
     */
    protected Integer parseUrlId(String urlId) {
        try {
            return Integer.parseInt(urlId);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid ID: " + urlId + ". It must be a positive number.");
        }
    }
}
