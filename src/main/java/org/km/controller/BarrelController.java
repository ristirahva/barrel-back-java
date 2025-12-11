package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.db.entity.Barrel;
import org.km.db.entity.Wood;
import org.km.exception.ResourceNotFoundException;
import org.km.service.BarrelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BarrelController {

    private static final String GET_BARRELS = "/api/v1/barrels";

    @Autowired
    private BarrelService service;

    @Operation(summary = "Получение списка бочек", description="Получение списка бочек")
    @RequestMapping(method = RequestMethod.GET, value = GET_BARRELS, produces = "application/json")
    public ResponseEntity<List<Barrel>> getBarrels() {
        return new ResponseEntity<>(service.getBarrels(), HttpStatus.OK);
    }

    @Operation(summary = "Получение бочки", description="Получение бочки")
    @RequestMapping(method = RequestMethod.GET,
            value = GET_BARRELS + "/{id}",
            produces = "application/json")
    public ResponseEntity<Barrel> getBarrel(@PathVariable Integer id) {
        Optional<Barrel> optionalBarrel = service.getBarrel(id);
        if (optionalBarrel.isPresent()) {
            return new ResponseEntity<>(optionalBarrel.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException(String.format("Бочка с id=%d не найдена" , id));
        }
    }

    @Operation(summary = "Изменение бочки", description="Изменение данных бочки")
    @PutMapping(value = GET_BARRELS + "/{id}", produces = "application/json")
    public ResponseEntity<Barrel> updateBarrel(@PathVariable Integer id, @RequestBody Barrel barrel) {
        return ResponseEntity.ok(service.updateBarrel(id, barrel));
    }

    @Operation(summary = "Создание бочки", description="Добавление новой бочки")
    @PostMapping(produces = "application/json")
    public ResponseEntity<Barrel> addBarrel(@RequestBody Barrel barrel) {
        return ResponseEntity.ok(service.addBarrel(barrel));
    }

    @Operation(summary = "Удаление бочки", description="Удаление бочки")
    @DeleteMapping(value = GET_BARRELS + "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteBarrel(@PathVariable Integer id) {
        service.deleteBarrel(id);
        return ResponseEntity.noContent().build();
    }
}
