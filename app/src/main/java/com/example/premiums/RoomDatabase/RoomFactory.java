package com.example.premiums.RoomDatabase;

import android.content.Context;

import androidx.room.Room;

public class RoomFactory {

    private static PersonalDatabase personalDatabase;


    public static PersonalDatabase getPersonalDatabase(Context context) {

        if (personalDatabase == null) {
            personalDatabase = Room.databaseBuilder(context, PersonalDatabase.class, "premiums_db")
                    .build();
        }
        return personalDatabase;
    }
}
