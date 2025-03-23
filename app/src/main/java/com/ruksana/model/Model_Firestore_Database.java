package com.ruksana.model;

public class Model_Firestore_Database {

    String name,category,data,categoryQ;

    public Model_Firestore_Database() {
    }

    public Model_Firestore_Database(String name, String category, String data, String categoryQ) {
        this.name = name;
        this.category = category;
        this.data = data;
        this.categoryQ = categoryQ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoryQ() {
        return categoryQ;
    }

    public void setCategoryQ(String categoryQ) {
        this.categoryQ = categoryQ;
    }
}
