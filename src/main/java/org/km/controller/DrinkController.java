package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.db.entity.Cooper;
import org.km.db.entity.Drink;
import org.km.exception.ResourceNotFoundException;
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
public class DrinkController {
    @Autowired
    private DrinkService service;

    @Operation(summary = "Получение списка напитков", description="Получение списка напитков")
    @RequestMapping(method = RequestMethod.GET,
            value = DRINK_URL,
            produces = "application/json")
    public ResponseEntity<List<Drink>> getDrinks() {
        return new ResponseEntity<>(service.getDrinks(), HttpStatus.OK);
    }

    @Operation(summary = "Получение напитка", description="Получение напитка по id")
    @RequestMapping(method = RequestMethod.GET,
            value = DRINK_URL + "/{id}",
            produces = "application/json")
    public ResponseEntity<Drink> getDrink(@PathVariable Integer id) {
        Optional<Drink> drink = service.getDrink(id);
        if (drink.isPresent()) {
            return new ResponseEntity<>(drink.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException(String.format("Бочка с id=%d не найдена" , id));
        }
    }
}
