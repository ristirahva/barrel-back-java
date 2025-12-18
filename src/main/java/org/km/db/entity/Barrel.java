package org.km.db.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cooper_id")
    private Cooper cooper;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wood_id")
    private Wood wood;

    @OneToMany(mappedBy = "barrel",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<BarrelHistory> barrelHistories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barrel barrel = (Barrel) o;
        return id == barrel.id && volume == barrel.volume && Objects.equals(description, barrel.description) && Objects.equals(cooper, barrel.cooper) && Objects.equals(wood, barrel.wood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volume, description, cooper, wood);
    }

    @Override
    public String toString() {
        return "Barrel{" +
                "id=" + id +
                ", volume=" + volume +
                ", description='" + description + '\'' +
                ", cooper=" + cooper +
                ", wood=" + wood +
                '}';
    }
}
