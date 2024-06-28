package org.example.repository.queries;

public enum SqlGenreQueries {
    CREATE("INSERT INTO genres (name, description) VALUES(?,?) RETURNING id"),
    DELETE_BY_ID("DELETE FROM genres WHERE id = ?"),
    FIND_BY_ID("SELECT * FROM genres WHERE id = ?"),
    FIND_ALL("SELECT * FROM genres"),
    UPDATE("UPDATE genres SET name = ?, description = ? WHERE id = ?");

    public final String query;

    SqlGenreQueries(String query) {
        this.query = query;
    }
}
