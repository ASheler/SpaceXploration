package com.glaserproject.spacexploration.DependencyInjection;

import com.glaserproject.spacexploration.Fragments.LaunchesMainFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Dependency injection module for fragments
 */

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract LaunchesMainFragment contributeLaunchesMainFragment();
}