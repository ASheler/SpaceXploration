package com.glaserproject.spacexploration.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.NetUtils.AppExecutors;
import com.glaserproject.spacexploration.NetUtils.CheckNetConnection;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.Room.AppDatabase;
import com.glaserproject.spacexploration.Room.InsertPastLaunchesAsyncTask;
import com.glaserproject.spacexploration.RvAdapters.LaunchesAdapter;
import com.glaserproject.spacexploration.ViewModels.MainViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaunchesMainFragment extends Fragment implements InsertPastLaunchesAsyncTask.AsyncTaskListeners{

    //Default empty constructor
    public LaunchesMainFragment (){
    }


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    LaunchesAdapter launchesAdapter;
    private MainViewModel mainViewModel;




    @BindView(R.id.launches_rv)
    RecyclerView launchesRV;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_launches_main, container, false);

        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        launchesRV.setLayoutManager(layoutManager);
        launchesAdapter = new LaunchesAdapter();
        launchesRV.setAdapter(launchesAdapter);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AndroidSupportInjection.inject(this);

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        mainViewModel.init();
        mainViewModel.getLaunches().observe(this, launches -> updateUi(launches));
    }

    private void updateUi(List<Launch> launches){
        launchesAdapter.setLaunches(launches);
    }


    @Override
    public void onTaskBegin() {
        //if data is shown, there is no need to show loading bar as data is only updating, not downloading as whole
        if (launchesAdapter.getItemCount() != 0) {
            progressBar.setVisibility(View.VISIBLE);
            Log.d("Ondra", "OnTaskBegin");
        }
    }

    @Override
    public void onTaskComplete() {
        progressBar.setVisibility(View.GONE);
    }
}
