package com.glaserproject.spacexploration.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaunchesMainFragment extends Fragment implements InsertPastLaunchesAsyncTask.AsyncTaskListeners{

    //Default empty constructor
    public LaunchesMainFragment (){
    }


    LaunchesAdapter launchesAdapter;
    MainViewModel mainViewModel;
    AppDatabase mDb;




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


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mDb = AppDatabase.getInstance(getContext());


        retrieveLaunches();
        if (CheckNetConnection.isNetworkAvailable(getContext())){
            initRetrofit();
        } else {
            if (launchesAdapter.getItemCount() == 0){
                Toast.makeText(getContext(), "No connection and no offline data", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No connection, loading offline data", Toast.LENGTH_SHORT).show();
            }
            //TODO: Check if roomDb is empty
        }

        return rootView;
    }

    //Initialize and call Retrofit get Past Launches
    private void initRetrofit() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(NetConstants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient apiClient = retrofit.create(ApiClient.class);
        Call<List<Launch>> pastLaunchesCall = apiClient.getLaunches();

        pastLaunchesCall.enqueue(new Callback<List<Launch>>() {

            @Override
            public void onResponse(Call<List<Launch>> call, final Response<List<Launch>> response) {
                AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //insert launches into Db
                        new InsertPastLaunchesAsyncTask(LaunchesMainFragment.this).execute(getContext(), response.body());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Launch>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //get Launches from Db
    private void retrieveLaunches (){

        mainViewModel.getLaunches().removeObservers(this);
        mainViewModel.getLaunches().observe(this, new Observer<List<Launch>>() {
            @Override
            public void onChanged(@Nullable List<Launch> launches) {
                launchesAdapter.setLaunches(launches);
            }
        });
    }


    @Override
    public void onTaskBegin() {
        //if data is shown, there is no need to show loading bar as data is only updating, not downloading as whole
        if (launchesAdapter.getItemCount() != 0) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTaskComplete() {
        progressBar.setVisibility(View.GONE);
    }
}
