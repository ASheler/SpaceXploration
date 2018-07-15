package com.glaserproject.spacexploration.NetUtils;

import android.content.Context;
import android.net.ConnectivityManager;

public class CheckNetConnection {


    //Check if Network connection is available
    public static boolean isNetworkAvailable(Context context) {
        //set connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //check if ActiveNetwork isn't null && is Connected
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
