package org.dronworks.testphotos;

public class Photo {

    private final int imageDbId;
    private final int imageResId;
    private boolean isFavorite;
    private final String name;
    private final String description;

    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public Photo(int imageResId, String name, int imageDbId, String description) {
        this.imageResId = imageResId;
        this.name = name;
        this.imageDbId = imageDbId;
        this.description = description;
    }

    public int getImageDbId() {
        return imageDbId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
}