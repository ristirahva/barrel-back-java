package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import org.km.db.entity.Barrel;
import org.km.db.view.BarrelView;
import org.km.exception.ResourceNotFoundException;
import org.km.service.BarrelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.km.controller.ControllerConstants.BARREL_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BarrelController {

    @Autowired
    private BarrelService service;

    @Operation(summary = "Получение списка бочек", description="Получение списка бочек")
    @RequestMapping(method = RequestMethod.GET, value = BARREL_URL, produces = "application/json")
    public ResponseEntity<List<BarrelView>> getBarrels() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка бочек из определённой древесины", description="Получение списка бочек из определённой древесины")
    @RequestMapping(method = RequestMethod.GET, value = BARREL_URL + "/by-wood/{woodId}", produces = "application/json")
    public ResponseEntity<List<BarrelView>> getBarrelsByWoodId( @Parameter(description = "ID категории") @PathVariable int woodId) {
        return new ResponseEntity<>(service.getBarrelsByWoodId(woodId), HttpStatus.OK);
    }

    @Operation(summary = "Получение бочки", description="Получение бочки по id")
    @RequestMapping(method = RequestMethod.GET,
            value = BARREL_URL + "/{id}",
            produces = "application/json")
    public ResponseEntity<BarrelView> getBarrel(@PathVariable Integer id) {
        Optional<BarrelView> optionalBarrel = service.getById(id);
        if (optionalBarrel.isPresent()) {
            return new ResponseEntity<>(optionalBarrel.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException(String.format("Бочка с id=%d не найдена" , id));
        }
    }

    @Operation(summary = "Изменение бочки", description="Изменение данных бочки")
    @PutMapping(value = BARREL_URL + "/{id}", produces = "application/json")
    public ResponseEntity<Barrel> updateBarrel(@PathVariable Integer id, @RequestBody Barrel barrel) {
        return ResponseEntity.ok(service.update(id, barrel));
    }

    @Operation(summary = "Создание бочки", description="Добавление новой бочки")
    @PostMapping(value = BARREL_URL, produces = "application/json")
    public ResponseEntity<Barrel> addBarrel(@RequestBody Barrel barrel) {
        return ResponseEntity.ok(service.add(barrel));
    }

    @Operation(summary = "Удаление бочки", description="Удаление бочки")
    @DeleteMapping(value = BARREL_URL + "/{id}", produces = "application/json")
    public ResponseEntity<Void> deleteBarrel(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
