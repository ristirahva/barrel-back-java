package org.km.service;

import java.util.List;
import java.util.Optional;

public interface CrudService <T>{
    List<T> getAll();
    Optional<T> getById(Integer id);
    T add(T entity);
    T update(Integer id, T entity);
    void delete(Integer id);
    boolean exists(Integer id);
}
