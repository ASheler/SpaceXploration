package com.glaserproject.spacexploration;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SupportUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_us);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.support_us_activity_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void share(View view) {
        //share string with link for the app
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, "Look what beautiful app I am using! \nhttps://play.google.com/store/apps/developer?id=GlaserProject");
        startActivity(Intent.createChooser(i, "Share This App"));
    }

    public void patreon(View view) {

        //open Patreon in ChromeCustom Tab
        String url = "https://patreon.com";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

    }
}
