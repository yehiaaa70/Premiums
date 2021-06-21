package com.example.premiums.RoomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.premiums.Models.CusCategory_model;
import com.example.premiums.Models.Information_Users_Model;

@Database(entities = {Information_Users_Model.class , CusCategory_model.class}, version = 1, exportSchema = false)

public abstract class PersonalDatabase extends RoomDatabase {
    public abstract PersonalInfoDAO getPersonalDAO();
    public abstract CategoryDAO categoryDAO();

}
