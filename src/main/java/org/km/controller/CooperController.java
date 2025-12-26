package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;

import org.km.db.view.CooperView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.km.db.entity.Cooper;
import org.km.service.CooperService;
import org.km.service.CrudService;

import java.util.List;

import static org.km.controller.ControllerConstants.COOPER_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(COOPER_URL)
public class CooperController extends AbstractCrudController <CooperView>{

    @Autowired
    private CooperService service;

    @Override
    protected CrudService<CooperView> getService() {
        return service;
    }

    @Override
    protected String getEntityName() {
        return "Производитель бочек";
    }

    @Operation(summary = "Получение списка производителей", description="Получение списка производителей")
    @RequestMapping(method = RequestMethod.GET,
            value="",
            produces = "application/json")
    public ResponseEntity<List<CooperView>> getCoopers() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Получение производителя", description="Получение производителя по id")
    @RequestMapping(method = RequestMethod.GET,
            value="/{id}",
            produces = "application/json")
    public ResponseEntity<CooperView> getCooper(@PathVariable Integer id) {
        return super.getById(id);
    }

    @Operation(summary = "Создание производителя", description="Добавление нового производителя")
    @PostMapping(value = "", produces = "application/json")
    @Override
    public ResponseEntity<CooperView> add(@RequestBody CooperView cooperView) {
        return super.add(cooperView);
    }

    @Operation(summary = "Обновление производителя", description="Обновление существующего производителя")
    @PutMapping(value = "/{id}", produces = "application/json")
    @Override
    public ResponseEntity<CooperView> update(@PathVariable Integer id, @RequestBody CooperView cooperView) {
        return super.update(id, cooperView);
    }

    @Operation(summary = "Удаление производителя", description="Удаление существующего производителя")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
