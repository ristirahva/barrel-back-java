package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.db.entity.Barrel;
import org.km.db.entity.Cooper;
import org.km.dto.CooperDTO;
import org.km.exception.ResourceNotFoundException;
import org.km.service.CooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.km.controller.ControllerConstants.COOPER_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CooperController {

    @Autowired
    private CooperService service;

    @Operation(summary = "Получение списка производителей", description="Получение списка производителей")
    @RequestMapping(method = RequestMethod.GET,
            value = COOPER_URL,
            produces = "application/json")
    public ResponseEntity<List<CooperDTO>> getCoopers() {
        return new ResponseEntity<>(service.getCoopers(), HttpStatus.OK);
    }

    @Operation(summary = "Получение производителя", description="Получение производителя по id")
    @RequestMapping(method = RequestMethod.GET,
            value = COOPER_URL + "/{id}",
            produces = "application/json")
    public ResponseEntity<Cooper> getCooper(@PathVariable Integer id) {
        Optional<Cooper> cooper = service.getCooper(id);
        if (cooper.isPresent()) {
            return new ResponseEntity<>(cooper.get(), HttpStatus.OK);
        }
        else {
            throw new ResourceNotFoundException(String.format("Бочка с id=%d не найдена" , id));
        }
    }
}
