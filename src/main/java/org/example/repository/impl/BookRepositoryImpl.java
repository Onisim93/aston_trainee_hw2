package org.example.repository.impl;

import org.example.model.AuthorEntity;
import org.example.model.BookEntity;
import org.example.model.GenreEntity;
import org.example.repository.BookRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.repository.queries.SqlBookQueries.*;

public class BookRepositoryImpl implements BookRepository {

    private final Connection connection;

    public BookRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public BookEntity create(BookEntity entity) {
        try (
                PreparedStatement insertBookSt = connection.prepareStatement(CREATE.query);
                PreparedStatement insertBookGenresSt = connection.prepareStatement(INSERT_GENRE_TO_BOOK_GENRES.query);
                PreparedStatement insertBookToAuthorBooksSt = connection.prepareStatement(INSERT_BOOK_TO_AUTHOR_BOOKS.query)
        ) {
            connection.setAutoCommit(false);

            fillStatementWithBookFields(insertBookSt, entity);
            ResultSet generatedId = insertBookSt.executeQuery();


            if (generatedId.next()) {
                int bookId = generatedId.getInt(1);
                insertBookToAuthorBooksSt.setInt(1, entity.getAuthor().getId());
                insertBookToAuthorBooksSt.setInt(2, bookId);
                insertBookToAuthorBooksSt.executeUpdate();

                insertBookGenresSt.setInt(1, bookId);
                for (GenreEntity g : entity.getGenres()) {
                    insertBookGenresSt.setInt(2, g.getId());
                    insertBookGenresSt.addBatch();
                }
                insertBookGenresSt.executeBatch();


                entity.setId(bookId);
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public BookEntity update(BookEntity entity) {

        try (
                PreparedStatement updateBookSt = connection.prepareStatement(UPDATE.query);
                PreparedStatement updateAuthorBookSt = connection.prepareStatement(UPDATE_AUTHOR_BOOK.query);
                PreparedStatement deleteOldBookGenresSt = connection.prepareStatement(DELETE_BOOK_GENRES.query);
                PreparedStatement insertBookGenresSt = connection.prepareStatement(INSERT_GENRE_TO_BOOK_GENRES.query)) {

            connection.setAutoCommit(false);

            // Обновление основных данных сущности
            fillStatementWithBookFields(updateBookSt, entity);
            updateBookSt.executeUpdate();

            // Обновление связи книги с автором

            updateAuthorBookSt.setInt(1, entity.getAuthor().getId());
            updateAuthorBookSt.setInt(2, entity.getId());
            updateAuthorBookSt.executeUpdate();

            // Удаление старых связей книги с жанрами
            deleteOldBookGenresSt.setInt(1, entity.getId());
            deleteOldBookGenresSt.executeUpdate();

            // Добавление новых связей книги и жанров
            insertBookGenresSt.setInt(1, entity.getId());

            for (GenreEntity genre : entity.getGenres()) {
                insertBookGenresSt.setInt(2, genre.getId());
                insertBookGenresSt.addBatch();
            }

            insertBookGenresSt.executeBatch();

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public BookEntity findById(Integer id) {
        Map<Integer, BookEntity> map = new HashMap<>();


        try (PreparedStatement st = connection.prepareStatement(FIND_BY_ID.query)) {

            st.setInt(1, id);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                BookEntity entity = getBookFromResultSet(rs);

                if (map.containsKey(id)) {
                    map.get(id).getGenres().addAll(entity.getGenres());
                } else {
                    map.put(id, entity);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return map.get(id);
    }

    @Override
    public List<BookEntity> findAll() {
        Map<Integer, BookEntity> map = new HashMap<>();

        try (PreparedStatement st = connection.prepareStatement(FIND_ALL.query)
        ) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                BookEntity entity = getBookFromResultSet(rs);

                if (map.containsKey(entity.getId())) {
                    map.get(entity.getId()).getGenres().addAll(entity.getGenres());
                } else {
                    map.put(entity.getId(), entity);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return map.values().stream().toList();
    }

    @Override
    public void deleteById(Integer id) {
        try (
                PreparedStatement deleteBookSt = connection.prepareStatement(DELETE_BY_ID.query);
                PreparedStatement deleteAuthorBookSt = connection.prepareStatement(DELETE_AUTHOR_BOOK.query);
                PreparedStatement deleteBookGenresSt = connection.prepareStatement(DELETE_BOOK_GENRES.query);
        ) {
            connection.setAutoCommit(false);
            // Удаление записей из таблицы author_books
            deleteAuthorBookSt.setInt(1, id);
            deleteAuthorBookSt.executeUpdate();

            // Удаление записей из таблицы book_genres
            deleteBookGenresSt.setInt(1, id);
            deleteBookGenresSt.executeUpdate();

            // Удаление книги из books
            deleteBookSt.setInt(1, id);
            deleteBookSt.executeUpdate();

            connection.commit();
        }
        catch (SQLException e) {
            throw  new RuntimeException();
        }
    }


    private BookEntity getBookFromResultSet(ResultSet rs) throws SQLException {
        int bookId = rs.getInt("book_id");
        String title = rs.getString("book_title");
        String description = rs.getString("book_description");
        String publishedDate = rs.getString("book_published_date");
        String isbn = rs.getString("book_isbn");

        int authorId = rs.getInt("author_id");
        String authorName = rs.getString("author_name");
        String authorBio = rs.getString("author_bio");

        int genreId = rs.getInt("genre_id");
        String genreName = rs.getString("genre_name");
        String genreDescription = rs.getString("genre_description");


        GenreEntity genre = GenreEntity.builder()
                .id(genreId)
                .name(genreName)
                .description(genreDescription)
                .build();

        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(authorId)
                .name(authorName)
                .bio(authorBio)
                .build();

        return BookEntity.builder()
                .id(bookId)
                .title(title)
                .description(description)
                .isbn(isbn)
                .publishedDate(publishedDate)
                .author(authorEntity)
                .genres(new ArrayList<>(List.of(genre)))
                .build();
    }

    private void fillStatementWithBookFields(PreparedStatement st, BookEntity entity) throws SQLException {
        if (entity.getTitle() != null) {
            st.setString(1, entity.getTitle());
        }
        if (entity.getDescription() != null) {
            st.setString(2, entity.getDescription());
        }
        if (entity.getPublishedDate() != null) {
            st.setString(3, entity.getPublishedDate());
        }
        if (entity.getIsbn() != null) {
            st.setString(4, entity.getIsbn());
        }
        if (entity.getAuthor() != null) {
            st.setInt(5, entity.getAuthor().getId());
        }
        if (entity.getId() != null) {
            st.setInt(6, entity.getId());
        }
    }
}
