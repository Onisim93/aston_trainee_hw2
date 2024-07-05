package org.example.servlets.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents an error response object that will be returned to the client in JSON format.
 * Contains fields for HTTP status code and error message.
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
}
