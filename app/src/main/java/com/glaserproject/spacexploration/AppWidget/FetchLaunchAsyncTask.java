package com.glaserproject.spacexploration.AppWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

import java.util.Date;

public class FetchLaunchAsyncTask extends AsyncTask<Object, Void, Launch> {
    @Override
    protected Launch doInBackground(Object... objects) {

        //Get Params
        Context context = (Context) objects[0];
        AppWidgetManager appWidgetManager = (AppWidgetManager) objects[1];
        int appWidgetId = (int) objects[2];

        //setup Db and RemoteViews
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.next_launch_widget);
        LaunchesDatabase db = LaunchesDatabase.getInstance(context);

        //Get Next Launch from Db
        long currentTime = new Date().getTime()/1000L;
        Launch nextLaunch = db.pastLaunchesDao().getNextLaunch(currentTime);
        

        //Update Widget UI
        views.setTextViewText(R.id.appwidget_text, nextLaunch.getMission_name());
        appWidgetManager.updateAppWidget(appWidgetId, views);

        return null;
    }
}
