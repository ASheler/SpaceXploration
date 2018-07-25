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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchDetailActivity extends AppCompatActivity {

    Launch launch;

    @BindView(R.id.launch_name)
    TextView launchTitle;
    @BindView(R.id.launch_date)
    TextView launchDate;
    @BindView(R.id.launch_site)
    TextView launchSite;
    @BindView(R.id.launch_rocket)
    TextView launchRocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ButterKnife.bind(this);

        Intent intent = getIntent();
        launch = intent.getParcelableExtra(ExtrasKeys.LAUNCH_DETAIL_EXTA_KEY);

        toolbar.setTitle(launch.getMission_name());


        launchTitle.setText(launch.getMission_name());
        Date date = new java.util.Date(launch.getLaunch_date_unix()*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = sdf.format(date);
        launchDate.setText(formattedDate);
        launchSite.setText(launch.getLaunch_site().getSite_name_long());
        launchRocket.setText(launch.getRocket().getRocket_name());
    }

}
