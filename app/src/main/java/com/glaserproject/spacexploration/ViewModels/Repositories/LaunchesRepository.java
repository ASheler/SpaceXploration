package com.glaserproject.spacexploration.ViewModels.Repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.SpacexDao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main repository for Launches Architecture
 */

@Singleton
public class LaunchesRepository {

    private final ApiClient webservice;
    private final SpacexDao spacexDao;
    private final Executor executor;


    @Inject
    public LaunchesRepository(ApiClient webservice, SpacexDao spacexDao, Executor executor) {
        this.webservice = webservice;
        this.spacexDao = spacexDao;
        this.executor = executor;
    }

    //refresh launches and return them as LiveData
    public LiveData<List<Launch>> getLaunches() {

        refreshLaunches();
        return spacexDao.getLaunches();
    }


    //refresh launches from web
    private void refreshLaunches() {
        executor.execute(() -> {

            //set new Refresh reference
            Date maxRefresh = getMaxRefreshTime(new Date());
            //get all launches with older time
            List<Launch> launchesToRefresh = spacexDao.launchesToRefresh(maxRefresh);
            Launch anyLaunch = spacexDao.getAnyLaunch();
            //check if we have any
            boolean refreshLaunchesExists = (launchesToRefresh.size() > 0);

            //if we have some launches to update or we don't have any launches at all
            if ((anyLaunch == null) | refreshLaunchesExists) {
                //call for launches
                webservice.getLaunches().enqueue(new Callback<List<Launch>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Launch>> call, @NonNull Response<List<Launch>> response) {
                        executor.execute(() -> {

                            //get launches into local variable
                            List<Launch> launches = response.body();

                            //edit last Refresh reference for all launches
                            int i = 0;
                            while (launches != null && i < launches.size()) {
                                launches.get(i).setLastRefresh(new Date());
                                i++;
                            }

                            //insert newly fetched launches into db
                            spacexDao.insertLaunches(launches);
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Launch>> call, @NonNull Throwable t) {
                    }
                });
            }


        });
    }

    private Date getMaxRefreshTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, NetConstants.LAUNCHES_FRESH_TIMEOUT_MINUTES);
        return cal.getTime();
    }


}
