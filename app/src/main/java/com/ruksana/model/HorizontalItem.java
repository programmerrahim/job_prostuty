package com.ruksana.model;

public class HorizontalItem {
    private String imageUrl; // URL of the image
    private String category;     // New text field

    public HorizontalItem() {
        // Default constructor required for Firebase
    }

    public HorizontalItem(String imageUrl, String category) {
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return category;
    }
}