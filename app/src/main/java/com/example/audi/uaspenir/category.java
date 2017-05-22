package com.example.audi.uaspenir;

/**
 * Created by asus on 5/22/2017.
 */

public class category {
    private int categoryID;
    private String categoryName;

    public category(int categoryID, String categoryName) {
        this.setCategoryID(categoryID);
        this.setCategoryName(categoryName);
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
