package org.km.db.view;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Дистиллят.
 */
@Entity
@Table(name="drink_view")
public class DrinkView {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @SequenceGenerator(
            name = "sequence_generator",
            sequenceName = "drink_id_seq", // Название автоматически создаваемой последовательности PostgreSQL
            allocationSize = 1
    )
    private Integer id;

    /**
     сырьё (фрукты, зерно и тд)
     */
    @Column(name="source")
    private String source;

    /**
     * название напитка
     */
    @Column(name="name")
    private String name;

    /**
     * итоговая крепость (если выдерживался в бочке, то после неё)
     */
    @Column(name="alcohol")
    private Integer alcohol;

    /**
     * примечания
     */
    @Column(name = "description")
    private String description;

    /**
     * выдерживался ли напиток в бочке
     */
    @Column(name="is_filled")
    private Boolean filled;

    /**
     * дата окончания выдержки
     */
    @Column(name = "date_end")
    private LocalDate dateEnd;

    public DrinkView(Integer id, String source, String name, Integer alcohol, String description, Boolean isFilled, LocalDate dateEnd) {
        this.id = id;
        this.source = source;
        this.name = name;
        this.alcohol = alcohol;
        this.description = description;
        this.filled = isFilled;
        this.dateEnd = dateEnd;
    }

    public DrinkView(Integer id, String source, String name, Integer alcohol, String description) {
        this.id = id;
        this.source = source;
        this.name = name;
        this.alcohol = alcohol;
        this.description = description;
    }

    public DrinkView(String source, String name, Integer alcohol, String description) {
        this.source = source;
        this.name = name;
        this.alcohol = alcohol;
        this.description = description;
    }

    private DrinkView() {
    }

    public Integer getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getName() {
        return name;
    }

    public Integer getAlcohol() {
        return alcohol;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isFilled() {
        return filled;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }
}
