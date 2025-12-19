package org.km.service;

import java.util.List;
import java.util.Optional;

public interface CrudService <T, U>{
    List<T> getAll();
    Optional<T> getById(Integer id);
    U add(U entity);
    U update(Integer id, U entity);
    void delete(Integer id);
    boolean exists(Integer id);
}
