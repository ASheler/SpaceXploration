package com.glaserproject.spacexploration.ViewModels.Repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.LaunchesDao;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository for AboutSpacex ViewModel
 */

@Singleton
public class AboutSpacexRepository {

    private final ApiClient webservice;
    private final LaunchesDao launchesDao;
    private final Executor executor;

    @Inject
    public AboutSpacexRepository(ApiClient webservice, LaunchesDao launchesDao, Executor executor) {
        this.webservice = webservice;
        this.launchesDao = launchesDao;
        this.executor = executor;
    }


    public LiveData<AboutSpaceX> getAboutSpacex() {
        //refresh from net
        refreshAboutSpacex();
        //return  liveData
        return launchesDao.getAboutSpaceXLiveData();
    }


    //refresh About from web
    private void refreshAboutSpacex() {
        executor.execute(() -> {

            //set new Refresh reference
            Date maxRefresh = getMaxRefreshTime(new Date());

            //get old to check if we need to update
            AboutSpaceX aboutSpaceX = launchesDao.getAboutSpaceX();

            //check if we got something
            boolean refreshNeeded;
            if (aboutSpaceX != null) {
                //check if we need to update
                refreshNeeded = (aboutSpaceX.getLastRefresh().getTime() < maxRefresh.getTime());
            } else {
                refreshNeeded = true;
            }

            //if we need to update, - update data
            if (refreshNeeded) {
                webservice.getAboutSpaceX().enqueue(new Callback<AboutSpaceX>() {
                    @Override
                    public void onResponse(@NonNull Call<AboutSpaceX> call, @NonNull Response<AboutSpaceX> response) {
                        executor.execute(() -> {
                            //get response into local var
                            AboutSpaceX newAboutSpaceX = response.body();
                            //if response != null, insert new refresh date and insert into db
                            if (newAboutSpaceX != null) {
                                newAboutSpaceX.setLastRefresh(new Date());
                                launchesDao.insertAboutSpaceX(newAboutSpaceX);
                            }

                        });

                    }

                    @Override
                    public void onFailure(@NonNull Call<AboutSpaceX> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }


    //get time of max refresh
    private Date getMaxRefreshTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -NetConstants.ABOUT_SPACEX_FRESH_TIMEOUT_MINUTES);
        return cal.getTime();
    }


}
