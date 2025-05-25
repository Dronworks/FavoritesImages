package org.dronworks.testphotos;

public class Photo {
    private int imageResId;
    private String location;
    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public Photo(int imageResId, String location) {
        this.imageResId = imageResId;
        this.location = location;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getLocation() {
        return location;
    }
}