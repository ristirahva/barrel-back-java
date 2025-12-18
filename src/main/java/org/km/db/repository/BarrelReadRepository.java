package org.km.db.repository;

import org.km.db.view.BarrelView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarrelReadRepository extends ReadRepository<BarrelView, Integer> {
    List<BarrelView> findByWoodId(int woodId);
    List<BarrelView> findByCooperId(int cooperId);
    List <BarrelView> findByIsArchived(boolean isArchived);
}
