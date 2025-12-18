package org.km.db.repository;

import org.km.db.view.BarrelHistoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarrelHistoryRepository extends JpaRepository<BarrelHistoryView, Integer> {
}
