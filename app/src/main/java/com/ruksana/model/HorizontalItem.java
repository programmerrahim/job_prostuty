package com.ruksana.model;

public class HorizontalItem {
    private String imageUrl; // URL of the image
    private String category; // New text field
    private String name;     // New text field

    public HorizontalItem() {
        // Default constructor required for Firebase
    }

    public HorizontalItem(String imageUrl, String category, String name) {
        this.imageUrl = imageUrl;
        this.category = category;
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }
}