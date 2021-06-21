package com.example.premiums.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.premiums.Models.Information_Users_Model;

import java.util.List;

@Dao
public interface PersonalInfoDAO {

    @Query("SELECT * FROM PersonalInfo")
    List<Information_Users_Model> getAllInfo();

    @Insert
    void insertInfo(Information_Users_Model personalEntity);

    @Update
    void updateInfo(Information_Users_Model personalEntity);

    @Delete
    void deleteInfo(Information_Users_Model personalEntity);

}
