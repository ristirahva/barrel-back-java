package org.km.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
@Table
public class Wood {
    @Id
    private Integer id;
    private String name;
    private String nameLat;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameLat() {
        return nameLat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wood wood = (Wood) o;
        return id == wood.id && Objects.equals(name, wood.name) && Objects.equals(nameLat, wood.nameLat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameLat);
    }

    @Override
    public String toString() {
        return "Wood{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameLat='" + nameLat + '\'' +
                '}';
    }
}
