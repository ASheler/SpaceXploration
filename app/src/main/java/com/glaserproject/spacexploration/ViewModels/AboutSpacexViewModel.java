package com.glaserproject.spacexploration.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

/**
 * ViewModel for AboutSpaceX data from room Db
 */

public class AboutSpacexViewModel extends AndroidViewModel {

    private LiveData<AboutSpaceX> aboutSpaceX;

    public AboutSpacexViewModel(@NonNull Application application) {
        super(application);

        //init db and get LiveData
        LaunchesDatabase db = LaunchesDatabase.getInstance(application);
        aboutSpaceX = db.pastLaunchesDao().getAboutSpaceX();
    }

    public LiveData<AboutSpaceX> getAboutSpaceX() {
        return aboutSpaceX;
    }
}
