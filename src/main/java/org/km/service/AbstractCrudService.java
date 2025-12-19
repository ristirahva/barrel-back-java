package org.km.service;

import org.km.db.repository.ReadRepository;
import org.km.db.repository.WriteRepository;
import org.km.exception.EntityNotFoundException;

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
    public Optional<T> getById(Integer id) {
        return readRepository.findById(id);
    }

    @Override
    public U add(U entity) {
        return writeRepository.save(entity);
    }

    @Override
    public U update(Integer id, U entity) {
        if (!readRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    String.format("%s с id=%s не найден", getEntityName(), id)
            );
        }

        return writeRepository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        writeRepository.deleteById(id);
    }

    @Override
    public boolean exists(Integer id) {
        return readRepository.existsById(id);
    }

    /**
     * Получить имя сущности для сообщений об ошибках
     */
    protected abstract String getEntityName();
}
