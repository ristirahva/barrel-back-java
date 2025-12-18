package org.km.service;

import java.util.List;
import java.util.Optional;

public interface CrudService <T, U>{
    List<T> getAll();
    Optional<T> getById(int id);
    U add(U entity);
    U update(int id, U entity);
    void deleteById(int id);
    boolean existsById(int id);
}
