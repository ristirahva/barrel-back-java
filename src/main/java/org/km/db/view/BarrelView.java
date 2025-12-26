package org.km.db.view;

import jakarta.persistence.*;

import java.time.Period;
import java.util.Objects;

@Entity
@Table(name="barrel_view")
public class BarrelView {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "barrel_id_seq", // Название автоматически создаваемой последовательности PostgreSQL
            allocationSize = 1
    )
    private Integer id;

    @Column(name="volume")
    private Integer volume;

    @Column(name="description")
    private String description;

    @Column(name="cooper_id")
    private Integer cooperId;

    @Column(name="cooper_name")
    private String cooperName;

    @Column(name="wood_id")
    private Integer woodId;

    @Column(name="wood_name")
    private String woodName;

    @Column(name="fill_count")
    private Integer fillCount;

    @Column(name="fill_duration")
    private String fillDuration;

    @Column(name="is_archived")
    private Boolean isArchived;

    public BarrelView(Integer id, Integer volume, String description, Integer cooperId, String cooperName, Integer woodId, String woodName, Integer fillCount, String fillDuration, Boolean isArchived) {
        this.id = id;
        this.volume = volume;
        this.description = description;
        this.cooperId = cooperId;
        this.cooperName = cooperName;
        this.woodId = woodId;
        this.woodName = woodName;
        this.fillCount = fillCount;
        this.fillDuration = fillDuration;
    }

    public BarrelView(Integer id, Integer volume, String description, Integer cooperId, Integer woodId) {
        this.id = id;
        this.volume = volume;
        this.description = description;
        this.cooperId = cooperId;
        this.woodId = woodId;
    }

    public BarrelView(int volume, String description, Integer cooperId, Integer woodId) {
        this.volume = volume;
        this.description = description;
        this.cooperId = cooperId;
        this.woodId = woodId;
    }

    private BarrelView() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getVolume() {
        return volume;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCooperId() {
        return cooperId;
    }

    public String getCooperName() {
        return cooperName;
    }

    public Integer getWoodId() {
        return woodId;
    }

    public String getWoodName() {
        return woodName;
    }

    public Integer getFillCount() {
        return fillCount;
    }

    public String getFillDuration() {
        return fillDuration;
    }

    public Boolean isArchived() {
        return isArchived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BarrelView that = (BarrelView) o;
        return id == that.id && volume == that.volume && Objects.equals(description, that.description) && Objects.equals(cooperId, that.cooperId) && Objects.equals(cooperName, that.cooperName) && Objects.equals(woodId, that.woodId) && Objects.equals(woodName, that.woodName) && Objects.equals(fillCount, that.fillCount) && Objects.equals(fillDuration, that.fillDuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volume, description);
    }

    @Override
    public String toString() {
        return "BarrelView{" +
                "id=" + id +
                ", volume=" + volume +
                ", description='" + description + '\'' +
                ", cooperId=" + cooperId +
                ", cooperName='" + cooperName + '\'' +
                ", woodId=" + woodId +
                ", woodName='" + woodName + '\'' +
                ", fillCount=" + fillCount +
                ", fillDuration=" + fillDuration +
                ", isArchived=" + isArchived +
                '}';
    }
}
