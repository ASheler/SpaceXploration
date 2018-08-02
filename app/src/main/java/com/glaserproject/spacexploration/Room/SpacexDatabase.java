package com.glaserproject.spacexploration.Room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.LaunchObjects.Launch;

/**
 * Database class for Room
 */

@Database(entities = {Launch.class, Milestone.class, AboutSpaceX.class}, version = 6, exportSchema = false)
@TypeConverters(DataConverters.class)
public abstract class SpacexDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DB_NAME = "SpaceXdatabase.db";
    private static SpacexDatabase sInstance;

    //get instance
    public static SpacexDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                //build DB
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        SpacexDatabase.class,
                        DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    //call for DAO
    public abstract SpacexDao spacexDao();
}
