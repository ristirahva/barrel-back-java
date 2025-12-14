package org.km.db.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Barrel {
    @Id
    private int id;

    private int volume;

    private String description;

    public Barrel(int id, int volume, String description) {
        this.id = id;
        this.volume = volume;
        this.description = description;
    }

    private Barrel() {}

    public int getId() {
        return id;
    }

    public int getVolume() {
        return volume;
    }

    public String getDescription() {
        return description;
    }

    @OneToMany(mappedBy = "barrel",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<DrinkInBarrel> courseAssociations = new HashSet<>();
}
