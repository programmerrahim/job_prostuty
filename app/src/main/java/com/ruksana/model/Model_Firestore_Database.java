package com.ruksana.model;

public class Model_Firestore_Database {

    String name,categoryForDetails;

    public Model_Firestore_Database() {
    }

    public Model_Firestore_Database(String name, String categoryForDetails) {
        this.name = name;
        this.categoryForDetails = categoryForDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryForDetails() {
        return categoryForDetails;
    }

    public void setCategoryForDetails(String categoryForDetails) {
        this.categoryForDetails = categoryForDetails;
    }
}
