package org.km.db.repository;

import org.km.db.entity.Barrel;
import org.km.dto.BarrelDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarrelRepository extends JpaRepository<Barrel, Integer> {
    @Query("SELECT NEW org.km.dto.BarrelDTO(b.id, b.volume, b.description, c.id, c.name, w.id, w.name) FROM Barrel b, Cooper c, Wood w WHERE b.cooper.id = c.id AND b.wood.id = w.id")
    List<BarrelDTO> findAllWithParents();

    List<Barrel> findByWoodId(int woodId);
}
