package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.db.entity.Cooper;
import org.km.service.CooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.km.controller.ControllerConstants.COOPER_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CooperController {

    @Autowired
    private CooperService cooperService;

    @Operation(summary = "Получение списка производителей", description="Получение списка производителей")
    @RequestMapping(method = RequestMethod.GET,
            value = COOPER_URL,
            produces = "application/json")
    public ResponseEntity<List<Cooper>> getCoopers() {
        return new ResponseEntity<>(cooperService.getCoopers(), HttpStatus.OK);
    }
}
