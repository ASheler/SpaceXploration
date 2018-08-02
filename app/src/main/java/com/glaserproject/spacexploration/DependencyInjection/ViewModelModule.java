package com.glaserproject.spacexploration.DependencyInjection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.glaserproject.spacexploration.ViewModels.FactoryViewModel;
import com.glaserproject.spacexploration.ViewModels.LaunchesViewModel;


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
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
