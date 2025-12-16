package org.km.controller;

import org.km.db.entity.Wood;
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
//@RequestMapping("/api/v1/woods")
public class WoodController {

    @Autowired
    private WoodService service;

    @Operation(summary = "Получение списка древесины", description="Получение списка материалов для бочек(они могут быть и смешанными)")
    @RequestMapping(method = RequestMethod.GET,
            value = WOOD_URL,
            produces = "application/json")
    public ResponseEntity<List<Wood>> getWoods() {
        return new ResponseEntity<>(service.getWoods(), HttpStatus.OK);
    }

    @Operation(summary = "Получение древесины", description="Получение материала для бочек(он может быть и смешанными)")
    @RequestMapping(method = RequestMethod.GET,
            value = WOOD_URL + "/{id}",
            produces = "application/json")
    public ResponseEntity<Wood> getWood(@PathVariable Integer id) {
        Optional<Wood> optionalWood = service.getWood(id);
        return optionalWood.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создание древесины", description="Добавление новой древесины")
    @PostMapping(value = WOOD_URL, produces = "application/json")
    public ResponseEntity<Wood> addWood(@RequestBody Wood wood) {
        return ResponseEntity.ok(service.addWood(wood));
    }
}
