package com.glaserproject.spacexploration.DependencyInjection;

import com.glaserproject.spacexploration.Fragments.CompanyInfoFragment;
import com.glaserproject.spacexploration.Fragments.LaunchesMainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//DI module for fragments

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract LaunchesMainFragment contributeLaunchesMainFragment();
}