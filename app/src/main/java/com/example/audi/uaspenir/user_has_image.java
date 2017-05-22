package com.example.audi.uaspenir;

/**
 * Created by asus on 5/22/2017.
 */

public class user_has_image {
    private int User_userID;
    private int Image_imageID;

    public user_has_image(int User_userID, int Image_imageID) {
        this.setUser_userID(User_userID);
        this.setImage_imageID(Image_imageID);
    }

    public int getUser_userID() {
        return User_userID;
    }

    public void setUser_userID(int User_userID) {
        this.User_userID = User_userID;
    }

    public int getImage_imageID() {
        return Image_imageID;
    }

    public void setImage_imageID(int Image_imageID) {
        this.Image_imageID = Image_imageID;
    }
}
