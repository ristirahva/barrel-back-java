package org.km.db.repository;

import org.km.db.entity.DrinkInBarrel;
import org.km.dto.BarrelHistoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkInBarrelRepository extends JpaRepository<DrinkInBarrel, Integer> {
//    @Query("SELECT NEW  org.km.dto.BarrelHistoryDTO(b.id, b.volume, d.id, d.name, w.id, w.name, db.dateStart, db.dateEnd, db.alcoholStart, db.alcoholEnd, db.description) " +
//            "FROM DrinkInBarrel db, Drink d, Barrel b, Wood w " +
//            "WHERE db.drink.id = d.id AND db.barrel.id = b.id AND b.wood.id = w.id  " +
//            "ORDER BY db.dateStart")
    @Query("SELECT NEW  org.km.dto.BarrelHistoryDTO(db.alcoholEnd, db.description) " +
            "FROM DrinkInBarrel db " +
            //"WHERE db.drink.id = d.id AND db.barrel.id = b.id AND b.wood.id = w.id  " +
            "ORDER BY db.dateStart")
    List<BarrelHistoryDTO>findBarrelHistory();
}
