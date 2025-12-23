package org.km.db.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Материал, из которого изготавливаются бочки.
 */
@Entity
@Table
public class Wood {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "wood_id_seq", // Название автоматически создаваемой последовательности PostgreSQL
            allocationSize = 1
    )
    private Integer id;

    /**
     * Наименовение материала.
     */
    @Column(name="name")
    private String name;

    /**
     * Латинское наименование.
     */
    @Column(name="name_lat")
    private String nameLat;

    /**
     * Дата и время создания.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Wood(Integer id, String name, String nameLat) {
        this.id = id;
        this.name = name;
        this.nameLat = nameLat;
    }

    private Wood() {
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
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
