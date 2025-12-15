package org.km.dto;

public class BarrelDTO {
    private int id;
    private int volume;
    private String description;
    private int cooperId;
    private String cooperName;
    private int woodId;
    private String woodName;

    public BarrelDTO(int id, int volume, String description, int cooperId, String cooperName, int woodId, String woodName) {
        this.id = id;
        this.volume = volume;
        this.description = description;
        this.cooperId = cooperId;
        this.cooperName = cooperName;
        this.woodId = woodId;
        this.woodName = woodName;
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

    public int getCooperId() {
        return cooperId;
    }

    public String getCooperName() {
        return cooperName;
    }

    public int getWoodId() {
        return woodId;
    }

    public String getWoodName() {
        return woodName;
    }

}
