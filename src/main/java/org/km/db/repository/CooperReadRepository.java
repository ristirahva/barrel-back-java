package org.km.db.repository;

import org.km.db.view.CooperView;
import org.springframework.stereotype.Repository;

@Repository
public interface CooperReadRepository extends ReadRepository<CooperView, Integer> {
}
