package com.glaserproject.spacexploration;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.ViewModels.MainViewModel;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AndroidInjection.inject(this);

        if (savedInstanceState != null){
            saveLaunchesRvPosition = savedInstanceState.getParcelable(BundleKeys.LAUNCHES_RV_POSITION_KEY);
            saveInfoRvPosition = savedInstanceState.getParcelable(BundleKeys.INFO_RV_POSITION_KEY);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        //set or restore fragment view;
        if (savedInstanceState != null){
            fragmentId = savedInstanceState.getInt(BundleKeys.FRAGMENT_ID_KEY);
        }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        Bundle bundle = new Bundle();

        switch (id){
            case R.id.nav_launches:
                fragment = new LaunchesMainFragment();

                fragmentId = 0;
                bundle.putParcelable(BundleKeys.LAUNCHES_RV_POSITION_KEY, saveLaunchesRvPosition);
                //put current rv position to bundle and send it to fragment

                break;

            case R.id.nav_spacex:
                fragment = new CompanyInfoFragment();

                fragmentId = 1;
                bundle.putParcelable(BundleKeys.INFO_RV_POSITION_KEY, saveInfoRvPosition);

                break;
            case R.id.nav_launch_pads:
                break;
            case R.id.nav_rockets:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_support:
                break;
            case R.id.nav_about:
                break;
        }

        fragment.setArguments(bundle);

        //launch Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main_layout, fragment)
                .commit();

        //close drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
