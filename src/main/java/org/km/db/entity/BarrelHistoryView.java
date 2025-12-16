package org.km.db.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name="barrel_history")
public class BarrelHistoryView {
    // Внутренний класс для композиции ключа
    @Embeddable
    static class Key implements Serializable {

        @Column(name = "drink_id")
        private int drinkId;

        @Column(name = "barrel_id")
        private int barrelId;

        // Конструктор
        public Key(int drinkId, int barrelId) {
            this.drinkId = drinkId;
            this.barrelId = barrelId;
        }

        // Безаргументный конструктор нужен для ORM
        protected Key() {
        }

        // геттеры и сеттеры...

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) return false;
            final Key other = (Key) obj;
            return Objects.equals(drinkId, other.drinkId) && Objects.equals(barrelId, other.barrelId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(drinkId, barrelId);
        }
    }

    @EmbeddedId
    private Key key;

    @Column(name="barrel_volume")
    private int barrelVolume;

    @Column(name="drink_name")
    private String drinkName;

    @Column(name="wood_id")
    private int woodId;

    @Column(name="wood_name")
    private String woodName;

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

    public BarrelHistoryView(Key key, int barrelVolume, String drinkName, int woodId, String woodName, LocalDate dateStart, LocalDate dateEnd, Integer alcoholStart, Integer alcoholEnd, String description) {
        this.key = key;
        this.barrelVolume = barrelVolume;
        this.drinkName = drinkName;
        this.woodId = woodId;
        this.woodName = woodName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.alcoholStart = alcoholStart;
        this.alcoholEnd = alcoholEnd;
        this.description = description;
    }

    private BarrelHistoryView() {
    }

    public Key getKey() {
        return key;
    }

    public int getBarrelVolume() {
        return barrelVolume;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public int getWoodId() {
        return woodId;
    }

    public String getWoodName() {
        return woodName;
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
