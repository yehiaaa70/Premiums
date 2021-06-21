package com.example.premiums.RoomDatabase.AsyncTask.PersonalAsync;

import android.os.AsyncTask;

import com.example.premiums.Models.Information_Users_Model;
import com.example.premiums.RoomDatabase.PersonalInfoDAO;

public class DeletePersonal_Async extends AsyncTask<Information_Users_Model,Void,Void> {
    PersonalInfoDAO infoDAO;

    public DeletePersonal_Async(PersonalInfoDAO infoDAO) {
        this.infoDAO = infoDAO;
    }

    @Override
    protected Void doInBackground(Information_Users_Model... information_users_models) {
        infoDAO.deleteInfo(information_users_models[0]);
        return null;
    }
}
