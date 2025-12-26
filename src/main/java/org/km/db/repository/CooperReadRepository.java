package org.km.db.repository;

import org.km.db.view.CooperView;
import org.springframework.stereotype.Repository;

@Repository
public interface CooperReadRepository extends org.springframework.data.jpa.repository.JpaRepository<CooperView, Integer> {
}
