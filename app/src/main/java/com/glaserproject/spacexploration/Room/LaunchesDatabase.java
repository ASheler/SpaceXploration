package com.glaserproject.spacexploration.Room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.LaunchObjects.Launch;

@Database(entities = {Launch.class, Milestone.class, AboutSpaceX.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverters.class)
public abstract class LaunchesDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DB_NAME = "SpaceXdatabase.db";
    private static LaunchesDatabase sInstance;

    public static LaunchesDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                //build DB
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        LaunchesDatabase.class,
                        DB_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    //call for DAO
    public abstract LaunchesDao pastLaunchesDao();
}
