package org.km.db.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * История напитков в бочке
 */

@Entity
@Table(name="barrel_history")
public class BarrelHistory {

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

    public BarrelHistory(Drink drink, Barrel barrel, LocalDate dateStart, LocalDate dateEnd, Integer alcoholStart, Integer alcoholEnd, String description) {
        this.drink = drink;
        this.barrel = barrel;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.alcoholStart = alcoholStart;
        this.alcoholEnd = alcoholEnd;
        this.description = description;
    }

    private BarrelHistory() {
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
