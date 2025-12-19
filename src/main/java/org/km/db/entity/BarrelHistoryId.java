package org.km.db.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BarrelHistoryId implements Serializable {
    private Integer drinkId;
    private Integer barrelId;

    private BarrelHistoryId() {
    }

    public BarrelHistoryId(Integer drinkId, Integer barrelId) {
        this.drinkId = drinkId;
        this.barrelId = barrelId;
    }


    public Integer getDrinkId() {
        return drinkId;
    }

    public Integer getBarrelId() {
        return barrelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarrelHistoryId that = (BarrelHistoryId) o;
        return drinkId == that.drinkId && barrelId == that.barrelId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(drinkId, barrelId);
    }

    @Override
    public String toString() {
        return "DrinkInBarrelId{" +
                "drinkId=" + drinkId +
                ", barrelId=" + barrelId +
                '}';
    }
}
