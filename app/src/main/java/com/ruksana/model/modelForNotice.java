package com.ruksana.model;

public class modelForNotice {

    String name,data,category;

    // Empty constructor for Firestore
    public modelForNotice() {}

    public modelForNotice(String name, String data, String category) {
        this.name = name;
        this.data = data;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
