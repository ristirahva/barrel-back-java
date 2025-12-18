package org.km.db.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name="drink_view")
public class DrinkView {
    @Id
    private int id;
    private String source;
    private String name;
    private int alcohol;
    private String description;
    private LocalDate dateEnd;

    public DrinkView(int id, String source, String name, int alcohol, String description, LocalDate dateEnd) {
        this.id = id;
        this.source = source;
        this.name = name;
        this.alcohol = alcohol;
        this.description = description;
        this.dateEnd = dateEnd;
    }

    private DrinkView() {
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getName() {
        return name;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }
}
