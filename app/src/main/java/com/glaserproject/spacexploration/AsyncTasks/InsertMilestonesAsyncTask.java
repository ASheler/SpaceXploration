package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

import java.util.List;

public class InsertMilestonesAsyncTask extends AsyncTask<Object, Void, Void> {

    private List<Milestone> mMilestones;

    public InsertMilestonesAsyncTask(List<Milestone> milestones) {
        this.mMilestones = milestones;
    }

    @Override
    protected Void doInBackground(Object... objects) {

        Context context = (Context) objects[0];

        LaunchesDatabase db = LaunchesDatabase.getInstance(context);

        db.pastLaunchesDao().insertMilestones(mMilestones);

        return null;
    }
}
