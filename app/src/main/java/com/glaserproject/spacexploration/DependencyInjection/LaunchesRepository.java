package com.glaserproject.spacexploration.DependencyInjection;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

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

@Singleton
public class LaunchesRepository {

    private static int FRESH_TIMEOUT_IN_MINUTES = 3;
    private static final String LOG = "Launches Repo";

    private final ApiClient webservice;
    private final LaunchesDao launchesDao;
    private final Executor executor;


    @Inject
    public LaunchesRepository (ApiClient webservice, LaunchesDao launchesDao, Executor executor){
        this.webservice = webservice;
        this.launchesDao = launchesDao;
        this.executor = executor;
    }

    public LiveData<List<Launch>> getLaunches(){
        refreshLaunches();
        Log.d(LOG, "returining Launches");
        return launchesDao.getLaunches();

        //return launchesDao.getLaunches();
    }



    private void refreshLaunches(){
        executor.execute(() ->  {

            //set new Refresh reference
            Date maxRefresh = getMaxRefreshTime(new Date());
            //get all launches with older time
            List<Launch> launchesToRefresh = launchesDao.launchesToRefresh(maxRefresh);
            Launch anyLaunch = launchesDao.getAnyLaunch();
            boolean refreshLaunchesExists = (launchesToRefresh.size() > 0);
            Log.d(LOG, "refreshing Launches");

            //if we have some launches to update
            if ((anyLaunch == null) | refreshLaunchesExists) {

                webservice.getLaunches().enqueue(new Callback<List<Launch>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Launch>> call, @NonNull Response<List<Launch>> response) {
                        executor.execute(() -> {
                            Log.d(LOG, "got launches from net successfully");
                            List<Launch> launches = response.body();
                            int i = 0;
                            //edit last Refresh reference for all launches
                            while (i < launches.size()){
                                launches.get(i).setLastRefresh(new Date());
                                i++;
                            }
                            launchesDao.insertLaunches(response.body());
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Launch>> call, Throwable t) {
                        Log.d(LOG, "getting launches from net failed: " + t);
                    }
                });
            } else {
                Log.d(LOG, "Loading from memory");
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
