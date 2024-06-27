package org.example.repository;

import org.example.model.BaseEntity;

import java.util.List;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    T create(T entity);
    T update(T entity);
    T findById(K id);
    List<T> findAll();
    void deleteById(K id);
}
