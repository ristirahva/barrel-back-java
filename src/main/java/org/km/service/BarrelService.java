package org.km.service;

import org.km.db.entity.Barrel;
import org.km.db.repository.BarrelRepository;
import org.km.dto.BarrelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarrelService {
    private final BarrelRepository repository;

    private static final Logger log = LoggerFactory.getLogger(BarrelService.class);

    @Autowired
    public BarrelService(BarrelRepository repository) {
        this.repository = repository;
    }

    public List<BarrelDTO> getBarrels() {
        return repository.findAllWithParents();
    }

    public List<Barrel> getBarrelsByWoodId(int woodId) {
        return repository.findByWoodId(woodId);
    }

    public Optional<Barrel> getBarrel(int barrelId) {
        return repository.findById(barrelId);
    }

    public Barrel updateBarrel(Integer id, Barrel barrel) {
        Optional<Barrel> optionalBarrel = repository.findById(id);
        if (optionalBarrel.isEmpty()) {
            return null;
        }
        barrel = new Barrel(id, barrel.getVolume(), barrel.getDescription());
        return repository.save(barrel);
    }

    public Barrel addBarrel(Barrel barrel) {
        return repository.save(barrel);
    }

    public void deleteBarrel(Integer id) {
        repository.deleteById(id);
    }
}
