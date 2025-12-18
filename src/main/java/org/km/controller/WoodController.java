package org.km.controller;

import org.km.db.entity.Drink;
import org.km.db.entity.Wood;
import org.km.db.view.DrinkView;
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

import static org.km.controller.ControllerConstants.BARREL_URL;
import static org.km.controller.ControllerConstants.WOOD_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class WoodController extends AbstractCrudController<WoodView, Wood> {

    @Autowired
    private WoodService service;

    @Override
    protected CrudService<WoodView, Wood> getService() {
        return service;
    }

    @Override
    protected String getEntityName() {
        return "Древесина";
    }

    @Operation(summary = "Получение списка древесины", description="Получение списка материалов для бочек(они могут быть и смешанными)")
    @RequestMapping(method = RequestMethod.GET,
            value = WOOD_URL,
            produces = "application/json")
    public ResponseEntity<List<WoodView>> getWoods() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Получение древесины", description="Получение материала для бочек(он может быть и смешанными)")
    @RequestMapping(method = RequestMethod.GET,
            value = WOOD_URL + "/{id}",
            produces = "application/json")
    public ResponseEntity<WoodView> getWood(@PathVariable Integer id) {
        Optional<WoodView> optionalWood = service.getById(id);
        return optionalWood.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создание древесины", description="Добавление новой древесины")
    @PostMapping(value = WOOD_URL, produces = "application/json")
    public ResponseEntity<Wood> addWood(@RequestBody Wood wood) {
        return ResponseEntity.ok(service.add(wood));
    }

    @Operation(summary = "Обновление древесины", description="Обновление существующей древесины")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<Wood> update(@PathVariable Integer id, @RequestBody Wood wood) {
        return super.update(id, wood);
    }

    @Operation(summary = "Удаление древесины", description="Удаление существующей древесины")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return super.delete(id);
    }
}
