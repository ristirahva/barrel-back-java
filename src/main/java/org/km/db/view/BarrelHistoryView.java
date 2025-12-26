package org.km.db.view;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name="barrel_history")
public class BarrelHistoryView {
    // Внутренний класс для композиции ключа
    @Embeddable
    static class Key implements Serializable {

        @Column(name = "drink_id")
        private Integer drinkId;

        @Column(name = "barrel_id")
        private Integer barrelId;

        // Конструктор
        public Key(Integer drinkId, Integer barrelId) {
            this.drinkId = drinkId;
            this.barrelId = barrelId;
        }

        // Безаргументный конструктор нужен для ORM
        protected Key() {
        }

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
    private Integer barrelVolume;

    @Column(name="drink_name")
    private String drinkName;

    @Column(name="wood_id")
    private Integer woodId;

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

    public BarrelHistoryView(Key key, int barrelVolume, String drinkName, Integer woodId, String woodName, LocalDate dateStart, LocalDate dateEnd, Integer alcoholStart, Integer alcoholEnd, String description) {
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

    public Integer getBarrelVolume() {
        return barrelVolume;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public Integer getWoodId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarrelHistoryView that = (BarrelHistoryView) o;
        return barrelVolume == that.barrelVolume && woodId == that.woodId && Objects.equals(key, that.key) && Objects.equals(drinkName, that.drinkName) && Objects.equals(woodName, that.woodName) && Objects.equals(dateStart, that.dateStart) && Objects.equals(dateEnd, that.dateEnd) && Objects.equals(alcoholStart, that.alcoholStart) && Objects.equals(alcoholEnd, that.alcoholEnd) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, barrelVolume, drinkName, woodId, woodName, dateStart, dateEnd, alcoholStart, alcoholEnd, description);
    }

    @Override
    public String toString() {
        return "BarrelHistoryView{" +
                "key=" + key +
                ", barrelVolume=" + barrelVolume +
                ", drinkName='" + drinkName + '\'' +
                ", woodId=" + woodId +
                ", woodName='" + woodName + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", alcoholStart=" + alcoholStart +
                ", alcoholEnd=" + alcoholEnd +
                ", description='" + description + '\'' +
                '}';
    }
}
