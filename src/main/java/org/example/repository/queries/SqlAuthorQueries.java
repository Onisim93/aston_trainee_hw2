package org.example.repository.queries;

/**
 * Enum representing SQL queries related to authors in the database.
 */
public enum SqlAuthorQueries {
    /**
     * SQL query to insert a new author into the database.
     */
    CREATE("INSERT INTO authors (name, bio) VALUES(?,?) RETURNING id"),
    /**
     * SQL query to update an existing author in the database.
     */
    UPDATE("UPDATE authors SET name = ?, bio = ? WHERE id = ?"),
    /**
     * SQL query to delete an author from the database by its ID
     */
    DELETE_BY_ID("DELETE FROM authors WHERE id = ?"),
    /**
     * SQL query to retrieve an author by its id
     */
    FIND_BY_ID("SELECT * FROM authors WHERE id = ?"),
    /**
     * SQL query to retrieve all authors
     */
    FIND_ALL("SELECT * FROM authors"),;

    public final String query;

    /**
     * Constructs an {@code SqlAuthorQueries} enum with the specified SQL query.
     * @param query The SQL query string.
     */
    SqlAuthorQueries(String query) {
        this.query = query;
    }
}
