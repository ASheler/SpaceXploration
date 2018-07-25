package com.glaserproject.spacexploration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.glaserproject.spacexploration.AppConstants.BundleKeys;
import com.glaserproject.spacexploration.Fragments.CompanyInfoFragment;
import com.glaserproject.spacexploration.Fragments.LaunchesMainFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HasSupportFragmentInjector,
        LaunchesMainFragment.SaveRvPositionListener,
        CompanyInfoFragment.SaveCompanyInfoRvPositionListener {


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private Parcelable saveLaunchesRvPosition;
    private Parcelable saveInfoRvPosition;
    private int fragmentId;


    //Firebase Analytics
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Dependency injection
        AndroidInjection.inject(this);

        //initialize analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //retrieve data from savedInstanceState
        if (savedInstanceState != null){
            //rv position for fragments
            saveLaunchesRvPosition = savedInstanceState.getParcelable(BundleKeys.LAUNCHES_RV_POSITION_KEY);
            saveInfoRvPosition = savedInstanceState.getParcelable(BundleKeys.INFO_RV_POSITION_KEY);
            //selected fragment
            fragmentId = savedInstanceState.getInt(BundleKeys.FRAGMENT_ID_KEY);
        }


        //setup nav drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //setup fragment from savedState or default(0)
        MenuItem item =  navigationView.getMenu().getItem(fragmentId);
        onNavigationItemSelected(item);
        //set menu item as checked for UI consistency
        item.setChecked(true);


    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //setup Bundle for Analytics
        Bundle analyticsBundle = new Bundle();
        //put data into analytics bundle
        analyticsBundle.putInt(FirebaseAnalytics.Param.ITEM_ID, fragmentId);
        analyticsBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, item.toString());
        analyticsBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "menu_selection");
        //send analytics
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, analyticsBundle);



        // Handle navigation view item clicks here.
        int id = item.getItemId();


        //setup Fragment and its Bundle
        Fragment fragment = null;
        Bundle fragmentExtraBundle = new Bundle();

        Intent activityIntent = null;

        switch (id){
            case R.id.nav_launches:
                fragment = new LaunchesMainFragment();

                fragmentId = 0;
                //put current rv position to bundle and send it to fragment
                fragmentExtraBundle.putParcelable(BundleKeys.LAUNCHES_RV_POSITION_KEY, saveLaunchesRvPosition);

                break;

            case R.id.nav_about_spacex:
                fragment = new CompanyInfoFragment();

                fragmentId = 1;
                fragmentExtraBundle.putParcelable(BundleKeys.INFO_RV_POSITION_KEY, saveInfoRvPosition);

                break;
            case R.id.nav_support:
                activityIntent = new Intent(this, SupportUsActivity.class);
                break;
            case R.id.nav_about:
                activityIntent = new Intent(this, AboutActivity.class);
                break;
        }


        //check if we are running new activity
        if (activityIntent != null){
            startActivity(activityIntent);
            return true;


            //if not, run fragment
        } else if (fragment != null){

            //send data into Fragment
            fragment.setArguments(fragmentExtraBundle);

            //launch Fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main_layout, fragment)
                    .commit();
        }


        //close drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }







    //saving rv position from Launches Fragment
    @Override
    public void saveLaunchesRvPosition(Parcelable position) {
        saveLaunchesRvPosition = position;
    }

    @Override
    public void saveInfoRvPosition(Parcelable position) {
        saveInfoRvPosition = position;
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save RV positions
        outState.putParcelable(BundleKeys.LAUNCHES_RV_POSITION_KEY, saveLaunchesRvPosition);
        outState.putParcelable(BundleKeys.INFO_RV_POSITION_KEY, saveInfoRvPosition);
        //save Fragment id
        outState.putInt(BundleKeys.FRAGMENT_ID_KEY, fragmentId);

    }


}
