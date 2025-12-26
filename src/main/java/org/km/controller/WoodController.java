package org.km.controller;

import org.km.db.view.WoodView;
import org.km.service.CrudService;
import org.km.service.WoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;

import static org.km.controller.ControllerConstants.WOOD_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(WOOD_URL)
public class WoodController extends AbstractCrudController<org.km.db.view.WoodView> {

    @Autowired
    private WoodService service;

    @Override
    protected CrudService<org.km.db.view.WoodView> getService() {
        return service;
    }

    @Override
    protected String getEntityName() {
        return "Древесина";
    }

    @Operation(summary = "Получение списка древесины", description="Получение списка материалов для бочек(они могут быть и смешанными)")
    @RequestMapping(method = RequestMethod.GET,
            value = "",
            produces = "application/json")
    public ResponseEntity<List<org.km.db.view.WoodView>> getWoods() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Получение древесины", description="Получение материала для бочек(он может быть и смешанными)")
    @RequestMapping(method = RequestMethod.GET,
            value = "/{id}",
            produces = "application/json")
    public ResponseEntity<org.km.db.view.WoodView> getWood(@PathVariable Integer id) {
        Optional<org.km.db.view.WoodView> optionalWood = service.getById(id);
        return optionalWood.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создание древесины", description="Добавление новой древесины")
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<org.km.db.view.WoodView> addWood(@RequestBody org.km.db.view.WoodView woodView) {
        return super.add(woodView);
    }

    @Operation(summary = "Обновление древесины", description="Обновление существующей древесины")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<WoodView> update(@PathVariable Integer id, @RequestBody WoodView woodView) {
        return super.update(id, woodView);
    }

    @Operation(summary = "Удаление древесины", description="Удаление существующей древесины")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return super.delete(id);
    }
}
