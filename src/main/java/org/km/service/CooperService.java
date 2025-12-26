package org.km.service;

import org.km.db.view.CooperView;
import org.km.db.repository.CooperReadRepository;

import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;
import org.km.exception.PrimaryKeyChangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CooperService extends AbstractCrudService <CooperView, CooperReadRepository>{

    private static final Logger log = LoggerFactory.getLogger(CooperService.class);

    @Autowired
    public CooperService(CooperReadRepository repository) {
        super(repository);
    }

    @Override
    protected String getEntityName() {
        return "Производитель бочек";
    }

    @Override
    public CooperView update(Integer id, CooperView cooperView) {
        if (id != cooperView.getId()) {
            throw new PrimaryKeyChangeException("Первичный ключ невозможно изменять");
        }
        return super.update(id, cooperView);
    }

    @Override
    public void delete(Integer id) {
        var cooperView = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Запись в сущности %s с id=%s не найдена", getEntityName(), id)
        ));
        if (cooperView.getBarrelCount() > 0) {
            throw new DeleteParentEntityException("Невозможно удалить производителя, у которого есть бочки");
        } else {
            repository.deleteById(id);
        }
    }
}
