package org.example.repository.queries;

/**
 * Enum representing SQL queries related to genres in the database.
 */
public enum SqlGenreQueries {
    /**
     * SQL query to insert a new genre into the database.
     */
    CREATE("INSERT INTO genres (name, description) VALUES(?,?) RETURNING id"),
    /**
     * SQL query to delete a genre from the database by its ID
     */
    DELETE_BY_ID("DELETE FROM genres WHERE id = ?"),
    /**
     * SQL query to retrieve a genre by its id
     */
    FIND_BY_ID("SELECT * FROM genres WHERE id = ?"),
    /**
     * SQL query to retrieve all genres
     */
    FIND_ALL("SELECT * FROM genres"),
    /**
     * SQL query to update an existing genre in the database.
     */
    UPDATE("UPDATE genres SET name = ?, description = ? WHERE id = ?");

    public final String query;

    /**
     * Constructs an {@code SqlGenreQueries} enum with the specified SQL query.
     * @param query The SQL query string.
     */
    SqlGenreQueries(String query) {
        this.query = query;
    }
}
