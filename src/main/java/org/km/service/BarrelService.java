package org.km.service;

import org.km.db.entity.Barrel;
import org.km.db.entity.Cooper;
import org.km.db.repository.BarrelReadRepository;
import org.km.db.repository.BarrelWriteRepository;
import org.km.db.repository.CooperReadRepository;
import org.km.db.repository.CooperWriteRepository;
import org.km.db.view.BarrelView;
import org.km.db.view.CooperView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BarrelService  extends AbstractCrudService <BarrelView, BarrelReadRepository, Barrel, BarrelWriteRepository>{

    private static final Logger log = LoggerFactory.getLogger(BarrelService.class);

    @Autowired
    public BarrelService(BarrelReadRepository readRepository, BarrelWriteRepository writeRepository) {
        super(readRepository, writeRepository);
    }

    public List<BarrelView> getBarrelsByWoodId(int woodId) {
        return readRepository.findByWoodId(woodId);
    }

    @Override
    protected String getEntityName() {
        return "Бочка";
    }
}
