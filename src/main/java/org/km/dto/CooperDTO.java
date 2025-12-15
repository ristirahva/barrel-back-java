package org.km.dto;

public class CooperDTO {
    private int id;
    private String name;
    private String url;
    private long barrelCount;

    public CooperDTO(int id, String name, String url, long barrelCount) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.barrelCount = barrelCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public long getBarrelCount() {
        return barrelCount;
    }
}
