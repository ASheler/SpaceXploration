package com.glaserproject.spacexploration.DependencyInjection;

import com.glaserproject.spacexploration.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

//DI module for activities

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();
}