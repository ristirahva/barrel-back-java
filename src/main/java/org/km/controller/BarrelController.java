package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import org.km.db.entity.Barrel;
import org.km.db.view.BarrelView;
import org.km.exception.EntityNotFoundException;
import org.km.service.BarrelService;
import org.km.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.km.controller.ControllerConstants.BARREL_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(BARREL_URL)
public class BarrelController extends AbstractCrudController <BarrelView, Barrel> {

    @Autowired
    private BarrelService service;

    @Override
    protected CrudService<BarrelView, Barrel> getService() {
        return service;
    }

    @Override
    protected String getEntityName() {
        return "Бочка";
    }

    @Operation(summary = "Получение списка бочек", description="Получение списка бочек")
    @RequestMapping(method = RequestMethod.GET, value = "", produces = "application/json")
    public ResponseEntity<List<BarrelView>> getBarrels() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка бочек из определённой древесины", description="Получение списка бочек из определённой древесины")
    @RequestMapping(method = RequestMethod.GET, value = "/by-wood/{woodId}", produces = "application/json")
    public ResponseEntity<List<BarrelView>> getBarrelsByWoodId( @Parameter(description = "ID древесины") @PathVariable int woodId) {
        return new ResponseEntity<>(service.getBarrelsByWoodId(woodId), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка бочек определённого производителя", description="Получение списка бочек определённого производителя")
    @RequestMapping(method = RequestMethod.GET, value = "/by-cooper/{cooperId}", produces = "application/json")
    public ResponseEntity<List<BarrelView>> getBarrelsByCooperId( @Parameter(description = "ID производителя") @PathVariable int cooperId) {
        return new ResponseEntity<>(service.getBarrelsByCooperId(cooperId), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка активных бочек", description="Получение списка активных бочек")
    @RequestMapping(method = RequestMethod.GET, value = "/active", produces = "application/json")
    public ResponseEntity<List<BarrelView>> getBarrelsActive() {
        return new ResponseEntity<>(service.getActive(), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка архивных бочек", description="Получение списка архивных бочек")
    @RequestMapping(method = RequestMethod.GET, value = "/archived", produces = "application/json")
    public ResponseEntity<List<BarrelView>> getBarrelsArchive() {
        return new ResponseEntity<>(service.getInactive(), HttpStatus.OK);
    }

    @Operation(summary = "Получение бочки", description="Получение бочки по id")
    @RequestMapping(method = RequestMethod.GET,
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<BarrelView> getBarrel(@PathVariable Integer id) {
        Optional<BarrelView> optionalBarrel = service.getById(id);
        if (optionalBarrel.isPresent()) {
            return new ResponseEntity<>(optionalBarrel.get(), HttpStatus.OK);
        }
        else {
            throw new EntityNotFoundException(String.format("Бочка с id=%d не найдена" , id));
        }
    }

    @Operation(summary = "Создание бочки", description="Добавление новой бочки")
    @PostMapping(value = "", produces = "application/json")
    @Override
    public ResponseEntity<Barrel> add(@RequestBody Barrel barrel) {
        return ResponseEntity.ok(service.add(barrel));
    }

    @Operation(summary = "Изменение бочки", description="Изменение данных бочки")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<Barrel> update(@PathVariable Integer id, @RequestBody Barrel barrel) {
        return super.update(id, barrel);
    }

    @Operation(summary = "Удаление бочки", description="Удаление бочки")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return super.delete(id);
    }
}
