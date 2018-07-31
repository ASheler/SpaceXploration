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
import com.glaserproject.spacexploration.MainActivity;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.Room.LaunchesDatabase;
import com.glaserproject.spacexploration.Utils.DateUtils;

import java.util.Date;

/**
 * FetchLaunchAsyncTask is extracted AsyncTask for AppWidget
 * It fetches upcoming launch from db and sets its parameters into remoteViews
 */

class FetchLaunchAsyncTask extends AsyncTask<Object, Void, Launch> {

    /**
     * @param objects must contain:
     *                object[0] == Context;
     *                object[1] == AppWidgetManager
     *                object[2] == int appWidgetId
     */

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

        //init Intent
        Intent intent;

        //check if we got any launch
        if (nextLaunch == null) {
            //show error message if no launch found
            views.setTextViewText(R.id.launch_title_widget_tv, context.getString(R.string.widget_no_data_title));
            views.setTextViewText(R.id.launch_time_widget_tv, context.getString(R.string.widget_no_data_detail));

            //init intent to send user to MainActivity
            intent = new Intent(context, MainActivity.class);

        } else {

            //Update Widget UI
            views.setTextViewText(R.id.launch_title_widget_tv, nextLaunch.getMission_name());

            //format the date
            String formattedTime = DateUtils.formateDate(nextLaunch.getLaunch_date_unix());
            views.setTextViewText(R.id.launch_time_widget_tv, formattedTime);

            //set onClick pending intent
            intent = new Intent(context, LaunchDetailActivity.class);
            intent.putExtra(ExtrasKeys.LAUNCH_DETAIL_EXTA_KEY, nextLaunch);

        }

        //init PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //set onClink PendingIntent into views
        views.setOnClickPendingIntent(R.id.wrapper_widget_rl, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);

        return null;
    }

}
