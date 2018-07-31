package com.glaserproject.spacexploration.DependencyInjection;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Dependency injection for Launches
 */


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
