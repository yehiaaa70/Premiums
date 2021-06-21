package com.example.premiums.RoomDatabase.AsyncTask.CategoryAsync;

import android.os.AsyncTask;

import com.example.premiums.Models.CusCategory_model;
import com.example.premiums.RoomDatabase.CategoryDAO;

public class UpdateCategory_Async extends AsyncTask<CusCategory_model,Void,Void> {
    CategoryDAO categoryDAO;

    public UpdateCategory_Async(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    protected Void doInBackground(CusCategory_model... cusCategory_models) {
        categoryDAO.updateCategory(cusCategory_models[0]);
        return null;
    }
}
