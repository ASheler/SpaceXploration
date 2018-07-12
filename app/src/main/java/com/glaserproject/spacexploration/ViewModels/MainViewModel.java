package com.glaserproject.spacexploration.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.Room.AppDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Launch>> launches;


    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase mDb = AppDatabase.getInstance(this.getApplication());
        launches = mDb.pastLaunchesDao().getPastLaunches();
    }

    public LiveData<List<Launch>> getLaunches() {
        return launches;
    }

}
