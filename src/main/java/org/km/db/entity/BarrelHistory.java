package org.km.db.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * История напитков в бочке
 */

@Entity
@Table(name="barrel_history")
public class BarrelHistory {

    @EmbeddedId
    private BarrelHistoryId id;

    /**
     * Выдерживаемый напиток.
     */
    @ManyToOne
    @MapsId("drinkId")
    @JoinColumn(name = "drink_id")
    private Drink drink;

    /**
     * Бочка, в которой выдерживается напиток.
     */
    @ManyToOne
    @MapsId("barrelId")
    @JoinColumn(name = "barrel_id")
    private Barrel barrel;

    /**
     * Дата начала выдержки.
     */
    @Column(name="date_start")
    private LocalDate dateStart;

    /**
     * Дата окончания выдержки.
     */
    @Column(name="date_end")
    private LocalDate dateEnd;

    /**
     * Начальная выдержка.
     */
    @Column(name="alcohol_start")
    private Integer alcoholStart;

    /**
     * Итоговая выдержка.
     */
    @Column(name="alcohol_end")
    private Integer alcoholEnd;

    /**
     * Примечание.
     */
    @Column
    private String description;

    /**
     * Дата и время создания.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarrelHistory that = (BarrelHistory) o;
        return Objects.equals(id, that.id) && Objects.equals(drink, that.drink) && Objects.equals(barrel, that.barrel) && Objects.equals(dateStart, that.dateStart) && Objects.equals(dateEnd, that.dateEnd) && Objects.equals(alcoholStart, that.alcoholStart) && Objects.equals(alcoholEnd, that.alcoholEnd) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drink, barrel, dateStart, dateEnd, alcoholStart, alcoholEnd, description);
    }

    @Override
    public String toString() {
        return "BarrelHistory{" +
                "id=" + id +
                ", drink=" + drink +
                ", barrel=" + barrel +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", alcoholStart=" + alcoholStart +
                ", alcoholEnd=" + alcoholEnd +
                ", description='" + description + '\'' +
                '}';
    }
}
