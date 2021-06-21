package com.example.premiums.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.premiums.Models.CusCategory_model;
import com.example.premiums.Models.Information_Users_Model;

import java.util.List;

@Dao
public interface CategoryDAO {


    @Query("SELECT * FROM Category")
    List<CusCategory_model> getAllCategory();

    @Insert
    void insertCategory(CusCategory_model categoryEntity);

    @Update
    void updateCategory(CusCategory_model categoryEntity);

    @Delete
    void deleteCategory(CusCategory_model categoryEntity);

}
