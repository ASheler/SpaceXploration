package com.glaserproject.spacexploration.AppWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.glaserproject.spacexploration.AppConstants.ExtrasKeys;
import com.glaserproject.spacexploration.LaunchDetailActivity;
import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        views.setTextViewText(R.id.launch_title_widget_tv, nextLaunch.getMission_name());

        String formattedTime = formatLaunchTime(nextLaunch.getLaunch_date_unix());
        views.setTextViewText(R.id.launch_time_widget_tv, formattedTime);

        Intent intent = new Intent(context, LaunchDetailActivity.class);
        intent.putExtra(ExtrasKeys.LAUNCH_DETAIL_EXTA_KEY, nextLaunch);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.wrapper_widget_rl, pendingIntent);


        appWidgetManager.updateAppWidget(appWidgetId, views);

        return null;
    }

    private String formatLaunchTime(long launchDateUnix) {

        //set readable date from millis
        Date date = new java.util.Date(launchDateUnix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MMM-dd hh:ss", Locale.US);

        return sdf.format(date);
    }
}
