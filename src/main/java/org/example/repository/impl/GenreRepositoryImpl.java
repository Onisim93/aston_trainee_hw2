package org.example.repository.impl;

import org.example.model.GenreEntity;
import org.example.repository.GenreRepository;
import org.example.repository.queries.SqlGenreQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepository {

    private final Connection connection;

    public GenreRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public GenreEntity create(GenreEntity entity) {
        try(PreparedStatement createSt = connection.prepareStatement(SqlGenreQueries.CREATE.query)) {
            connection.setAutoCommit(false);
            fillStatementWithGenreFields(createSt, entity);
            ResultSet resultSet = createSt.executeQuery();

            if (resultSet.next()) {
                entity.setId(resultSet.getInt("id"));
            }


            connection.commit();
        }
        catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public GenreEntity update(GenreEntity entity) {
        try (PreparedStatement updateSt = connection.prepareStatement(SqlGenreQueries.UPDATE.query)) {
            connection.setAutoCommit(false);
            fillStatementWithGenreFields(updateSt, entity);
            updateSt.executeUpdate();
            connection.commit();
        }
        catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public GenreEntity findById(Integer id) {
        GenreEntity genreEntity = null;

        try (PreparedStatement findSt = connection.prepareStatement(SqlGenreQueries.FIND_BY_ID.query)) {
            findSt.setInt(1, id);
            ResultSet resultSet = findSt.executeQuery();
            if (resultSet.next()) {
                genreEntity = buildGenreEntity(resultSet);
            }
            return genreEntity;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GenreEntity> findAll() {
        List<GenreEntity> genreEntities = new ArrayList<>();

        try (PreparedStatement findAllSt = connection.prepareStatement(SqlGenreQueries.FIND_ALL.query)) {
            ResultSet resultSet = findAllSt.executeQuery();
            while (resultSet.next()) {
                genreEntities.add(buildGenreEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }


    private GenreEntity buildGenreEntity(ResultSet resultSet) throws SQLException {
        return GenreEntity.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .build();
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
