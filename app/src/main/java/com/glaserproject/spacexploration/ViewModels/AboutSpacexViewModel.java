package com.glaserproject.spacexploration.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.ViewModels.Repositories.AboutSpacexRepository;

import javax.inject.Inject;

/**
 * ViewModel for AboutSpaceX data from room Db
 */

public class AboutSpacexViewModel extends ViewModel {

    private LiveData<AboutSpaceX> aboutSpaceX;
    private AboutSpacexRepository aboutSpacexRepository;

    @Inject
    public AboutSpacexViewModel(AboutSpacexRepository aboutSpacexRepository) {
        this.aboutSpacexRepository = aboutSpacexRepository;
    }

    //init
    public void init() {
        if (aboutSpaceX != null) {
            return;
        }
        aboutSpaceX = aboutSpacexRepository.getAboutSpacex();
    }


    public LiveData<AboutSpaceX> getAboutSpaceX() {
        return this.aboutSpaceX;
    }
}
