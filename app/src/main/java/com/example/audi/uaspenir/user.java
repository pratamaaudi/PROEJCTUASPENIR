package com.example.audi.uaspenir;

/**
 * Created by asus on 5/22/2017.
 */

public class user {
    private int UserID;
    private String username;
    private String password;
    private String fullname;

    public user(int UserID, String username, String password, String fullname) {
        this.setUserID(UserID);
        this.setUsername(username);
        this.setPassword(password);
        this.setFullname(fullname);
    }

    public int getUserID() {
        return UserID;
    }


    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
