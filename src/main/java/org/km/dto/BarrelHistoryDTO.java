package org.km.dto;

import java.time.LocalDate;

public class BarrelHistoryDTO {
    private int barrelId;
    private int barrelVolume;
    private int drinkId;
    private String drinkName;
    private int woodId;
    private String woodName;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int alcoholStart;
    private int alcoholEnd;
    private String description;

    public BarrelHistoryDTO(int alcoholEnd, String description) {
    //public BarrelHistoryDTO(int barrelId, int barrelVolume, int drinkId, String drinkName, int woodId, String woodName, LocalDate dateStart, LocalDate dateEnd, int alcoholStart, int alcoholEnd, String description) {
//        this.barrelId = barrelId;
//        this.barrelVolume = barrelVolume;
//        this.drinkId = drinkId;
//        this.drinkName = drinkName;
//        this.woodId = woodId;
//        this.woodName = woodName;
//        this.dateStart = dateStart;
//        this.dateEnd = dateEnd;
//        this.alcoholStart = alcoholStart;
        this.alcoholEnd = alcoholEnd;
        this.description = description;
    }

    public BarrelHistoryDTO() {
    }

    public int getBarrelId() {
        return barrelId;
    }

    public int getBarrelVolume() {
        return barrelVolume;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public int getWoodId() {
        return woodId;
    }

    public String getWoodName() {
        return woodName;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public int getAlcoholStart() {
        return alcoholStart;
    }

    public int getAlcoholEnd() {
        return alcoholEnd;
    }

    public String getDescription() {
        return description;
    }

}
