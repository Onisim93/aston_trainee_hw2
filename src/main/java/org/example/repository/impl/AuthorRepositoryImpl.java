package org.example.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.DataProcessingException;
import org.example.exception.EntityNotFoundException;
import org.example.exception.ExceptionMessageHelper;
import org.example.model.AuthorEntity;
import org.example.repository.AuthorRepository;
import org.example.repository.queries.SqlAuthorQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link AuthorRepository} interface.
 * This class handles CRUD operations for {@link AuthorEntity} entities in the database.
 */
@Slf4j
public class AuthorRepositoryImpl implements AuthorRepository {

    /**
     * The JDBC connection to be used by the repository for database operations.
     */
    private final Connection connection;

    /**
     * Constructs an instance of {@code AuthorRepositoryImpl} with the specified database connection.
     * @param connection The JDBC connection to be used by the repository for database operations.
     */
    public AuthorRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AuthorEntity create(AuthorEntity entity) {
        ResultSet resultSet = null;
        try (PreparedStatement createSt = connection.prepareStatement(SqlAuthorQueries.CREATE.query)) {
            connection.setAutoCommit(false);

            fillStatementWithAuthorFields(createSt, entity);
            resultSet = createSt.executeQuery();
            int authorId = - 1;
            if (resultSet.next()) {
                authorId = resultSet.getInt(1);
            }

            connection.commit();

            return findById(authorId);
        }
        catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.warn(ExceptionMessageHelper.ERROR_WHILE_ROLLBACK_TRANSACTION_MSG, ex);
            }
            throw new DataProcessingException(e.getMessage());
        }
        finally {
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
    public AuthorEntity update(AuthorEntity entity) {
        findById(entity.getId());

        try (PreparedStatement updateSt = connection.prepareStatement(SqlAuthorQueries.UPDATE.query)) {
            connection.setAutoCommit(false);
            fillStatementWithAuthorFields(updateSt, entity);
            updateSt.executeUpdate();
            connection.commit();

            return findById(entity.getId());
        }
        catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.warn(ExceptionMessageHelper.ERROR_WHILE_ROLLBACK_TRANSACTION_MSG, ex);
            }
            throw new DataProcessingException(e.getMessage());
        }
    }

    @Override
    public AuthorEntity findById(Integer id) {
        ResultSet resultSet = null;
        try (PreparedStatement findSt = connection.prepareStatement(SqlAuthorQueries.FIND_BY_ID.query)) {
            findSt.setInt(1, id);
            resultSet = findSt.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                throw new EntityNotFoundException(ExceptionMessageHelper.entityNotFoundMessage(id));
            }
            else {
                resultSet.next();
                return buildAuthorEntity(resultSet);
            }
        }
        catch (SQLException e) {
            throw new DataProcessingException(e.getMessage());
        }
        finally {
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
    public List<AuthorEntity> findAll() {
        List<AuthorEntity> authorEntities = new ArrayList<>();
        ResultSet resultSet = null;
        try (PreparedStatement findAllSt = connection.prepareStatement(SqlAuthorQueries.FIND_ALL.query)) {
            resultSet = findAllSt.executeQuery();
            while (resultSet.next()) {
                authorEntities.add(buildAuthorEntity(resultSet));
            }
        }
        catch (SQLException e) {
            throw new DataProcessingException(e.getMessage());
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    log.warn(ExceptionMessageHelper.ERROR_WHILE_CLOSING_RESULT_SET_MSG, e);
                }
            }
        }
        return authorEntities;
    }

    @Override
    public void deleteById(Integer id) {
        try (PreparedStatement deleteByIdSt = connection.prepareStatement(SqlAuthorQueries.DELETE_BY_ID.query)) {
            connection.setAutoCommit(false);
            deleteByIdSt.setInt(1, id);
            deleteByIdSt.executeUpdate();
            connection.commit();
        }
        catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.warn(ExceptionMessageHelper.ERROR_WHILE_ROLLBACK_TRANSACTION_MSG, ex);
            }
            throw new DataProcessingException(e.getMessage());
        }
    }

    private AuthorEntity buildAuthorEntity(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String name = resultSet.getString(2);
        String bio = resultSet.getString(3);

        return AuthorEntity.builder()
                .id(id)
                .name(name)
                .bio(bio)
                .build();
    }

    private void fillStatementWithAuthorFields(PreparedStatement st, AuthorEntity entity) throws SQLException {
        if (entity.getName() != null) {
            st.setString(1, entity.getName());
        }
        if (entity.getBio() != null) {
            st.setString(2, entity.getBio());
        }
        if (entity.getId() != null) {
            st.setInt(3, entity.getId());
        }
    }
}
