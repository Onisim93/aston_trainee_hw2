package org.example.repository.queries;

/**
 * Enum representing SQL queries related to books in the database.
 */
public enum SqlBookQueries {

    /**
     * SQL query to retrieve a book by its id
     */
    FIND_BY_ID(getSelectQueryByType("findById")),
    /**
     * SQL query to retrieve all books
     */
    FIND_ALL(getSelectQueryByType("findAll")),
    /**
     * SQL query to retrieve books by its author_id
     */
    FIND_BY_AUTHOR_ID(getSelectQueryByType("findByAuthorId")),
    /**
     * SQL query to retrieve books that belong to the genre by its id
     */
    FIND_BY_GENRE_ID(getSelectQueryByType("findByGenreId")),
    /**
     * SQL query to retrieve books on the specified criteria
     */
    FIND_BY_CRITERIA(getSelectQueryByType("findByCriteria")),
    /**
     * SQL query to insert a new book into the database.
     */
    CREATE("INSERT INTO books (title, description, published_date, isbn, author_id) VALUES (?, ?, ?, ?, ?) RETURNING id"),
    /**
     * SQL query to update an existing book in the database.
     */
    UPDATE("UPDATE books SET title = ?, description = ?, published_date = ?, isbn = ?, author_id = ? WHERE id = ?"),
    UPDATE_AUTHOR_BOOK("UPDATE author_books SET author_id = ? WHERE book_id = ?"),
    DELETE_BOOK_GENRES("DELETE FROM book_genres WHERE book_id = ?"),
    /**
     * SQL query to delete a book from the database by its ID
     */
    DELETE_BY_ID("DELETE FROM books WHERE id = ?"),
    INSERT_GENRE_TO_BOOK_GENRES("INSERT INTO book_genres (book_id, genre_id) VALUES (?, ?)"),
    INSERT_BOOK_TO_AUTHOR_BOOKS("INSERT INTO author_books (author_id, book_id) VALUES (?, ?)");


    public final String query;

    /**
     * Constructs an {@code SqlBookQueries} enum with the specified SQL query.
     * @param query The SQL query string.
     */
    SqlBookQueries(String query) {
        this.query = query;
    }

    /**
     * Retrieves the SQL SELECT query for the specified type.
     *
     * @param type The type of query to retrieve. Supported types are:
     *             - "findById": Retrieves book by its id
     *             - "findAll: Retrieves all books
     *             - "findByAuthorId": Retrieves books by author ID.
     *             - "findByGenreId": Retrieves books by genre ID.
     *             - "findByCriteria": Retrieves books based on author ID and/or genre ID criteria.
     * @return The SQL SELECT query corresponding to the specified type.
     */
    private static String getSelectQueryByType(String type) {

        String query = """
                SELECT b.id AS book_id,
                b.title AS book_title,
                b.description AS book_description,
                b.published_date AS book_published_date,
                b.isbn AS book_isbn,
                a.id AS author_id,
                a.name AS author_name,
                g.id AS genre_id,
                g.name AS genre_name\s
             FROM books b\s
             JOIN authors a ON b.author_id = a.id\s
             LEFT JOIN book_genres bg ON bg.book_id = b.id\s
             LEFT JOIN genres g ON bg.genre_id = g.id\s
             """;


        StringBuilder resultSQL = new StringBuilder(query);


        String addCriteria = "WHERE b.author_id = ? AND g.id = ? ORDER BY b.title";
        String findByAuthorId = "WHERE b.author_id = ?";
        String findByGenreId = "WHERE g.id = ?";
        return switch (type) {
            case "findById" -> resultSQL.append("WHERE b.id = ?").toString();
            case "findAll" -> resultSQL.append("ORDER BY b.title").toString();
            case "findByCriteria" -> resultSQL.append(addCriteria).toString();
            case "findByAuthorId" -> resultSQL.append(findByAuthorId).toString();
            case "findByGenreId" -> resultSQL.append(findByGenreId).toString();
            default -> query;
        };
    }



}
