package com.glaserproject.spacexploration.DependencyInjection;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;
import com.glaserproject.spacexploration.Room.LaunchesDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    LaunchesDatabase provideDatabase(Application application) {
        return LaunchesDatabase.getInstance(application);
    }

    @Provides
    @Singleton
    LaunchesDao provideLaunchesDao(LaunchesDatabase database) { return database.pastLaunchesDao(); }


    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    LaunchesRepository provideLaunchesRepository(ApiClient webservice, LaunchesDao userDao, Executor executor) {
        return new LaunchesRepository(webservice, userDao, executor);
    }

    // NETWORK INJECTION

    private static String BASE_URL = NetConstants.API_BASE_URL;

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    ApiClient provideApiWebservice(Retrofit restAdapter) {
        return restAdapter.create(ApiClient.class);
    }
}
