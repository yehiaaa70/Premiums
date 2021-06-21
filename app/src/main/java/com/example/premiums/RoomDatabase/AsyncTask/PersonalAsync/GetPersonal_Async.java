package com.example.premiums.RoomDatabase.AsyncTask.PersonalAsync;

import android.os.AsyncTask;

import com.example.premiums.Models.Information_Users_Model;
import com.example.premiums.RoomDatabase.PersonalInfoDAO;

import java.util.List;

public class GetPersonal_Async extends AsyncTask<Void,Void, List<Information_Users_Model>> {

    private PersonalInfoDAO infoDAO;

    public GetPersonal_Async(PersonalInfoDAO infoDAO) {
        this.infoDAO = infoDAO;
    }

    @Override
    protected List<Information_Users_Model> doInBackground(Void... voids) {
        return infoDAO.getAllInfo();
    }
}
