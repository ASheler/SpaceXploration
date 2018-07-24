package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.util.Pair;
import android.util.Log;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
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

public class FetchAboutSpaceXDataAsyncTask extends AsyncTask<Object, Void, Pair<List<Milestone>, AboutSpaceX>> {

    private AsyncTaskProgressListener progressListener;

    public FetchAboutSpaceXDataAsyncTask(AsyncTaskProgressListener progressListener) {
        this.progressListener = progressListener;
    }


    @Override
    protected Pair<List<Milestone>, AboutSpaceX> doInBackground(Object... objects) {

        Context context = (Context) objects[0];
        LaunchesDatabase db = LaunchesDatabase.getInstance(context);
        Log.d("Milestones", "fetching from Db");

        List<Milestone> milestones = db.pastLaunchesDao().getAllMilestones();
        AboutSpaceX aboutSpaceX = db.pastLaunchesDao().getAboutSpaceX();

        Pair<List<Milestone>, AboutSpaceX> pair = new Pair<>(milestones, aboutSpaceX);


        return pair;
    }

    @Override
    protected void onPostExecute(Pair<List<Milestone>, AboutSpaceX> dataPair) {
        super.onPostExecute(dataPair);
        progressListener.onTaskComplete(dataPair);

    }




    public interface AsyncTaskProgressListener{

        void onTaskComplete(Pair<List<Milestone>, AboutSpaceX> aboutPair);
    }


}
