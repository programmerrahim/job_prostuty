package com.ruksana.model;

public class Model_Firestore_Database {

    String name,data,category,description,page,linkText;

    public Model_Firestore_Database() {
    }

    public Model_Firestore_Database(String name, String data, String category, String description, String page, String linkText) {
        this.name = name;
        this.data = data;
        this.category = category;
        this.description = description;
        this.page = page;
        this.linkText = linkText;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }
}
