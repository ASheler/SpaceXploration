package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchMilestonesAsyncTask extends AsyncTask<Object, Void, List<Milestone>> {

    private AsyncTaskProgressListener progressListener;

    public FetchMilestonesAsyncTask(AsyncTaskProgressListener progressListener) {
        this.progressListener = progressListener;
    }


    @Override
    protected List<Milestone> doInBackground(Object... objects) {

        Context context = (Context) objects[0];
        LaunchesDatabase db = LaunchesDatabase.getInstance(context);
        Log.d("Milestones", "fetching from Db");

        return db.pastLaunchesDao().getAllMilestones();
    }

    @Override
    protected void onPostExecute(List<Milestone> milestones) {
        super.onPostExecute(milestones);
        progressListener.onTaskComplete(milestones);

    }




    public interface AsyncTaskProgressListener{

        void onTaskComplete(List<Milestone> milestones);
    }


}
