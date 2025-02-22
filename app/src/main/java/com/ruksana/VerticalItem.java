package com.ruksana;

import java.util.List;

public class VerticalItem {
    private String title;
    private List<HorizontalItem> horizontalItems;

    public VerticalItem() {
        // Default constructor required for Firebase
    }

    public VerticalItem(String title, List<HorizontalItem> horizontalItems) {
        this.title = title;
        this.horizontalItems = horizontalItems;
    }

    public String getTitle() {
        return title;
    }

    public List<HorizontalItem> getHorizontalItems() {
        return horizontalItems;
    }
}