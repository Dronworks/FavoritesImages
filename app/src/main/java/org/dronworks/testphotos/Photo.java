package org.dronworks.testphotos;

public class Photo {
    private int imageResId;
    private String location;
    private boolean isFavorite;

    private String name;

    public Photo(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public Photo(int imageResId, String location, String s) {
        this.imageResId = imageResId;
        this.location = location;
        this.name = location;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getLocation() {
        return location;
    }
}