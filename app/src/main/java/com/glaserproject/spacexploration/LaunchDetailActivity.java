package com.glaserproject.spacexploration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.glaserproject.spacexploration.AppConstants.ExtrasKeys;
import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchDetailActivity extends AppCompatActivity {

    private Launch launch;

    //Firebase Analytics
    private FirebaseAnalytics mFirebaseAnalytics;

    @BindView(R.id.launch_name)
    TextView launchTitle;
    @BindView(R.id.launch_date)
    TextView launchDate;
    @BindView(R.id.launch_site)
    TextView launchSite;
    @BindView(R.id.launch_rocket)
    TextView launchRocket;

    @BindView(R.id.adView)
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //get incoming intent
        Intent intent = getIntent();
        launch = intent.getParcelableExtra(ExtrasKeys.LAUNCH_DETAIL_EXTA_KEY);

        //set Toolbar Title
        toolbar.setTitle(launch.getMission_name());
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //bind
        ButterKnife.bind(this);


        //initialize analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);



        setUpUI();



        // used Sample Admob App Id
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        setAdListener();
    }

    private void setUpUI() {
        launchTitle.setText(launch.getMission_name());
        Date date = new Date(launch.getLaunch_date_unix()*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = sdf.format(date);
        launchDate.setText(formattedDate);
        launchSite.setText(launch.getLaunch_site().getSite_name_long());
        launchRocket.setText(launch.getRocket().getRocket_name());
    }


    public void setAdListener (){
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdOpened() {
                super.onAdOpened();
                //send analytics on ad click
                Bundle analyticsBundle = new Bundle();
                //send analytics
                analyticsBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "ad_banner");
                analyticsBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "ad_clicked");
                //send analytics
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, analyticsBundle);
            }
        });
    }

}
