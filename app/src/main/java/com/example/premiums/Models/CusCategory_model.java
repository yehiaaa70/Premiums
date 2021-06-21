package com.example.premiums.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Category")
public class CusCategory_model implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long _id;

    @ColumnInfo(name = "CateName")
    String CateName;

    @ColumnInfo(name = "CatePrice")
    String CatePrice;

    @ColumnInfo(name = "CateAmount")
    String CateAmount;
    @ColumnInfo(name ="CatePhone")
    String phone;

    public CusCategory_model(String cateName, String catePrice, String cateAmount, String phone) {
        CateName = cateName;
        CatePrice = catePrice;
        CateAmount = cateAmount;
        this.phone = phone;
    }



    public CusCategory_model() {
    }

    public CusCategory_model(String cateName, String catePrice, String cateAmount) {
        CateName = cateName;
        CatePrice = catePrice;
        CateAmount = cateAmount;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }

    public String getCatePrice() {
        return CatePrice;
    }

    public void setCatePrice(String catePrice) {
        CatePrice = catePrice;
    }

    public String getCateAmount() {
        return CateAmount;
    }

    public void setCateAmount(String cateAmount) {
        CateAmount = cateAmount;
    }
}
