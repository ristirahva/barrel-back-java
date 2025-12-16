package org.km.db.repository;

import org.km.db.entity.Cooper;
import org.km.dto.CooperDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CooperRepository extends JpaRepository<Cooper, Integer> {
    @Query("SELECT NEW org.km.dto.CooperDTO(c.id, c.name, c.url, count(b)) FROM Cooper c LEFT JOIN Barrel b ON c.id = b.cooper.id group by c.id ORDER BY c.name")
    List<CooperDTO> findAllWithBarrelCount();
}
