package org.km.service;

import org.km.db.repository.WoodRepository;
import org.km.db.view.DrinkView;
import org.km.db.view.WoodView;
import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;
import org.km.exception.PrimaryKeyChangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WoodService extends AbstractCrudService <WoodView, WoodRepository>{

    private static final Logger log = LoggerFactory.getLogger(WoodService.class);

    @Autowired
    public WoodService(WoodRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Материал бочек";
    }

    @Override
    public WoodView add (WoodView voodView) {
        return super.add(voodView);
    }

    @Override
    public WoodView update(Integer id, WoodView woodView) {
        if (id != woodView.getId()) {
            throw new PrimaryKeyChangeException("Первичный ключ невозможно изменять");
        }
        return super.update(id, woodView);
    }

    @Override
    public void delete(Integer id) {
        var woodView = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Запись в сущности %s с id=%s не найдена", getEntityName(), id)
        ));
        if (woodView.getBarrelCount() > 0) {
            throw new DeleteParentEntityException("Невозможно удалить древесину, из которой есть изготовленные бочки");
        } else {
            repository.deleteById(id);
        }
    }
}
