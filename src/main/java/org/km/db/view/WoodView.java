package org.km.db.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="wood_view")
public class WoodView {
    @Id
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="name_lat")
    private String nameLat;

    /**
     * Количество бочек из этого материала
     */
    @Column(name="barrel_count")
    private Integer barrelCount;

    public WoodView(int id, String name, String nameLat, Integer barrelCount) {
        this.id = id;
        this.name = name;
        this.nameLat = nameLat;
        this.barrelCount = barrelCount;
    }

    private WoodView() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameLat() {
        return nameLat;
    }

    public Integer getBarrelCount() {
        return barrelCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WoodView woodView = (WoodView) o;
        return id == woodView.id && Objects.equals(name, woodView.name) && Objects.equals(nameLat, woodView.nameLat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameLat);
    }

    @Override
    public String toString() {
        return "WoodView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameLat='" + nameLat + '\'' +
                ", barrelCount=" + barrelCount +
                '}';
    }
}
