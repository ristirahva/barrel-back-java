package org.km.service;

import org.km.db.entity.Cooper;
import org.km.db.repository.CooperRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CooperService {
    private final CooperRepository repository;

    private static final Logger log = LoggerFactory.getLogger(CooperService.class);

    @Autowired
    public CooperService(CooperRepository repository) {
        this.repository = repository;
    }

    public List<Cooper> getCoopers() {
        return repository.findAll();
    }
}
