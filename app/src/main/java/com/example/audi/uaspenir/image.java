package com.example.audi.uaspenir;

/**
 * Created by asus on 5/22/2017.
 */

public class image {
    private int imageID;
    private String imagename;
    private String ekstensi;
    private int Category_categoryID;

    public image(int imageID, String imagename, String ekstensi, int Category_categoryID) {
        this.setImageID(imageID);
        this.setImagename(imagename);
        this.setEkstensi(ekstensi);
        this.setCategory_categoryID(Category_categoryID);
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getEkstensi() {
        return ekstensi;
    }

    public void setEkstensi(String ekstensi) {
        this.ekstensi = ekstensi;
    }

    public int getCategory_categoryID() {
        return Category_categoryID;
    }

    public void setCategory_categoryID(int Category_categoryID) {
        this.Category_categoryID = Category_categoryID;
    }
}
