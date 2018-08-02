package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.Room.SpacexDatabase;


/**
 * AsyncTask to check if we have any Company Info data in db
 */

public class CheckIfInfoInDb extends AsyncTask<Object, Void, Boolean> {


    private CheckInfoListener listener;

    //init with listener for onPostExecute Interface
    public CheckIfInfoInDb(CheckInfoListener listener) {
        this.listener = listener;
    }

    /**
     * @param objects must contain:
     *                object[0] == Context;
     */

    @Override
    protected Boolean doInBackground(Object... objects) {
        Context context = (Context) objects[0];
        //create db connection
        SpacexDatabase db = SpacexDatabase.getInstance(context);
        //get one milestone from Db
        Milestone milestone = db.spacexDao().getOneMilestone();
        //return logic if Milestone != null
        return milestone != null;
    }

    @Override
    protected void onPostExecute(Boolean dbIsFull) {
        super.onPostExecute(dbIsFull);
        //notify listener
        listener.onDbChecked(dbIsFull);
    }

    public interface CheckInfoListener {
        void onDbChecked(Boolean dbIsFull);
    }
}
