package com.example.audi.uaspenir;

/**
 * Created by asus on 5/22/2017.
 */

public class image_has_comment {
    private int Image_imageID;
    private int Comment_commentID;

    public image_has_comment(int Image_imageID, int Comment_commentID) {
        this.setImage_imageID(Image_imageID);
        this.setComment_commentID(Comment_commentID);
    }

    public int getImage_imageID() {
        return Image_imageID;
    }

    public void setImage_imageID(int Image_imageID) {
        this.Image_imageID = Image_imageID;
    }

    public int getComment_commentID() {
        return Comment_commentID;
    }

    public void setComment_commentID(int Comment_commentID) {
        this.Comment_commentID = Comment_commentID;
    }
}
