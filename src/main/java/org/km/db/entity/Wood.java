package org.km.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table
public class Wood {
    @Id
    private int id;
    private String name;
    private String nameLat;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameLat() {
        return nameLat;
    }
}
