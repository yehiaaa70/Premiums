package com.example.premiums.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "PersonalInfo")
public class Information_Users_Model implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "age")
    private String age;

    @ColumnInfo(name = "_id")
    private String ID;

    @ColumnInfo(name = "Customer_price")
    private String CusPrice;

    @ColumnInfo(name = "total_price")
    private String TotalPrice;

    @ColumnInfo(name = "percent")
    private String Percentage;

    @ColumnInfo(name = "date")
    private String Date;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "booking")
    private boolean book;

    private String Url;

    public Information_Users_Model() {
    }

    public Information_Users_Model(String name, String phone, String address, String age, String ID, String cusPrice, String totalPrice, String percentage, String date, String image, boolean book) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.ID = ID;
        this.CusPrice = cusPrice;
        this.TotalPrice = totalPrice;
        this.Percentage = percentage;
        this.Date = date;
        this.image = image;
        this.book = book;
    }

    public Information_Users_Model(String name, String phone, String address, String age, String url) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.age = age;
        Url = url;
    }

    public boolean isBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCusPrice() {
        return CusPrice;
    }

    public void setCusPrice(String cusPrice) {
        CusPrice = cusPrice;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
