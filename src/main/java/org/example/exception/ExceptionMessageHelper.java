package org.example.exception;

/**
 * Helper class for generating exception messages with additional context information.
 */
public class ExceptionMessageHelper {

    public static final String ERROR_WHILE_CLOSING_RESULT_SET_MSG = "Error occurred while closing ResultSet";
    public static final String ERROR_WHILE_ROLLBACK_TRANSACTION_MSG = "Error occurred while rolling back transaction";

    private ExceptionMessageHelper() {}

    public static String entityNotFoundMessage(Integer id) {
        return String.format("The entity with id %s was not found.", id);
    }
    public static String invalidUrlMsg(String url) {
        return String.format("Invalid url: %s", url);
    }
}
