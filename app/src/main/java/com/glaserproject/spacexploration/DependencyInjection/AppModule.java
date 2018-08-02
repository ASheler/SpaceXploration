package com.glaserproject.spacexploration.DependencyInjection;

import android.app.Application;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.SpacexDao;
import com.glaserproject.spacexploration.Room.SpacexDatabase;
import com.glaserproject.spacexploration.ViewModels.Repositories.LaunchesRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AppModule for Dependency Injection
 */

@Module(includes = ViewModelModule.class)
public class AppModule {


    @Provides
    @Singleton
    SpacexDatabase provideDatabase(Application application) {
        return SpacexDatabase.getInstance(application);
    }

    @Provides
    @Singleton
    SpacexDao provideLaunchesDao(SpacexDatabase database) {
        return database.spacexDao();
    }


    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    LaunchesRepository provideLaunchesRepository(ApiClient webservice, SpacexDao userDao, Executor executor) {
        return new LaunchesRepository(webservice, userDao, executor);
    }

    /* NETWORK INJECTION */

    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(NetConstants.API_BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ApiClient provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(ApiClient.class);
    }
}
