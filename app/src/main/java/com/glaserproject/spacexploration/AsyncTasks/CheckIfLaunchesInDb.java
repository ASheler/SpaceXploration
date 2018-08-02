package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.Room.SpacexDatabase;


/**
 * AsyncTask to check if we have any Launches data in db
 */

public class CheckIfLaunchesInDb extends AsyncTask<Object, Void, Boolean> {

    private CheckInfoListener listener;

    //init with listener
    public CheckIfLaunchesInDb(CheckInfoListener listener) {
        this.listener = listener;
    }

    /**
     * @param objects must contain:
     *                object[0] == Context;
     */

    @Override
    protected Boolean doInBackground(Object... objects) {
        Context context = (Context) objects[0];

        //get db and any launch
        SpacexDatabase db = SpacexDatabase.getInstance(context);
        Launch launch = db.spacexDao().getAnyLaunch();
        //return logic if launch != null
        return launch != null;
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
