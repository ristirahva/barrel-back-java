package org.km.service;

import org.km.db.repository.ReadRepository;
import org.km.db.repository.WriteRepository;
import org.km.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractCrudService<T, R extends ReadRepository<T, Integer>, U, W extends WriteRepository<U, Integer>> implements CrudService<T, U>{

    protected final R readRepository;
    protected final W writeRepository;

    protected AbstractCrudService(R readRepository, W writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }
    @Override
    public List<T> getAll() {
        return  StreamSupport.stream(readRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<T> getById(int id) {
        return readRepository.findById(id);
    }

    @Override
    public U add(U entity) {
        return writeRepository.save(entity);
    }

    @Override
    public U update(int id, U entity) {
        if (!readRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("%s с id=%s не найден", getEntityName(), id)
            );
        }

        return writeRepository.save(entity);
    }

    @Override
    public void deleteById(int id) {
        writeRepository.deleteById(id);
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
