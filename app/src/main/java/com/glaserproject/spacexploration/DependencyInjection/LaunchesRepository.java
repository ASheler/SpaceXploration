package com.glaserproject.spacexploration.DependencyInjection;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.PastLaunchesDao;

import java.io.IOException;
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

    private final ApiClient webservice;
    private final PastLaunchesDao launchesDao;
    private final Executor executor;


    @Inject
    public LaunchesRepository (ApiClient webservice, PastLaunchesDao launchesDao, Executor executor){
        this.webservice = webservice;
        this.launchesDao = launchesDao;
        this.executor = executor;
    }

    public LiveData<List<Launch>> getLaunches(){
        refreshLaunches();
        return launchesDao.getPastLaunches();
    }



    private void refreshLaunches(){
        executor.execute(() ->  {
            
                webservice.getLaunches().enqueue(new Callback<List<Launch>>() {
                    @Override
                    public void onResponse(Call<List<Launch>> call, Response<List<Launch>> response) {
                        executor.execute(() -> {
                            Log.d("hovno", "hovno");
                            launchesDao.insertPastLaunches(response.body());
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Launch>> call, Throwable t) {
                        Log.d("prdel", "prdel");
                    }
                });

        });
    }






}
