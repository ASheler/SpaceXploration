package com.glaserproject.spacexploration.DependencyInjection;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.AppDatabase;
import com.glaserproject.spacexploration.Room.PastLaunchesDao;
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
    AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, "MyDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    PastLaunchesDao provideLaunchesDao(AppDatabase database) { return database.pastLaunchesDao(); }


    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    LaunchesRepository provideLaunchesRepository(ApiClient webservice, PastLaunchesDao userDao, Executor executor) {
        return new LaunchesRepository(webservice, userDao, executor);
    }

    // --- NETWORK INJECTION ---

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
