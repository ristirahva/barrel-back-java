package org.km.db.view;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Бондарная компания - производитель бочек.
 */
@Entity
@Table(name="cooper_view")
public class CooperView {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "cooper_id_seq", // Название автоматически создаваемой последовательности PostgreSQL
            allocationSize = 1
    )
    private Integer id;

    /**
     * Название компании.
     */
    @Column(name = "name")
    private String name;

    /**
     * Сайт компании.
     */
    private String url;
    Integer barrelCount;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Integer getBarrelCount() {
        return barrelCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CooperView that = (CooperView) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url);
    }

    @Override
    public String toString() {
        return "CooperView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", barrelCount=" + barrelCount +
                '}';
    }
}
