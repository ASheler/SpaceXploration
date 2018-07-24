package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

public class InsertAboutIntoDbAsyncTask extends AsyncTask<Object, Void, Void> {

    private AboutSpaceX aboutSpaceX;

    public InsertAboutIntoDbAsyncTask(AboutSpaceX aboutSpaceX) {
        this.aboutSpaceX = aboutSpaceX;
    }

    @Override
    protected Void doInBackground(Object... objects) {
        Context context = (Context) objects[0];

        LaunchesDatabase db = LaunchesDatabase.getInstance(context);

        db.pastLaunchesDao().insertAboutSpaceX(aboutSpaceX);

        return null;
    }
}
