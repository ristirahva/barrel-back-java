package org.km.service;

import org.km.db.entity.Barrel;
import org.km.db.entity.Drink;
import org.km.db.repository.DrinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {
    private final DrinkRepository repository;

    private static final Logger log = LoggerFactory.getLogger(DrinkService.class);

    @Autowired
    public DrinkService(DrinkRepository repository) {
        this.repository = repository;
    }

    public List<Drink> getDrinks() {
        return repository.findAll();
    }

    public Optional<Drink> getDrink(int drinkId) {
        return repository.findById(drinkId);
    }
}
