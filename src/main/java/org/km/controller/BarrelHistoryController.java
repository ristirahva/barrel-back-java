package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.db.entity.BarrelHistoryView;
import org.km.service.BarrelHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.km.controller.ControllerConstants.BARREL_HISTORY_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BarrelHistoryController {
    @Autowired
    private BarrelHistoryService service;

    @Operation(summary = "История бочек", description="Получение истории бочек")
    @RequestMapping(method = RequestMethod.GET, value = BARREL_HISTORY_URL, produces = "application/json")
    public ResponseEntity<List<BarrelHistoryView>> getBarrelHistory() {
        return new ResponseEntity<>(service.getBarrelHistory(), HttpStatus.OK);
    }
}
