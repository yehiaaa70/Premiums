package com.example.premiums.RoomDatabase.AsyncTask.CategoryAsync;

import android.os.AsyncTask;

import com.example.premiums.Models.CusCategory_model;
import com.example.premiums.RoomDatabase.CategoryDAO;

public class AddCategory_Async extends AsyncTask<CusCategory_model, Void, Void> {

    private CategoryDAO categoryDAO;

    public AddCategory_Async(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    protected Void doInBackground(CusCategory_model... cusCategory_models) {
        categoryDAO.insertCategory(cusCategory_models[0]);
        return null;
    }
}
