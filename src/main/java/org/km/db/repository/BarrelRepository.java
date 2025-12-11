package org.km.db.repository;

import org.km.db.entity.Barrel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarrelRepository extends JpaRepository<Barrel, Integer> {

}
