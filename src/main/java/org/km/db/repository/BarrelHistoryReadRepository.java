package org.km.db.repository;

import org.km.db.view.BarrelHistoryView;
import org.springframework.stereotype.Repository;

@Repository
public interface BarrelHistoryReadRepository extends org.springframework.data.jpa.repository.JpaRepository<BarrelHistoryView, Integer> {
//    @Query("SELECT NEW  org.km.dto.BarrelHistoryDTO(b.id, b.volume, d.id, d.name, w.id, w.name, db.dateStart, db.dateEnd, db.alcoholStart, db.alcoholEnd, db.description) " +
//            "FROM DrinkInBarrel db, Drink d, Barrel b, Wood w " +
//            "WHERE db.drink.id = d.id AND db.barrel.id = b.id AND b.wood.id = w.id  " +
//            "ORDER BY db.dateStart")
//    @Query("SELECT NEW  org.km.dto.BarrelHistoryDTO(db.alcoholEnd, db.description) " +
//            "FROM DrinkInBarrel db " +
//            //"WHERE db.drink.id = d.id AND db.barrel.id = b.id AND b.wood.id = w.id  " +
//            "ORDER BY db.dateStart")
//    List<BarrelHistoryDTO>findBarrelHistory();
}
