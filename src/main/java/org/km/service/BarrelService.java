package org.km.service;

import jakarta.persistence.LockModeType;
import org.km.db.entity.Barrel;
import org.km.db.repository.BarrelRepository;

import org.km.db.view.BarrelView;

import org.km.exception.DeleteParentEntityException;
import org.km.exception.EntityNotFoundException;
import org.km.exception.PrimaryKeyChangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class BarrelService extends AbstractCrudService <BarrelView, BarrelRepository>{

    private static final Logger log = LoggerFactory.getLogger(BarrelService.class);

    @Autowired
    public BarrelService(BarrelRepository repository) {
        super(repository);
    }

    public List<BarrelView> getBarrelsByWoodId(Integer woodId) {
        return repository.findByWoodId(woodId);
    }

    public List<BarrelView> getBarrelsByCooperId(Integer cooperId) {
        return repository.findByCooperId(cooperId);
    }

    public List<BarrelView> getActive() {
        return repository.findByIsArchived(false);
    }

    public List<BarrelView> getInactive() {
        return repository.findByIsArchived(true);
    }

    // TODO продумать и реализовать логику архивации.
    public void archive(int id) {
//        var barrel = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("бочка с id=" +id + " не найдена!"));
//        barrel = new BarrelView(barrel.getCooperId(), barrel.getWood(), barrel.getId(), barrel.getVolume(), barrel.getDescription(), true);
//        repository.save(barrel);
    }

    @Override
    public BarrelView update(Integer id, BarrelView barrelView) {
        if (id != barrelView.getId()) {
            throw new PrimaryKeyChangeException("Первичный ключ невозможно изменять");
        }
        return super.update(id, barrelView);
    }

    @Override
    public void delete(Integer id) {
        var barrelView = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                String.format("Запись в сущности %s с id=%s не найдена", getEntityName(), id)
        ));

        if (barrelView.getFillCount() > 0) {
            throw new DeleteParentEntityException("Невозможно удалить бочку, в которой выдерживался дистиллят");
        } else {
            repository.deleteById(id);
        }
    }

    @Override
    protected String getEntityName() {
        return "Бочка";
    }
}
