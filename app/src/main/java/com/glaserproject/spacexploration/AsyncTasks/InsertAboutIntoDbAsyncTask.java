package com.glaserproject.spacexploration.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.Room.SpacexDatabase;

/**
 * AsyncTask to insert AboutSpacex data into Db
 */

public class InsertAboutIntoDbAsyncTask extends AsyncTask<Object, Void, Void> {

    private AboutSpaceX aboutSpaceX;

    public InsertAboutIntoDbAsyncTask(AboutSpaceX aboutSpaceX) {
        this.aboutSpaceX = aboutSpaceX;
    }

    /**
     * @param objects must contain:
     *                object[0] == Context;
     */

    @Override
    protected Void doInBackground(Object... objects) {
        Context context = (Context) objects[0];
        //get Instance
        SpacexDatabase db = SpacexDatabase.getInstance(context);

        //insert data
        db.spacexDao().insertAboutSpaceX(aboutSpaceX);

        return null;
    }
}
