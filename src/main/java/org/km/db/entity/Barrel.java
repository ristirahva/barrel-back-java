package org.km.db.entity;

import jakarta.persistence.*;
import org.km.db.util.BurnLevelEnumConverter;
import org.km.exception.ApplicationException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table
public class Barrel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "barrel_id_seq", // Название автоматически создаваемой последовательности PostgreSQL
            allocationSize = 1
    )
    private Integer id;

    /**
     * Объём бочки.
     */
    @Column(name="volume")
    private Integer volume;

    /**
     * Степень обжига.
     */
    @Column(name="burn_level")
    @Convert(converter = BurnLevelEnumConverter.class)
    private BurnLevel burnLevel;

    /**
     * Примечание.
     */
    @Column(name="description")
    private String description;

    /**
     * Используется ли ещё бочка.
     */
    @Column(name="is_archived")
    private boolean isArchived;

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

    /**
     * Дата и время создания записи.
     */
    @Column(name="created_at")
    private LocalDateTime createdAt;

    public Barrel(Cooper cooper, Wood wood, Integer id, Integer volume, BurnLevel burnLevel, String description, boolean isArchived) {
        this.cooper = cooper;
        this.wood = wood;
        this.id = id;
        this.volume = volume;
        this.description = description;
        this.isArchived = isArchived;
    }

    private Barrel() {}

    public Integer getId() {
        return id;
    }

    public Integer getVolume() {
        return volume;
    }

    public BurnLevel getBurnLevel() {
        return burnLevel;
    }

    public String getDescription() {
        return description;
    }

    public Cooper getCooper() {
        return cooper;
    }

    public Wood getWood() {
        return wood;
    }

    public boolean isArchived() {
        return isArchived;
    }

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
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public enum BurnLevel {
        LIGHT("слабый"),
        MEDIUM("средний"),
        HEAVY("сильный");

        private final String name;

        BurnLevel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static BurnLevel fromName(String name) {
            return Arrays.stream(values())
                    .filter(burnLevel -> burnLevel.name.equalsIgnoreCase(name))
                    .findFirst()
                    .orElseThrow(() ->new ApplicationException("Несуществующий уровень обжига бочки: " + name));
        }
    }
}
