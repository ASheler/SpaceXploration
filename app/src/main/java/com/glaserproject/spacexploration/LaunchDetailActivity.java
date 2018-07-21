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

import butterknife.ButterKnife;

public class LaunchDetailActivity extends AppCompatActivity {

    Launch launch;

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

        TextView textView = findViewById(R.id.textim);
        textView.setText(launch.getMission_name());

    }

}
