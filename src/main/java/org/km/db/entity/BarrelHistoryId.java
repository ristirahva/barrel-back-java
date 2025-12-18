package org.km.db.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BarrelHistoryId implements Serializable {
    private int drinkId;
    private int barrelId;

    private BarrelHistoryId() {
    }

    public BarrelHistoryId(int drinkId, int barrelId) {
        this.drinkId = drinkId;
        this.barrelId = barrelId;
    }


    public int getDrinkId() {
        return drinkId;
    }

    public int getBarrelId() {
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
