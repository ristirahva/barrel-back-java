package org.km.service;

import org.km.db.entity.Cooper;

import org.km.db.view.CooperView;
import org.km.db.repository.CooperReadRepository;
import org.km.db.repository.CooperWriteRepository;

import org.km.dto.CooperDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
