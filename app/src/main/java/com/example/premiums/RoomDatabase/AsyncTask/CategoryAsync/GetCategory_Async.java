package com.example.premiums.RoomDatabase.AsyncTask.CategoryAsync;

import android.os.AsyncTask;

import com.example.premiums.Models.CusCategory_model;
import com.example.premiums.RoomDatabase.CategoryDAO;

import java.util.List;

public class GetCategory_Async extends AsyncTask<Void,Void, List<CusCategory_model>> {

    private CategoryDAO categoryDAO;

    public GetCategory_Async(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    protected List<CusCategory_model> doInBackground(Void... voids) {
        return categoryDAO.getAllCategory();
    }
}
