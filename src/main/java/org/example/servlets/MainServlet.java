package org.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * MainServlet class that handles requests to the root path ('/') and
 * returns a welcome message about the RESTful Service.
 */
@WebServlet("/api")
public class MainServlet extends HttpServlet {

    /**
     * Handles HTTP GET requests to the root path ('/') and returns a welcome message.
     * This method sets the content type to "application/json", character encoding to "UTF-8",
     * and status to 200 (OK). It writes a JSON-formatted welcome message to the response.
     *
     * @param req the HttpServletRequest object that contains the request the client made to the servlet
     * @param resp the HttpServletResponse object that contains the response the servlet returns to the client
     * @throws IOException if an input or output error occurs while the servlet is handling the GET request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getRequestURI();
        if (pathInfo == null || pathInfo.endsWith("api")) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(getJsonMessage());
        }
        else {
            throw new IllegalArgumentException("Invalid url: " + pathInfo);
        }
    }

    /**
     * Returns a welcome message in JSON format.
     * This method generates a simple JSON-formatted string containing a welcome message
     * that will be returned to clients when they access the root path of the service.
     *
     * @return a String containing the welcome message formatted as JSON
     */
    private String getJsonMessage() {
       return "{"
               + "\"name\": \"My RESTful Service\","
               + "\"version\": \"1.0.0\","
               + "\"description\": \"Welcome to My RESTful Service. Below are the available resources and their respective endpoints.\","
               + "\"resources\": ["
               + "{"
               + "\"name\": \"Authors\","
               + "\"endpoints\": ["
               + "{"
               + "\"url\": \"/author\","
               + "\"methods\": {"
               + "\"GET\": \"Retrieve all authors\","
               + "\"POST\": \"Create a new author\""
               + "},"
               + "\"fields\": {"
               + "\"POST\": {"
               + "\"name\": \"String (required), max length 255\","
               + "\"bio\": \"String (required, max length 1024)\""
               + "}"
               + "}"
               + "},"
               + "{"
               + "\"url\": \"/author/{id}\","
               + "\"methods\": {"
               + "\"GET\": \"Retrieve author by ID\","
               + "\"PUT\": \"Update author by ID\","
               + "\"DELETE\": \"Delete author by ID\""
               + "},"
               + "\"fields\": {"
               + "\"PUT\": {"
               + "\"name\": \"String (required, max length 255)\","
               + "\"bio\": \"String (required, max length 1024)\""
               + "}"
               + "}"
               + "},"
               + "{"
               + "\"url\": \"/author/{id}/book\","
               + "\"methods\": {"
               + "\"GET\": \"Retrieve all books of the author by ID\","
               + "\"POST\": \"Add a new book with the specified author\""
               + "},"
               + "\"fields\": {"
               + "\"POST\": {"
               + "\"title\": \"String (required, max length 255)\","
               + "\"description\": \"String (required, max length 1024)\","
               + "\"publishedDate\": \"String (required, max length 255)\","
               + "\"isbn\": \"String (required, max length 30, UNIQUE)\","
               + "\"genreIds\": \"List Integer (required, at least existing 1 genre ID)\""
               + "}"
               + "}"
               + "}"
               + "]"
               + "},"
               + "{"
               + "\"name\": \"Books\","
               + "\"endpoints\": ["
               + "{"
               + "\"url\": \"/book\","
               + "\"methods\": {"
               + "\"GET\": \"Retrieve all books (optional filters: authorId, genreId)\","
               + "\"POST\": \"Create a new book\""
               + "},"
               + "\"fields\": {"
               + "\"POST\": {"
               + "\"title\": \"String (required, max length 255)\","
               + "\"description\": \"String (required, max length 1024)\","
               + "\"publishedDate\": \"String (required, max length 255)\","
               + "\"isbn\": \"String (required, max length 30, UNIQUE)\","
               + "\"authorId\": \"Integer (required, existing author id)\","
               + "\"genreIds\": \"List Integer (required, at least existing 1 genre ID)\""
               + "}"
               + "}"
               + "},"
               + "{"
               + "\"url\": \"/book/{id}\","
               + "\"methods\": {"
               + "\"GET\": \"Retrieve book by ID\","
               + "\"PUT\": \"Update book by ID\","
               + "\"DELETE\": \"Delete book by ID\""
               + "},"
               + "\"fields\": {"
               + "\"PUT\": {"
               + "\"title\": \"String (required, max length 255)\","
               + "\"description\": \"String (required, max length 1024)\","
               + "\"publishedDate\": \"String (required, max length 255)\","
               + "\"isbn\": \"String (required, max length 30, UNIQUE)\","
               + "\"authorId\": \"Integer (required, existing author id)\","
               + "\"genreIds\": \"List Integer (required, at least existing 1 genre ID)\""
               + "}"
               + "}"
               + "}"
               + "]"
               + "},"
               + "{"
               + "\"name\": \"Genres\","
               + "\"endpoints\": ["
               + "{"
               + "\"url\": \"/genre\","
               + "\"methods\": {"
               + "\"GET\": \"Retrieve all genres\","
               + "\"POST\": \"Create a new genre\""
               + "},"
               + "\"fields\": {"
               + "\"POST\": {"
               + "\"name\": \"String (required)\","
               + "\"description\": \"String (required, max length 1024)\""
               + "}"
               + "}"
               + "},"
               + "{"
               + "\"url\": \"/genre/{id}\","
               + "\"methods\": {"
               + "\"GET\": \"Retrieve genre by ID\","
               + "\"PUT\": \"Update genre by ID\","
               + "\"DELETE\": \"Delete genre by ID\""
               + "},"
               + "\"fields\": {"
               + "\"PUT\": {"
               + "\"name\": \"String (required)\","
               + "\"description\": \"String (required, max length 1024)\""
               + "}"
               + "}"
               + "}"
               + "]"
               + "}"
               + "]"
               + "}";
    }

}
