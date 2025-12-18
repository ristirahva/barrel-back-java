package org.km.db.repository;

import org.km.db.view.BarrelView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarrelReadRepository extends ReadRepository<BarrelView, Integer> {
//    @Query("SELECT NEW org.km.dto.BarrelDTO(b.id, b.volume, b.description, c.id, c.name, w.id, w.name) FROM Barrel b, Cooper c, Wood w WHERE b.cooper.id = c.id AND b.wood.id = w.id")
//    List<BarrelDTO> findAllWithParents();

    List<BarrelView> findByWoodId(int woodId);
}
