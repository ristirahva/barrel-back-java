package org.km.service;

import org.km.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractCrudService<T, R extends JpaRepository<T, Integer>> implements CrudService<T>{

    protected final R repository;

    protected AbstractCrudService(R repository) {
        this.repository = repository;
    }
    @Override
    public List<T> getAll() {
        return  StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<T> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public T add(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(Integer id, T entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("%s с id=%s не найден", getEntityName(), id)
            );
        }

        return repository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(Integer id) {
        return repository.existsById(id);
    }

    /**
     * Получить имя сущности для сообщений об ошибках
     */
    protected abstract String getEntityName();
}
