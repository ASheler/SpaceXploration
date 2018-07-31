package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

public class CheckIfInfoInDb extends AsyncTask<Object, Void, Boolean> {

    private CheckInfoListener listener;

    public CheckIfInfoInDb(CheckInfoListener listener) {
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Object... objects) {
        Context context = (Context) objects[0];

        LaunchesDatabase db = LaunchesDatabase.getInstance(context);
        Milestone milestone = db.pastLaunchesDao().getOneMilestone();
        return milestone != null;
    }

    @Override
    protected void onPostExecute(Boolean dbIsFull) {
        super.onPostExecute(dbIsFull);
        listener.onDbChecked(dbIsFull);
    }

    public interface CheckInfoListener {
        void onDbChecked(Boolean dbIsFull);
    }
}
