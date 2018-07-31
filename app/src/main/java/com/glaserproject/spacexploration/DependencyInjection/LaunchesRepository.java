package com.glaserproject.spacexploration.DependencyInjection;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.LaunchesDao;

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

    @SuppressWarnings("FieldCanBeLocal")
    private static int FRESH_TIMEOUT_IN_MINUTES = 3;

    private final ApiClient webservice;
    private final LaunchesDao launchesDao;
    private final Executor executor;


    @Inject
    public LaunchesRepository(ApiClient webservice, LaunchesDao launchesDao, Executor executor) {
        this.webservice = webservice;
        this.launchesDao = launchesDao;
        this.executor = executor;
    }

    //refresh launches and return them as LiveData
    public LiveData<List<Launch>> getLaunches() {

        refreshLaunches();
        return launchesDao.getLaunches();
    }


    //refresh launches from web
    private void refreshLaunches() {
        executor.execute(() -> {

            //set new Refresh reference
            Date maxRefresh = getMaxRefreshTime(new Date());
            //get all launches with older time
            List<Launch> launchesToRefresh = launchesDao.launchesToRefresh(maxRefresh);
            Launch anyLaunch = launchesDao.getAnyLaunch();
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
                            launchesDao.insertLaunches(response.body());
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
        cal.add(Calendar.MINUTE, -FRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }


}
