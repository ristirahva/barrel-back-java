package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.db.entity.Drink;
import org.km.exception.ResourceNotFoundException;
import org.km.service.CrudService;
import org.km.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.km.controller.ControllerConstants.DRINK_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(DRINK_URL)
public class DrinkController extends AbstractCrudController<Drink> {
    @Autowired
    private DrinkService service;

    @Override
    protected CrudService<Drink> getService() {
        return service;
    }

    @Override
    protected String getEntityName() {
        return "Дистиллят";
    }

    @Operation(summary = "Получение списка напитков", description="Получение списка напитков")
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Drink>> getDrinks() {
        //return new ResponseEntity<>(service.getDrinks(), HttpStatus.OK);
        return super.getAll();
    }

    @Operation(summary = "Получение напитка", description="Получение напитка по id")
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<Drink> getDrink(@PathVariable Integer id) {
        return super.getById(id);
    }

    @Operation(summary = "Удаление дистиллята", description="Удаление существующего дистиллята")
    @Override
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return super.delete(id);
    }
}
