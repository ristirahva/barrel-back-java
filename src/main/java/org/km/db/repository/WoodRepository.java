package org.km.db.repository;

import org.km.db.entity.Wood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WoodRepository extends JpaRepository <Wood, Integer>{
}
