package org.km.db.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Пребывание напитка в бочке
 */

@Entity
@Table(name="drink_in_barrel")
public class DrinkInBarrel {
    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @ManyToOne
    @MapsId("barrelId")
    @JoinColumn(name = "barrel_id")
    private Barrel barrel;

    private LocalDate dateStart;

    private LocalDate dateEnd;

    public DrinkInBarrel(Drink drink, Barrel barrel, LocalDate dateStart, LocalDate dateEnd, int alcoholStart, int alcoholEnd, String description) {
        this.drink = drink;
        this.barrel = barrel;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.alcoholStart = alcoholStart;
        this.alcoholEnd = alcoholEnd;
        this.description = description;
    }

    public Drink getDrinkId() {
        return drink;
    }

    public Barrel getBarrel() {
        return barrel;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public int getAlcoholStart() {
        return alcoholStart;
    }

    public int getAlcoholEnd() {
        return alcoholEnd;
    }

    public String getDescription() {
        return description;
    }

    private int alcoholStart;
    private int alcoholEnd;
    private String description;
}
