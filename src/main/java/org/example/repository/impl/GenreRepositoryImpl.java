package org.example.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.DataProcessingException;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ExceptionMessageHelper;
import org.example.model.GenreEntity;
import org.example.repository.GenreRepository;
import org.example.repository.queries.SqlGenreQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link GenreRepository} interface.
 * This class handles CRUD operations for {@link GenreEntity} entities in the database.
 */
@Slf4j
public class GenreRepositoryImpl implements GenreRepository {

    /**
     * The JDBC connection to be used by the repository for database operations.
     */
    private final Connection connection;

    /**
     * Constructs an instance of {@code BookRepositoryImpl} with the specified database connection.
     *
     * @param connection The JDBC connection to be used by the repository for database operations.
     */
    public GenreRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public GenreEntity create(GenreEntity entity) {
        ResultSet resultSet = null;
        try (PreparedStatement createSt = connection.prepareStatement(SqlGenreQueries.CREATE.query)) {
            connection.setAutoCommit(false);
            fillStatementWithGenreFields(createSt, entity);

            resultSet = createSt.executeQuery();
            int genreId = -1;
            if (resultSet.next()) {
                genreId = resultSet.getInt(1);
            }


            connection.commit();

            return findById(genreId);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.warn(ExceptionMessageHelper.ERROR_WHILE_ROLLBACK_TRANSACTION_MSG, ex);
            }
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
    public GenreEntity update(GenreEntity entity) {
        // Вызываем findById чтобы убедиться, что мы пытаемся обновить существующий объект в БД.
        findById(entity.getId());

        try (PreparedStatement updateSt = connection.prepareStatement(SqlGenreQueries.UPDATE.query)) {
            connection.setAutoCommit(false);
            fillStatementWithGenreFields(updateSt, entity);
            updateSt.executeUpdate();
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
    public GenreEntity findById(Integer id) {
        ResultSet resultSet = null;
        try (PreparedStatement findSt = connection.prepareStatement(SqlGenreQueries.FIND_BY_ID.query)) {
            findSt.setInt(1, id);
            resultSet = findSt.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new EntityNotFoundException("id");
            } else {
                resultSet.next();
                return buildGenreEntity(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    log.warn(ExceptionMessageHelper.ERROR_WHILE_CLOSING_RESULT_SET_MSG, ex);
                }
            }
        }
    }

    @Override
    public List<GenreEntity> findAll() {
        List<GenreEntity> genreEntities = new ArrayList<>();
        ResultSet resultSet = null;

        try (PreparedStatement findAllSt = connection.prepareStatement(SqlGenreQueries.FIND_ALL.query)) {
            resultSet = findAllSt.executeQuery();
            while (resultSet.next()) {
                genreEntities.add(buildGenreEntity(resultSet));
            }

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


        return genreEntities;
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement deleteSt = connection.prepareStatement(SqlGenreQueries.DELETE_BY_ID.query)) {
            connection.setAutoCommit(false);
            deleteSt.setInt(1, id);
            deleteSt.executeUpdate();
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


    private GenreEntity buildGenreEntity(ResultSet resultSet) throws SQLException {
        return GenreEntity.builder().id(resultSet.getInt("id")).name(resultSet.getString("name")).description(resultSet.getString("description")).build();
    }


    private void fillStatementWithGenreFields(PreparedStatement statement, GenreEntity entity) throws SQLException {
        if (entity.getName() != null) {
            statement.setString(1, entity.getName());
        }
        if (entity.getDescription() != null) {
            statement.setString(2, entity.getDescription());
        }
        if (entity.getId() != null) {
            statement.setInt(3, entity.getId());
        }
    }
}
