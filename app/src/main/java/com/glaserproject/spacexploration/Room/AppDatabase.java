package com.glaserproject.spacexploration.Room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.glaserproject.spacexploration.LaunchObjects.Launch;

@Database(entities = {Launch.class}, version = 2, exportSchema = false)
@TypeConverters(DataConverters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DB_NAME = "MyDatabase.db";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                //build DB
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        DB_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    //call for DAO
    public abstract PastLaunchesDao pastLaunchesDao();
}
