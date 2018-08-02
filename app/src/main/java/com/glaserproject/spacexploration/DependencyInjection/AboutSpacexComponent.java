package com.glaserproject.spacexploration.DependencyInjection;


import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {ActivityModule.class, FragmentModule.class, AppModule.class})
public interface AboutSpacexComponent {

    void inject(App app);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AboutSpacexComponent build();
    }
}
