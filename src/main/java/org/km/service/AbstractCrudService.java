package org.km.service;

import org.km.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<T, R extends JpaRepository<T, Integer>> implements CrudService<T>{

    protected final R repository;

    protected AbstractCrudService(R repository) {
        this.repository = repository;
    }
    @Override
    public List findAll() {
        return repository.findAll();
    }

    @Override
    public Optional findById(int id) {
        return repository.findById(id);
    }

    @Override
    public T add(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(int id, T entity) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("%s с id=%s не найден", getEntityName(), id)
            );
        }

        return repository.save(entity);
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public boolean existsById(int id) {
        return false;
    }

    /**
     * Получить имя сущности для сообщений об ошибках
     */
    protected abstract String getEntityName();
}
