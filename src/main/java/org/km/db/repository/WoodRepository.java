package org.km.db.repository;

import org.km.db.view.WoodView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WoodRepository extends JpaRepository<WoodView, Integer> {
}
