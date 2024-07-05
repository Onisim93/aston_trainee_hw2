package org.example.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.DataProcessingException;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ExceptionMessageHelper;
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

/**
 * Implementation of the {@link BookRepository} interface.
 * This class handles CRUD operations for {@link BookEntity} entities in the database.
 */
@Slf4j
public class BookRepositoryImpl implements BookRepository {

    /**
     * The JDBC connection to be used by the repository for database operations.
     */
    private final Connection connection;

    /**
     * Constructs an instance of {@code BookRepositoryImpl} with the specified database connection.
     * @param connection The JDBC connection to be used by the repository for database operations.
     */
    public BookRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public BookEntity create(BookEntity entity) {
        ResultSet generatedId = null;
        try (PreparedStatement insertBookSt = connection.prepareStatement(CREATE.query); PreparedStatement insertBookGenresSt = connection.prepareStatement(INSERT_GENRE_TO_BOOK_GENRES.query)) {
            connection.setAutoCommit(false);

            fillStatementWithBookFields(insertBookSt, entity);
            generatedId = insertBookSt.executeQuery();
            int bookId = -1;

            if (generatedId.next()) {
                bookId = generatedId.getInt(1);

                insertBookGenresSt.setInt(1, bookId);
                for (GenreEntity g : entity.getGenres()) {
                    insertBookGenresSt.setInt(2, g.getId());
                    insertBookGenresSt.addBatch();
                }
                insertBookGenresSt.executeBatch();
            }
            connection.commit();

            return findById(bookId);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.warn(ExceptionMessageHelper.ERROR_WHILE_ROLLBACK_TRANSACTION_MSG, ex);
            }
            throw new DataProcessingException(e.getMessage());
        }
    }

    @Override
    public BookEntity update(BookEntity entity) {

        findById(entity.getId());

        try (PreparedStatement updateBookSt = connection.prepareStatement(UPDATE.query); PreparedStatement deleteOldBookGenresSt = connection.prepareStatement(DELETE_BOOK_GENRES.query); PreparedStatement insertBookGenresSt = connection.prepareStatement(INSERT_GENRE_TO_BOOK_GENRES.query)) {

            connection.setAutoCommit(false);

            // Обновление основных данных сущности
            fillStatementWithBookFields(updateBookSt, entity);
            updateBookSt.executeUpdate();

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

            return findById(entity.getId());

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.warn(ExceptionMessageHelper.ERROR_WHILE_ROLLBACK_TRANSACTION_MSG, ex);
            }
            throw new DataProcessingException(e.getMessage());
        }

    }

    @Override
    public BookEntity findById(Integer id) {
        Map<Integer, BookEntity> map = new HashMap<>();
        ResultSet resultSet = null;

        try (PreparedStatement st = connection.prepareStatement(FIND_BY_ID.query)) {

            st.setInt(1, id);

            resultSet = st.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new EntityNotFoundException("id");
            }


            while (resultSet.next()) {
                BookEntity entity = getBookFromResultSet(resultSet);

                if (map.containsKey(id)) {
                    map.get(id).getGenres().addAll(entity.getGenres());
                } else {
                    map.put(id, entity);
                }
            }

            return map.get(id);
        } catch (SQLException e) {
            throw new DataProcessingException(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn(ExceptionMessageHelper.ERROR_WHILE_CLOSING_RESULT_SET_MSG);
                }
            }
        }
    }

    @Override
    public List<BookEntity> findAll() {
        Map<Integer, BookEntity> map = new HashMap<>();
        ResultSet resultSet = null;
        try (PreparedStatement st = connection.prepareStatement(FIND_ALL.query)) {
            resultSet = st.executeQuery();
            fillBooksMapFromResultSet(resultSet, map);

            return map.values().stream().toList();

        } catch (SQLException e) {
            throw new DataProcessingException(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn(ExceptionMessageHelper.ERROR_WHILE_CLOSING_RESULT_SET_MSG, e);
                }
            }
        }
    }

    @Override
    public List<BookEntity> findByCriteria(Integer authorId, Integer genreId) {
        Map<Integer, BookEntity> map = new HashMap<>();
        ResultSet resultSet = null;
        try (PreparedStatement st = connection.prepareStatement(FIND_BY_CRITERIA.query)) {
            st.setInt(1, authorId);
            st.setInt(2, genreId);

            resultSet = st.executeQuery();

            fillBooksMapFromResultSet(resultSet, map);

            return map.values().stream().toList();
        } catch (SQLException e) {
            throw new DataProcessingException(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn(ExceptionMessageHelper.ERROR_WHILE_CLOSING_RESULT_SET_MSG, e);
                }
            }
        }
    }

    @Override
    public List<BookEntity> findByAuthorId(Integer authorId) {
        Map<Integer, BookEntity> map = new HashMap<>();
        ResultSet resultSet = null;
        try (PreparedStatement st = connection.prepareStatement(FIND_BY_AUTHOR_ID.query)) {
            st.setInt(1, authorId);
            resultSet = st.executeQuery();

            fillBooksMapFromResultSet(resultSet, map);

            return map.values().stream().toList();


        } catch (SQLException e) {
            throw new DataProcessingException(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn(ExceptionMessageHelper.ERROR_WHILE_CLOSING_RESULT_SET_MSG, e);
                }
            }
        }
    }

    @Override
    public List<BookEntity> findByGenreId(Integer genreId) {
        Map<Integer, BookEntity> map = new HashMap<>();
        ResultSet resultSet = null;
        try (PreparedStatement st = connection.prepareStatement(FIND_BY_GENRE_ID.query)) {
            st.setInt(1, genreId);
            resultSet = st.executeQuery();
            fillBooksMapFromResultSet(resultSet, map);
            return map.values().stream().toList();


        } catch (SQLException e) {
            throw new DataProcessingException(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn(ExceptionMessageHelper.ERROR_WHILE_CLOSING_RESULT_SET_MSG, e);
                }
            }
        }
    }


    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement deleteBookSt = connection.prepareStatement(DELETE_BY_ID.query); PreparedStatement deleteBookGenresSt = connection.prepareStatement(DELETE_BOOK_GENRES.query)) {
            connection.setAutoCommit(false);
            // Удаление записей из таблицы book_genres
            deleteBookGenresSt.setInt(1, id);
            deleteBookGenresSt.executeUpdate();

            // Удаление книги из books
            deleteBookSt.setInt(1, id);
            deleteBookSt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.warn(ExceptionMessageHelper.ERROR_WHILE_ROLLBACK_TRANSACTION_MSG, ex);
            }
            throw new DataProcessingException(e.getMessage());
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
        AuthorEntity author = AuthorEntity.builder().id(authorId).name(authorName).build();

        int genreId = rs.getInt("genre_id");
        String genreName = rs.getString("genre_name");
        GenreEntity genre = GenreEntity.builder().id(genreId).name(genreName).build();

        return BookEntity.builder().id(bookId).title(title).description(description).isbn(isbn).publishedDate(publishedDate).author(author).genres(new ArrayList<>(List.of(genre))).build();
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

    private void fillBooksMapFromResultSet(ResultSet rs, Map<Integer, BookEntity> map) throws SQLException {
        while (rs.next()) {
            BookEntity entity = getBookFromResultSet(rs);
            if (map.containsKey(entity.getId())) {
                map.get(entity.getId()).getGenres().addAll(entity.getGenres());
            } else {
                map.put(entity.getId(), entity);
            }
        }
    }


}
