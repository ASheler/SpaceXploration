package com.glaserproject.spacexploration.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

import java.util.List;

public class MilestonesViewModel extends AndroidViewModel {

    private LiveData<List<Milestone>> milestones;


    public MilestonesViewModel(@NonNull Application application) {
        super(application);

        LaunchesDatabase db = LaunchesDatabase.getInstance(application);
        milestones = db.pastLaunchesDao().getAllMilestones();
    }


    public LiveData<List<Milestone>> getMilestones() {
        return milestones;
    }
}
