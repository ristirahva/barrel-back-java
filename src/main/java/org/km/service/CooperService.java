package org.km.service;

import org.km.db.entity.Cooper;

import org.km.db.view.CooperView;
import org.km.db.repository.CooperReadRepository;
import org.km.db.repository.CooperWriteRepository;

import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CooperService extends AbstractCrudService <CooperView, CooperReadRepository, Cooper, CooperWriteRepository>{

    private static final Logger log = LoggerFactory.getLogger(CooperService.class);

    @Autowired
    public CooperService(CooperReadRepository readRepository, CooperWriteRepository writeRepository) {
        super(readRepository, writeRepository);
    }

    @Override
    protected String getEntityName() {
        return "Производитель бочек";
    }

    @Override
    public void delete(Integer id) {
        var cooperView = readRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Запись в сущности %s с id=%s не найдена", getEntityName(), id)
        ));
        if (cooperView.getBarrelCount() > 0) {
            throw new DeleteParentEntityException("Невозможно удалить производителя, у которого есть бочки");
        } else {
            writeRepository.deleteById(id);
        }
    }
}
