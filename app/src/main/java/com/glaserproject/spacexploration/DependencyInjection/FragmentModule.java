package com.glaserproject.spacexploration.DependencyInjection;

import com.glaserproject.spacexploration.Fragments.CompanyInfoFragment;
import com.glaserproject.spacexploration.Fragments.LaunchesMainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract CompanyInfoFragment contributeCompanyInfoFragment();
    @ContributesAndroidInjector
    abstract LaunchesMainFragment contributeLaunchesMainFragment();
}