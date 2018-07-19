package com.glaserproject.spacexploration.DependencyInjection;


import android.app.Application;

import com.glaserproject.spacexploration.DependencyInjection.ActivityModule;
import com.glaserproject.spacexploration.DependencyInjection.App;
import com.glaserproject.spacexploration.DependencyInjection.AppModule;
import com.glaserproject.spacexploration.DependencyInjection.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules={ActivityModule.class, FragmentModule.class, AppModule.class})
public interface LaunchesComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        LaunchesComponent build();
    }

    void inject(App app);
}
