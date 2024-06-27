package org.example.model;

import lombok.ToString;

public interface BaseEntity<T> {
    public T getId();
    public void setId(T id);
}
