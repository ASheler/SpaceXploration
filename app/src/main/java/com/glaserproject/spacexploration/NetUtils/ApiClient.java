package com.glaserproject.spacexploration.NetUtils;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.LaunchObjects.Launch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Api helper interface for Retrofit
 */

public interface ApiClient {

    //get latest launch
    @GET("launches/latest")
    Call<Launch> getLatestLaunch();

    //get all launches
    @GET("launches/all")
    Call<List<Launch>> getLaunches();

    //get nearest launch
    @GET("launches/next")
    Call<Launch> getNextLaunch();

    //get All Milestones
    @GET("info/history")
    Call<List<Milestone>> getAllMilestones();

    //get About SpaceX
    @GET("info")
    Call<AboutSpaceX> getAboutSpaceX();


}
