package org.km.db.view;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="wood_view")
public class WoodView {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "wood_id_seq",
            allocationSize = 1
    )
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="name_lat")
    private String nameLat;

    /**
     * Количество бочек из этого материала
     */
    @Column(name="barrel_count")
    private Integer barrelCount;

    public WoodView(Integer id, String name, String nameLat, Integer barrelCount) {
        this.id = id;
        this.name = name;
        this.nameLat = nameLat;
        this.barrelCount = barrelCount;
    }

    public WoodView(String name, String nameLat) {
        this.name = name;
        this.nameLat = nameLat;
    }

    private WoodView() {
    }

    public Integer getId() {
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
