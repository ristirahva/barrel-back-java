package org.km.db.entity;

import jakarta.persistence.*;

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
    private int id;
    private String source;
    private String name;
    private int alcohol;
    private String description;

    public Drink(int id, String source, String name, int alcohol, String description) {
        this.id = id;
        this.source = source;
        this.name = name;
        this.alcohol = alcohol;
        this.description = description;
    }

    private Drink() {
    }

    public int getId() {
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
