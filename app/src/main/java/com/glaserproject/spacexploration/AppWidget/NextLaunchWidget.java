package com.glaserproject.spacexploration.AppWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.glaserproject.spacexploration.R;

/**
 * Implementation of App Widget for next Launch
 */
public class NextLaunchWidget extends AppWidgetProvider {

    //updating widget
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.next_launch_widget);

        //fetch next launch from db
        new FetchLaunchAsyncTask().execute(context, appWidgetManager, appWidgetId);

        //Update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

