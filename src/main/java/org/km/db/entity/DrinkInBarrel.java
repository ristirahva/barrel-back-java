package org.km.db.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Пребывание напитка в бочке
 */

@Entity
@Table(name="drink_in_barrel")
public class DrinkInBarrel {

    @EmbeddedId
    private DrinkInBarrelId id;
    @ManyToOne
    @MapsId("drinkId")
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @ManyToOne
    @MapsId("barrelId")
    @JoinColumn(name = "barrel_id")
    private Barrel barrel;

    @Column(name="date_start")
    private LocalDate dateStart;

    @Column(name="date_end")
    private LocalDate dateEnd;

    @Column(name="alcohol_start")
    private Integer alcoholStart;

    @Column(name="alcohol_end")
    private Integer alcoholEnd;

    @Column
    private String description;

    public DrinkInBarrel(Drink drink, Barrel barrel, LocalDate dateStart, LocalDate dateEnd, Integer alcoholStart, Integer alcoholEnd, String description) {
        this.drink = drink;
        this.barrel = barrel;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.alcoholStart = alcoholStart;
        this.alcoholEnd = alcoholEnd;
        this.description = description;
    }

    private DrinkInBarrel() {
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

    public Integer getAlcoholStart() {
        return alcoholStart;
    }

    public Integer getAlcoholEnd() {
        return alcoholEnd;
    }

    public String getDescription() {
        return description;
    }

}
