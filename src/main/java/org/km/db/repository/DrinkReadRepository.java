package org.km.db.repository;

import org.km.db.view.DrinkView;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkReadRepository extends ReadRepository<DrinkView, Integer> {
}
