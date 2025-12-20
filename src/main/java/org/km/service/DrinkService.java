package org.km.service;

import org.km.db.entity.Drink;
import org.km.db.repository.DrinkReadRepository;

import org.km.db.repository.DrinkWriteRepository;
import org.km.db.view.DrinkView;
import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkService  extends AbstractCrudService <DrinkView, DrinkReadRepository, Drink, DrinkWriteRepository> {

    private static final Logger log = LoggerFactory.getLogger(DrinkService.class);

    @Autowired
    public DrinkService(DrinkReadRepository readRepository, DrinkWriteRepository writeRepository) {
        super(readRepository, writeRepository);
    }

    @Override
    protected String getEntityName() {
        return "Производитель бочек";
    }

    @Override
    public void delete(Integer id) {
        var drinkView = readRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Запись в сущности %s с id=%s не найдена", getEntityName(), id)
        ));
        if (drinkView.isFilled()) {
            throw new DeleteParentEntityException("Невозможно удалить выдержанный напиток");
        } else {
            writeRepository.deleteById(id);
        }
    }
}
