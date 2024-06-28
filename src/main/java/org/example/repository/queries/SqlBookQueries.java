package org.example.repository.queries;

public enum SqlBookQueries {

    FIND_BY_ID(getSelectQueryByType("findById")),
    FIND_ALL(getSelectQueryByType("findAll")),
    CREATE("INSERT INTO books (title, description, published_date, isbn, author_id) VALUES (?, ?, ?, ?, ?) RETURNING id"),
    UPDATE("UPDATE books SET title = ?, description = ?, published_date = ?, isbn = ?, author_id = ? WHERE id = ?"),
    UPDATE_AUTHOR_BOOK("UPDATE author_books SET author_id = ? WHERE book_id = ?"),
    DELETE_BOOK_GENRES("DELETE FROM book_genres WHERE book_id = ?"),
    DELETE_AUTHOR_BOOK("DELETE FROM author_books WHERE book_id = ?"),
    DELETE_BY_ID("DELETE FROM book WHERE id = ?"),
    INSERT_GENRE_TO_BOOK_GENRES("INSERT INTO book_genres (book_id, genre_id) VALUES (?, ?)"),
    INSERT_BOOK_TO_AUTHOR_BOOKS("INSERT INTO author_books (author_id, book_id) VALUES (?, ?)");


    public final String query;

    SqlBookQueries(String q) {
        this.query = q;
    }

    private static String getSelectQueryByType(String type) {
        String query = """
                SELECT b.id AS book_id,
                b.title AS book_title,
                b.description AS book_description,
                b.published_date AS book_published_date,
                b.isbn AS book_isbn,
                a.id AS author_id,
                a.name AS author_name,
                a.bio AS author_bio,
                g.id AS genre_id,
                g.name AS genre_name\s
             FROM books b\s
             JOIN authors a ON b.author_id = a.id\s
             LEFT JOIN book_genres bg ON bg.book_id = b.id\s
             LEFT JOIN genres g ON bg.genre_id = g.id\s
             """;
        if (type.equals("findById")) {
            return query + " WHERE b.id = ?";
        }
        else if (type.equals("findAll")) {
            return query + " ORDER BY b.title";
        }

        return query;
    }



}
