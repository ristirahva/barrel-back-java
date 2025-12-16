package org.km.service;

import org.km.db.entity.BarrelHistoryView;
import org.km.db.repository.BarrelHistoryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarrelHistoryService {
    private final BarrelHistoryRepository repository;

    private static final Logger log = LoggerFactory.getLogger(BarrelHistoryService.class);

    @Autowired
    public BarrelHistoryService(BarrelHistoryRepository repository) {
        this.repository = repository;
    }

    public List<BarrelHistoryView> getBarrelHistory() {
        return repository.findAll();
    }
}
