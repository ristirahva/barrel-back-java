package org.km.db.repository;

import org.km.db.view.DrinkView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkView, Integer> {
}
