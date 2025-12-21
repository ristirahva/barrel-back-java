package org.km.db.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Производитель бочек
 */
@Entity
@Table
public class Cooper {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "cooper_id_seq", // Название автоматически создаваемой последовательности PostgreSQL
            allocationSize = 1
    )
    private Integer id;
    private String name;
    private String url;

    public Cooper(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    private Cooper() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cooper cooper = (Cooper) o;
        return id == cooper.id && Objects.equals(name, cooper.name) && Objects.equals(url, cooper.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url);
    }

    @Override
    public String toString() {
        return "Cooper{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
