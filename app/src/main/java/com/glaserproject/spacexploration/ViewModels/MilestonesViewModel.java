package com.glaserproject.spacexploration.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.ViewModels.Repositories.MilestonesRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * ViewModel for AboutSpaceX data from room Db
 */

public class MilestonesViewModel extends ViewModel {

    private LiveData<List<Milestone>> milestones;
    private MilestonesRepository milestonesRepository;


    @Inject
    public MilestonesViewModel(MilestonesRepository milestonesRepository) {
        this.milestonesRepository = milestonesRepository;
    }

    //init
    public void init() {
        if (milestones != null) {
            return;
        }
        milestones = milestonesRepository.getMilestones();
    }


    public LiveData<List<Milestone>> getMilestones() {
        return milestones;
    }
}
