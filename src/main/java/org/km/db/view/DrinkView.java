package org.km.db.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name="drink_view")
public class DrinkView {
    @Id
    private Integer id;

    @Column(name="source", columnDefinition = "сырьё (фрукты, зерно и тд")
    private String source;

    @Column(name="name")
    private String name;

    @Column(name="alcohol", columnDefinition = "крепость после выдержки в бочке")
    private Integer alcohol;

    @Column(name = "description")
    private String description;

    @Column(name="is_filled", columnDefinition = "выдерживался ли напиток в бочке")
    private Boolean filled;

    @Column(name = "date_end", columnDefinition = "дата окончания выдержки")
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
