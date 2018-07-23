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
        HasSupportFragmentInjector, LaunchesMainFragment.SaveRvPositionListener {


    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    Parcelable saveRvPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AndroidInjection.inject(this);

        if (savedInstanceState != null){
            saveRvPosition = savedInstanceState.getParcelable(BundleKeys.SAVE_RV_POSITION_KEY);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);







        //set default fragment view
        MenuItem item =  navigationView.getMenu().getItem(0);
        onNavigationItemSelected(item);
        //set menu item as checked for UI consistency
        item.setChecked(true);


    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }


    //get Launches from Db
    private void retrieveLaunches (){
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getLaunches().observe(this, new Observer<List<Launch>>() {
            @Override
            public void onChanged(@Nullable List<Launch> launches) {
                //data changed
            }
        });
    }


    //Check if Network connection is available
    public boolean isNetworkAvailable(Context context) {
        //set connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //check if ActiveNetwork isn't null && is Connected
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
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

        switch (id){
            case R.id.nav_launches:
                fragment = new LaunchesMainFragment();

                //put current rv position to bundle and send it to fragment
                Bundle bundle = new Bundle();
                bundle.putParcelable(BundleKeys.SAVE_RV_POSITION_KEY, saveRvPosition);
                fragment.setArguments(bundle);
                break;

            case R.id.nav_spacex:
                fragment = new CompanyInfoFragment();
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

        //launch Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.content_main_layout, fragment)
                .commit();

        //close drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //saving rv position from Launches Fragment
    @Override
    public void save(Parcelable position) {
        saveRvPosition = position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save RV position
        outState.putParcelable(BundleKeys.SAVE_RV_POSITION_KEY, saveRvPosition);
    }
}
