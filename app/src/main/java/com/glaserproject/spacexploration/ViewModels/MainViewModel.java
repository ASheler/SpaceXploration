package com.glaserproject.spacexploration.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.DependencyInjection.LaunchesRepository;

import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private LiveData<List<Launch>> launches;
    private LaunchesRepository launchesRepository;


    @Inject
    public MainViewModel(LaunchesRepository launchesRepository){
        this.launchesRepository = launchesRepository;

    }

    public void init(){
        if (launches != null){
            return;
        }
        launches = launchesRepository.getLaunches();
    }


    public LiveData<List<Launch>> getLaunches() {
        return this.launches;
    }

}
