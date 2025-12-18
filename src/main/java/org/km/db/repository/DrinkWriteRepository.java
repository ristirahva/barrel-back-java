package org.km.db.repository;

import org.km.db.entity.Drink;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkWriteRepository extends WriteRepository<Drink, Integer>{
}
