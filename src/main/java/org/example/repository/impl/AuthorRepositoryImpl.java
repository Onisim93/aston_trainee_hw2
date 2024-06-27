package org.example.repository.impl;

import org.example.model.AuthorEntity;
import org.example.repository.AuthorRepository;

import java.sql.Connection;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final Connection connection;

    public AuthorRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AuthorEntity create(AuthorEntity entity) {
        return null;
    }

    @Override
    public AuthorEntity update(AuthorEntity entity) {
        return null;
    }

    @Override
    public AuthorEntity findById(Integer id) {
        return null;
    }

    @Override
    public List<AuthorEntity> findAll() {
        return List.of(new AuthorEntity());
    }

    @Override
    public void deleteById(Integer id) {

    }
}
