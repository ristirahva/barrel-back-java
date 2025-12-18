package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.exception.ResourceNotFoundException;
import org.km.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractCrudController<T, U> {
    /**
     * Получение сервиса для работы с сущностью
     */
    protected abstract CrudService<T, U> getService();

    protected abstract String getEntityName();

    @Operation(summary = "Получение списка всех записей")
    @GetMapping("")
    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    @Operation(summary = "Получение записи по ID")
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable int id) {
        return getService().getById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("%s с id=%s не найден", getEntityName(), id)
                ));
    }

    @Operation(summary = "Создание новой записи")
    @PostMapping
    public ResponseEntity<U> add(@RequestBody U entity) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(getService().add(entity));
    }

    @Operation(summary = "Обновление записи")
    @PutMapping("/{id}")
    public ResponseEntity<U> update(@PathVariable int id, @RequestBody U entity) {
        if (!getService().existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("%s с id=%s не найден", getEntityName(), id)
            );
        }
        return ResponseEntity.ok(getService().update(id, entity));
    }

    @Operation(summary = "Удаление записи")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!getService().existsById(id)) {
            throw new ResourceNotFoundException(
                    String.format("%s с id=%s не найден", getEntityName(), id)
            );
        }
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
