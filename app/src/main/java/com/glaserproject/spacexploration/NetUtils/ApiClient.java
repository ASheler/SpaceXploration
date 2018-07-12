package com.glaserproject.spacexploration.NetUtils;

import com.glaserproject.spacexploration.LaunchObjects.Launch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClient {

    //get latest launch
    @GET("launches/latest")
    Call<Launch> getLatestLaunch();

    //get all launches
    @GET("launches")
    Call<List<Launch>> getLaunches();

    //get nearest launch
    @GET("launches/next")
    Call<Launch> getNextLaunch();


}
