package org.km.service;

import java.util.List;
import java.util.Optional;

public interface CrudService <T>{
    List<T> getAll();
    Optional<T> getById(int id);
    T add(T entity);
    T update(int id, T entity);
    void deleteById(int id);
    boolean existsById(int id);
}
