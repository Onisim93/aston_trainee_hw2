package org.example.service;

import org.example.model.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity<K>, K> {
    List<T> findAll();
    T findById(K id);
    T save(T entity);
    void delete(T entity);
    void deleteById(K id);
    T update(T entity);
}
