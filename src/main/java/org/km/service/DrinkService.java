package org.km.service;

import org.km.db.repository.DrinkRepository;

import org.km.db.view.DrinkView;
import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;
import org.km.exception.PrimaryKeyChangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkService  extends AbstractCrudService <DrinkView, DrinkRepository> {

    private static final Logger log = LoggerFactory.getLogger(DrinkService.class);

    @Autowired
    public DrinkService(DrinkRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Производитель бочек";
    }

    @Override
    public DrinkView update(Integer id, DrinkView drinkView) {
        if (id != drinkView.getId()) {
            throw new PrimaryKeyChangeException("Первичный ключ невозможно изменять");
        }
        return super.update(id, drinkView);
    }

    @Override
    public void delete(Integer id) {
        var drinkView = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Запись в сущности %s с id=%s не найдена", getEntityName(), id)
        ));
        if (drinkView.isFilled()) {
            throw new DeleteParentEntityException("Невозможно удалить выдержанный напиток");
        } else {
            repository.deleteById(id);
        }
    }
}
