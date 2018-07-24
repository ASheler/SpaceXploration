package com.glaserproject.spacexploration.Room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.LaunchObjects.Launch;

import java.util.Date;
import java.util.List;

@Dao
public interface LaunchesDao {

    @Query("SELECT * FROM launches ORDER BY flight_number DESC")
    LiveData<List<Launch>> getLaunches();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLaunches(List<Launch> launches);

    @Query("SELECT * FROM launches WHERE lastRefresh < :lastRefreshMax")
    List<Launch> launchesToRefresh(Date lastRefreshMax);

    @Query("SELECT * FROM launches LIMIT 1")
    Launch getAnyLaunch();

    @Query("SELECT * FROM launches WHERE launch_date_unix < :currentDate ORDER BY flight_number DESC")
    List<Launch> getPastLaunches(Date currentDate);

    @Query("SELECT flight_number FROM launches WHERE launch_date_unix < :currentDate ORDER BY flight_number DESC LIMIT 1")
    int getLatestLaunchNumber(Date currentDate);

    @Query("SELECT * FROM launches WHERE flight_number <= :nextFlightNumber ORDER BY flight_number DESC")
    LiveData<List<Launch>> getPastLaunchesAndOne (int nextFlightNumber);

    @Query("SELECT * FROM launches WHERE launch_date_unix > :currentTime ORDER BY flight_number ASC LIMIT 1")
    Launch getNextLaunch(long currentTime);

    @Query("SELECT * FROM milestones ORDER BY event_date_unix DESC")
    List<Milestone> getAllMilestones();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMilestones(List<Milestone> milestones);

    @Query("SELECT * FROM about_spacex LIMIT 1")
    AboutSpaceX getAboutSpaceX();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAboutSpaceX(AboutSpaceX aboutSpaceX);

}
