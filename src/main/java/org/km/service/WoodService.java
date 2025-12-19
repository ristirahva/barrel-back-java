package org.km.service;

import org.km.db.entity.Wood;
import org.km.db.repository.WoodReadRepository;
import org.km.db.repository.WoodWriteRepository;
import org.km.db.view.WoodView;
import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WoodService extends AbstractCrudService <WoodView, WoodReadRepository, Wood, WoodWriteRepository>{

    private static final Logger log = LoggerFactory.getLogger(WoodService.class);

    @Autowired
    public WoodService(WoodReadRepository readRepository, WoodWriteRepository writeRepository) {
        super(readRepository, writeRepository);
    }

    @Override
    protected String getEntityName() {
        return "Материал бочек";
    }

    @Override
    public void delete(Integer id) {
        var woodView = readRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Запись в сущности %s с id=%s не найдена", getEntityName(), id)
        ));
        if (woodView.getBarrelCount() > 0) {
            throw new DeleteParentEntityException("Невозможно удалить древесину, из которой есть изготовленные бочки");
        } else {
            writeRepository.deleteById(id);
        }
    }
}
