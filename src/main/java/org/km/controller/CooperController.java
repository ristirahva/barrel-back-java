package org.km.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.km.db.entity.Cooper;
import org.km.dto.CooperDTO;
import org.km.exception.ResourceNotFoundException;
import org.km.service.CooperService;
import org.km.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.km.controller.ControllerConstants.COOPER_URL;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(COOPER_URL)
public class CooperController extends AbstractCrudController <Cooper>{

    @Autowired
    private CooperService service;

    @Override
    protected CrudService<Cooper> getService() {
        return service;
    }
    @Override
    protected String getEntityName() {
        return "Производитель бочек";
    }

    @Operation(summary = "Получение списка производителей", description="Получение списка производителей")
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<CooperDTO>> getCoopers() {
        return new ResponseEntity<>(service.getCoopers(), HttpStatus.OK);
    }

    @Operation(summary = "Получение производителя", description="Получение производителя по id")
    public ResponseEntity<Cooper> getCooper(@PathVariable Integer id) {
        return super.getById(id);
    }

    @Operation(summary = "Создание производителя", description="Добавление нового производителя")
    //@PostMapping(value = COOPER_URL, produces = "application/json")
    @Override
    public ResponseEntity<Cooper> add(@RequestBody Cooper cooper) {
        return super.add(cooper);
    }

    @Operation(summary = "Обновление производителя", description="Обновление существующего производителя")
    //@PostMapping(value = COOPER_URL, produces = "application/json")
    @Override
    public ResponseEntity<Cooper> update(@PathVariable int id, @RequestBody Cooper cooper) {
        return super.update(id, cooper);
    }
}
