package org.km.db.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
Напиток
 */
@Entity
@Table
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "drink_id_seq", // Название автоматически создаваемой последовательности PostgreSQL
            allocationSize = 1
    )
    private Integer id;

    /**
     * Сырьё для напитка.
     */
    @Column(name = "source")
    private String source;

    /**
     * Наименование напитка.
     */
    @Column(name = "name")
    private String name;

    /**
     * Итоговая крепость.
     */

    @Column(name = "alcohol")
    private int alcohol;

    /**
     * Примечание.
     */
    @Column(name = "description")
    private String description;

    /**
     * Дата и время создания.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Drink(Integer id, String source, String name, int alcohol, String description) {
        this.id = id;
        this.source = source;
        this.name = name;
        this.alcohol = alcohol;
        this.description = description;
    }

    private Drink() {
    }

    public Integer getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getName() {
        return name;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public String getDescription() {
        return description;
    }

    @OneToMany(mappedBy = "drink",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<BarrelHistory> barrelHistories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return id == drink.id && alcohol == drink.alcohol && Objects.equals(source, drink.source) && Objects.equals(name, drink.name) && Objects.equals(description, drink.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, name, alcohol, description);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "description='" + description + '\'' +
                ", alcohol=" + alcohol +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", id=" + id +
                '}';
    }
}
