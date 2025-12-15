package org.km.db.repository;

import org.km.db.entity.Barrel;
import org.km.db.entity.Wood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarrelRepository extends JpaRepository<Barrel, Integer> {
    List<Barrel> findByWoodId(int woodId);
}
