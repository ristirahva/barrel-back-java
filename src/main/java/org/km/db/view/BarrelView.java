package org.km.db.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="barrel_view")
public class BarrelView {
    @Id
    private int id;

    @Column(name="volume")
    private int volume;

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
    private boolean isArchived;

    public BarrelView(int id, int volume, String description, Integer cooperId, String cooperName, Integer woodId, String woodName, Integer fillCount, String fillDuration, boolean isArchived) {
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

    private BarrelView() {
    }

    public int getId() {
        return id;
    }

    public int getVolume() {
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

    public boolean isArchived() {
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
