package org.km.db.repository;

import org.km.db.entity.Wood;
import org.springframework.stereotype.Repository;

@Repository
public interface WoodWriteRepository extends WriteRepository<Wood, Integer>{
}
