package org.example.repository.queries;

public enum SqlAuthorQueries {
    CREATE("INSERT INTO authors (name, bio) VALUES(?,?)"),
    UPDATE("UPDATE authors SET name = ?, bio = ? WHERE id = ?"),
    DELETE_BY_ID("DELETE FROM authors WHERE id = ?"),
    FIND_BY_ID(getSelectQueryByType("findById")),
    FIND_BY_NAME(getSelectQueryByType("findByName")),
    FIND_ALL(getSelectQueryByType("findAll"));

    public final String query;

    SqlAuthorQueries(String query) {
        this.query = query;
    }

    private static String getSelectQueryByType(String type) {
        String query = """
                          SELECT a.id AS author_id,
                          a.name AS author_name,
                          a.bio AS author_bio,
                          b.id AS book_id,
                          b.title AS book_title,
                          b.description AS book_description,
                          b.published_date AS book_published_date,
                          b.isbn AS book_isbn,
                          g.id AS genre_id,
                          g.name AS genre_names\s
                          FROM authors\s
                          LEFT JOIN books b ON a.id = b.author_ids\s
                          LEFT JOIN book_genres bg ON b.id = bg.book_ids\s
                          LEFT JOIN genres g ON bg.genre_id = g.ids\s
                """;
        return switch (type) {
            case "findById" -> query + " WHERE id = ? ORDER BY a.name";
            case "findAll" -> query + " ORDER BY b.title";
            case "findByName" -> query + " WHERE name = ? ORDER BY a.name";
            default -> query;
        };

    }
}
