package com.glaserproject.spacexploration.DependencyInjection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.glaserproject.spacexploration.ViewModels.AboutSpacexViewModel;
import com.glaserproject.spacexploration.ViewModels.FactoryViewModel;
import com.glaserproject.spacexploration.ViewModels.LaunchesViewModel;
import com.glaserproject.spacexploration.ViewModels.MilestonesViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * ViewModel module for Dependency Injection
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LaunchesViewModel.class)
    abstract ViewModel bindMainViewModel(LaunchesViewModel repoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AboutSpacexViewModel.class)
    abstract ViewModel bindAboutSpacexViewModel(AboutSpacexViewModel aboutSpacexViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MilestonesViewModel.class)
    abstract ViewModel bindMilestonesViewModel(MilestonesViewModel milestonesViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
