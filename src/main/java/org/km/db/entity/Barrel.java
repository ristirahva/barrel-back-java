package org.km.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
}
