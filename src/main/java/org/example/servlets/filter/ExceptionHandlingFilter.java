package org.example.servlets.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.DataAccessException;
import org.example.exception.DataProcessingException;
import org.example.exception.EntityNotFoundException;

import java.io.IOException;

/**
 * Filter that handles exceptions thrown by servlets and provides centralized error handling.
 */
@WebFilter("/*")
public class ExceptionHandlingFilter implements Filter {
    private ObjectMapper mapper;

    /**
     * Initializes the filter.
     * This method is called by the web container when the filter is initialized.
     *
     * @param filterConfig the configuration information for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
        mapper = new ObjectMapper();
    }

    /**
     * Filters requests and handles any exceptions that occur during request processing.
     *
     * @param request the servlet request to be processed
     * @param response the servlet response to be created
     * @param chain the filter chain to continue the request processing
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        }
        catch (DataProcessingException e) {
            sendError((HttpServletResponse) response, HttpServletResponse.SC_CONFLICT, e.getMessage());
        }
        catch (NumberFormatException e) {
            sendError((HttpServletResponse) response, HttpServletResponse.SC_BAD_REQUEST, "Cant resolve id " + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            sendError((HttpServletResponse) response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
        catch (EntityNotFoundException e) {
            sendError((HttpServletResponse) response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        catch (DataAccessException | IOException | ServletException e) {
            sendError((HttpServletResponse) response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Generates a JSON error object with the specified status code and message,
     * and sets it in the HttpServletResponse.
     *
     * @param resp the HttpServletResponse to set the error response
     * @param statusCode the HTTP status code to set in the response
     * @param message the error message to include in the JSON response
     */
    private void sendError(HttpServletResponse resp, int statusCode, String message) {
        try {
            resp.setStatus(statusCode);
            resp.setContentType("application/json");
            ErrorResponse errorResponse = new ErrorResponse(statusCode, message);
            String json = mapper.writeValueAsString(errorResponse);
            resp.getWriter().write(json);
        } catch (IOException e) {
            resp.setStatus(500);
        }
    }
}
