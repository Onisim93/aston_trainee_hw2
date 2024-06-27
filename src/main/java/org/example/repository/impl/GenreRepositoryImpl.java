package org.example.repository.impl;

import org.example.model.GenreEntity;
import org.example.repository.GenreRepository;

import java.sql.Connection;
import java.util.List;

public class GenreRepositoryImpl implements GenreRepository {

    private final Connection connection;

    public GenreRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public GenreEntity create(GenreEntity entity) {
        return null;
    }

    @Override
    public GenreEntity update(GenreEntity entity) {
        return null;
    }

    @Override
    public GenreEntity findById(Integer id) {
        return null;
    }

    @Override
    public List<GenreEntity> findAll() {
        return List.of();
    }

    @Override
    public void deleteById(Integer id) {

    }
}
