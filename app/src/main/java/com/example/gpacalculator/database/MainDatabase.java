package com.example.gpacalculator.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SubjectDetailEntity.class, SubjectLocationEntity.class}, version = 1, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {

    private static final String LOG_TAG = MainDatabase.class.getSimpleName();
    private static MainDatabase sInstance;


    public static MainDatabase getInstance(Context context) {
        if (sInstance != null) {
            Log.d(LOG_TAG, "dtb is already instantiated, retrieving data...");
            return sInstance;
        }
        synchronized (MainDatabase.class) {
            Log.d(LOG_TAG, "creating a new database instance");
            sInstance = Room.databaseBuilder(context.getApplicationContext(), MainDatabase.class, "gpa_database.db").build();
            return sInstance;
        }
    }

    public abstract SubjectDetailDAO subjectDetailDAO();

    public abstract SubjectLocationDAO subjectLocationDAO();

}
