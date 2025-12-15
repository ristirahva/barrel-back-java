package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.db.entity.Drink;
import org.km.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.km.controller.ControllerConstants.DRINK_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DrinkController {
    @Autowired
    private DrinkService drinkService;

    @Operation(summary = "Получение списка напитков", description="Получение списка напитков")
    @RequestMapping(method = RequestMethod.GET,
            value = DRINK_URL,
            produces = "application/json")
    public ResponseEntity<List<Drink>> getDrinks() {
        return new ResponseEntity<>(drinkService.getDrinks(), HttpStatus.OK);
    }
}
