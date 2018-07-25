package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

public class CheckIfLaunchesInDb extends AsyncTask<Object, Void, Boolean> {

    private CheckInfoListener listener;

    public CheckIfLaunchesInDb(CheckInfoListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        Context context = (Context) objects[0];

        LaunchesDatabase db = LaunchesDatabase.getInstance(context);
        Launch launch = db.pastLaunchesDao().getAnyLaunch();
        if (launch == null){
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onPostExecute(Boolean dbIsFull) {
        super.onPostExecute(dbIsFull);
        listener.onDbChecked(dbIsFull);
    }

    public interface CheckInfoListener{
        void onDbChecked(Boolean dbIsFull);
    }
}
