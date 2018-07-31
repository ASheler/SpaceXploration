package com.glaserproject.spacexploration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Activity showing information about the app
 */

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.about_activity_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    //email me method
    public void mailMe(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode("ondraglaser@gmail.com") +
                "?subject=" + Uri.encode("SpaceXploration");
        Uri uri = Uri.parse(uriText);

        intent.setData(uri);
        startActivity(Intent.createChooser(intent, "Send mail..."));
    }
}
