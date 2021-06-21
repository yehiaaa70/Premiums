package com.example.premiums.Models;

import android.widget.ImageView;

public class UserCard_Model {
    int userImage;
    String userName;
    String userPhone;
    String Url;
    boolean book;

    public UserCard_Model(String userName, String userPhone, String url, boolean book) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.Url = url;
        this.book = book;
    }

    public int getUserImage() {
        return userImage;
    }

    public void setUserImage(int userImage) {
        this.userImage = userImage;
    }

    public boolean isBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
