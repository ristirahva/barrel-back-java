package org.km.service;

import org.km.db.entity.Barrel;
import org.km.db.repository.BarrelReadRepository;
import org.km.db.repository.BarrelWriteRepository;

import org.km.db.view.BarrelView;

import org.km.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BarrelService extends AbstractCrudService <BarrelView, BarrelReadRepository, Barrel, BarrelWriteRepository>{

    private static final Logger log = LoggerFactory.getLogger(BarrelService.class);

    @Autowired
    public BarrelService(BarrelReadRepository readRepository, BarrelWriteRepository writeRepository) {
        super(readRepository, writeRepository);
    }

    public List<BarrelView> getBarrelsByWoodId(int woodId) {
        return readRepository.findByWoodId(woodId);
    }

    public List<BarrelView> getBarrelsByCooperId(int cooperId) {
        return readRepository.findByCooperId(cooperId);
    }

    public List<BarrelView> getActive() {
        return readRepository.findByIsArchived(false);
    }

    public List<BarrelView> getInactive() {
        return readRepository.findByIsArchived(true);
    }

    public void archive(int id) {
        BarrelView view = getById(id).orElseThrow(() -> new ResourceNotFoundException("бочка с id=" +id + " не найдена!"));
        Barrel barrel = new Barrel(view.getId(), view.getVolume(), view.getDescription(), true);
        writeRepository.save(barrel);
    }

    @Override
    protected String getEntityName() {
        return "Бочка";
    }
}
