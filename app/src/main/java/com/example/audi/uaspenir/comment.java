package com.example.audi.uaspenir;

/**
 * Created by asus on 5/22/2017.
 */

public class comment {
    private int commentID;
    private String isiComment;
    private int User_UserID;

    public comment(int commentID, String isiComment, int User_UserID) {
        this.setCommentID(commentID);
        this.setIsiComment(isiComment);
        this.setUser_UserID(User_UserID);
    }

    public int getCommentID() {
        return commentID;
    }


    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }


    public String getIsiComment() {
        return isiComment;
    }


    public void setIsiComment(String isiComment) {
        this.isiComment = isiComment;
    }


    public int getUser_UserID() {
        return User_UserID;
    }


    public void setUser_UserID(int User_UserID) {
        this.User_UserID = User_UserID;
    }
}
