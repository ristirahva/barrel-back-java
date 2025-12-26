package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.km.db.entity.BarrelHistory;
import org.km.db.view.BarrelHistoryView;
import org.km.service.BarrelHistoryService;
import org.km.service.CrudService;

import static org.km.controller.ControllerConstants.BARREL_HISTORY_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(BARREL_HISTORY_URL)
public class BarrelHistoryController extends AbstractCrudController <BarrelHistoryView> {
    @Autowired
    private BarrelHistoryService service;
    @Override
    protected CrudService<BarrelHistoryView> getService() {
        return service;
    }

    @Override
    protected String getEntityName() {
        return "История бочки";
    }

    @Operation(summary = "История бочек", description="Получение истории бочек")
    @RequestMapping(method = RequestMethod.GET, value = "", produces = "application/json")
    public ResponseEntity<List<BarrelHistoryView>> getBarrelHistory() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }
}
