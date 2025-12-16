package org.km.controller;

import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Optional;

public interface CrudController<T> {
    List<T> findAll();
    Optional<T> findById(int id);
    T save(T entity);
    void deleteById(int id);
    boolean existsById(int id);
}
