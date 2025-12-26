package org.km.db.repository;

import org.km.db.entity.Cooper;
import org.springframework.stereotype.Repository;

@Repository
public interface CooperWriteRepository extends org.springframework.data.repository.CrudRepository<Cooper, Integer> {
//    @Query("SELECT NEW org.km.dto.CooperDTO(c.id, c.name, c.url, count(b)) FROM Cooper c LEFT JOIN Barrel b ON c.id = b.cooper.id group by c.id ORDER BY c.name")
//    List<CooperDTO> findAllWithBarrelCount();
}
