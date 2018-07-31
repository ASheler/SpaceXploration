package com.glaserproject.spacexploration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.glaserproject.spacexploration.AppConstants.AnalyticsKeys;
import com.glaserproject.spacexploration.AppConstants.ExtrasKeys;
import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.RvAdapters.DetailLinksAdapter;
import com.glaserproject.spacexploration.Utils.DateUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activity showing Launch Detail.
 * Activity needs to receive Launch as an object in incoming intent
 */

public class LaunchDetailActivity extends AppCompatActivity implements DetailLinksAdapter.onClickHandler {

    private Launch launch;

    //Firebase Analytics
    private FirebaseAnalytics mFirebaseAnalytics;

    DetailLinksAdapter linksAdapter;


    @BindView(R.id.launch_name)
    TextView launchTitle;
    @BindView(R.id.launch_date)
    TextView launchDate;
    @BindView(R.id.launch_site)
    TextView launchSite;
    @BindView(R.id.launch_rocket)
    TextView launchRocket;
    @BindView(R.id.launch_detail)
    TextView launchDetail;
    @BindView(R.id.detail_links_rv)
    RecyclerView linksRv;

    @BindView(R.id.adView)
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);

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

        //send Ad load into delay for better performance and activity loading time
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            // used Sample Admob App Id
            MobileAds.initialize(this, getString(R.string.admob_app_id));
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            //setup AdListener for analytics
            setAdListener();
        }, 5000);


    }

    private void setUpUI() {
        //get formatted launch date
        String formattedDate = DateUtils.formateDate(launch.getLaunch_date_unix());

        launchTitle.setText(launch.getMission_name());
        launchDate.setText(formattedDate);
        launchSite.setText(launch.getLaunch_site().getSite_name_long());
        launchRocket.setText(launch.getRocket().getRocket_name());
        launchDetail.setText(launch.getDetails());

        //setup Rv for links
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linksRv.setLayoutManager(layoutManager);
        linksAdapter = new DetailLinksAdapter(launch.getLinks(), this);
        linksRv.setAdapter(linksAdapter);


    }


    public void setAdListener() {
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                super.onAdOpened();
                //send analytics on ad click
                Bundle analyticsBundle = new Bundle();
                //send analytics
                analyticsBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, AnalyticsKeys.AD_BANNER_KEY);
                analyticsBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, AnalyticsKeys.AD_CLICKED_KEY);
                //send analytics
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, analyticsBundle);
            }
        });
    }

    @Override
    public void onClick(String url) {
        //send user to chrome custom tab on click on link
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        //setup brand color for toolbar
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
