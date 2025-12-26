package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.km.db.entity.Drink;
import org.km.db.view.DrinkView;
import org.km.service.CrudService;
import org.km.service.DrinkService;

import static org.km.controller.ControllerConstants.DRINK_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(DRINK_URL)
public class DrinkController extends AbstractCrudController<DrinkView> {
    @Autowired
    private DrinkService service;

    @Override
    protected CrudService<DrinkView> getService() {
        return service;
    }

    @Override
    protected String getEntityName() {
        return "Дистиллят";
    }

    @Operation(summary = "Получение списка напитков", description="Получение списка напитков")
    @RequestMapping(method = RequestMethod.GET,
            path = "",
            produces = "application/json")
    public ResponseEntity<List<DrinkView>> getDrinks() {
        return super.getAll();
    }

    @Operation(summary = "Получение напитка", description="Получение напитка по id")
    @RequestMapping(method = RequestMethod.GET,
            path = "/{id}",
            produces = "application/json")
    public ResponseEntity<DrinkView> getDrink(@PathVariable Integer id) {
        return super.getById(id);
    }

    @Operation(summary = "Создание напитка", description="Добавление нового напитка")
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<DrinkView> add(@RequestBody DrinkView drinkView) {
        return super.add(drinkView);
    }

    @Operation(summary = "Обновление напитка", description="Обновление дистиллята")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<DrinkView> update(@PathVariable Integer id, @RequestBody DrinkView drinkView) {
        return super.update(id, drinkView);
    }

    @Operation(summary = "Удаление дистиллята", description="Удаление существующего дистиллята")
    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return super.delete(id);
    }
}
