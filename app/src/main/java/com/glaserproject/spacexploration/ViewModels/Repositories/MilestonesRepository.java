package com.glaserproject.spacexploration.ViewModels.Repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
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
 * Repository for Milestones ViewModel
 */

@Singleton
public class MilestonesRepository {


    private final ApiClient webservice;
    private final SpacexDao spacexDao;
    private final Executor executor;

    @Inject
    public MilestonesRepository(ApiClient webservice, SpacexDao spacexDao, Executor executor) {
        this.webservice = webservice;
        this.spacexDao = spacexDao;
        this.executor = executor;
    }


    public LiveData<List<Milestone>> getMilestones() {
        //refresh from net
        refreshAboutSpacex();
        //return liveData from Db
        return spacexDao.getAllMilestones();
    }


    //refresh launches from web
    private void refreshAboutSpacex() {
        executor.execute(() -> {

            //set new Refresh reference
            Date maxRefresh = getMaxRefreshTime(new Date());
            //get all milestones with older time
            List<Milestone> milestonesToRefresh = spacexDao.milestonesToRefresh(maxRefresh);
            Milestone anyMilestone = spacexDao.getOneMilestone();
            //check if we have any
            boolean refreshMilestonesExists = (milestonesToRefresh.size() > 0);


            if ((anyMilestone == null) | refreshMilestonesExists) {
                webservice.getAllMilestones().enqueue(new Callback<List<Milestone>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Milestone>> call, @NonNull Response<List<Milestone>> response) {

                        executor.execute(() -> {

                            //get milestones into local variable
                            List<Milestone> milestones = response.body();

                            //edit last Refresh reference for all milestones
                            int i = 0;
                            while (milestones != null && i < milestones.size()) {
                                milestones.get(i).setLastRefresh(new Date());
                                i++;
                            }
                            //insert newly fetched launches into db
                            spacexDao.insertMilestones(milestones);

                        });

                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Milestone>> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }

    private Date getMaxRefreshTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -NetConstants.MILESTONES_FRESH_TIMEOUT_MINUTES);
        return cal.getTime();
    }

}
