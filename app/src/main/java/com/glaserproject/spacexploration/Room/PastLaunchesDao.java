package com.glaserproject.spacexploration.Room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.glaserproject.spacexploration.LaunchObjects.Launch;

import java.util.List;

@Dao
public interface PastLaunchesDao {

    @Query("SELECT * FROM past_launches ORDER BY flight_number DESC")
    LiveData<List<Launch>> getPastLaunches();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPastLaunches(List<Launch> pastLaunches);
}
