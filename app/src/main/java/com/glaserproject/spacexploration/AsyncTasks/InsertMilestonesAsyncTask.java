package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

import java.util.List;

/**
 * AsyncTask to insert Milestones data into Db
 */


public class InsertMilestonesAsyncTask extends AsyncTask<Object, Void, Void> {

    private List<Milestone> mMilestones;

    public InsertMilestonesAsyncTask(List<Milestone> milestones) {
        this.mMilestones = milestones;
    }

    /**
     * @param objects must contain:
     *                object[0] == Context;
     */

    @Override
    protected Void doInBackground(Object... objects) {

        Context context = (Context) objects[0];

        //get db instance
        LaunchesDatabase db = LaunchesDatabase.getInstance(context);

        //insert data
        db.pastLaunchesDao().insertMilestones(mMilestones);

        return null;
    }
}
