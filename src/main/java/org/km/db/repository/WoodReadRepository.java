package org.km.db.repository;

import org.km.db.view.WoodView;
import org.springframework.stereotype.Repository;

@Repository
public interface WoodReadRepository extends ReadRepository <WoodView, Integer>{
}
