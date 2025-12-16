package org.km.service;

import org.km.db.entity.Wood;
import org.km.db.repository.WoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class WoodService {
    private final WoodRepository repository;

    private static final Logger log = LoggerFactory.getLogger(WoodService.class);

    @Autowired
    public WoodService(WoodRepository repository) {
        this.repository = repository;
    }

    public List<Wood> getWoods() {
        return repository.findAll();
    }

    public Optional<Wood> getWood(int id) {
        return repository.findById(id);
    }

    public Wood addWood(Wood wood) {
        return repository.save(wood);
    }

    public void deleteBWood(Integer id) {
        repository.deleteById(id);
    }
}
