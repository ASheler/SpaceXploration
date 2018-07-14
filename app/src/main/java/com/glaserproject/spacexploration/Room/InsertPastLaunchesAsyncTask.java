package com.glaserproject.spacexploration.Room;


import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.LaunchObjects.Launch;

import java.util.List;



public class InsertPastLaunchesAsyncTask extends AsyncTask<Object, Void, Void> {

    private AsyncTaskListeners mListeners;

    public InsertPastLaunchesAsyncTask (AsyncTaskListeners listeners ){
        this.mListeners = listeners;
    }

    @Override
    protected Void doInBackground(Object... objects) {

        Context context = (Context) objects[0];
        @SuppressWarnings("unchecked")
        List<Launch> launches = (List<Launch>) objects[1];

        AppDatabase mDb = AppDatabase.getInstance(context);
        mDb.pastLaunchesDao().insertPastLaunches(launches);
        return null;
    }


    @Override
    protected void onPreExecute() {
        mListeners.onTaskBegin();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListeners.onTaskComplete();
    }

    public interface AsyncTaskListeners{
        void onTaskBegin();
        void onTaskComplete();
    }

}
