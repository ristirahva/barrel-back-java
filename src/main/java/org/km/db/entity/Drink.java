package org.km.db.entity;

import jakarta.persistence.*;

import java.util.HashSet;
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

    public Drink(int id, String source, String name, int alcohol, String description) {
        this.id = id;
        this.source = source;
        this.name = name;
        this.alcohol = alcohol;
        this.description = description;
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

    private String description;

    @OneToMany(mappedBy = "drink",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<DrinkInBarrel> courseAssociations = new HashSet<>();
}
