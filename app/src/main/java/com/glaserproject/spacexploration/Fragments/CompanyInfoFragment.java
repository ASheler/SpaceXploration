package com.glaserproject.spacexploration.Fragments;

import android.arch.lifecycle.ViewModelProvider;
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
import android.widget.TextView;

import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.AsyncTasks.FetchMilestonesAsyncTask;
import com.glaserproject.spacexploration.AsyncTasks.InsertMilestonesAsyncTask;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.NetUtils.CheckNetConnection;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.RvAdapters.MilestonesAdapter;
import com.glaserproject.spacexploration.ViewModels.MainViewModel;
import com.google.gson.Gson;

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

public class CompanyInfoFragment extends Fragment implements FetchMilestonesAsyncTask.AsyncTaskProgressListener {

    public CompanyInfoFragment() {
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    TextView textView;
    private MainViewModel viewModel;


    @BindView(R.id.company_info_rv)
    RecyclerView recyclerView;

    MilestonesAdapter infoAdapter;




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AndroidSupportInjection.inject(this);


    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmnet_company_info, container, false);

        ButterKnife.bind(this, rootView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        infoAdapter = new MilestonesAdapter();
        recyclerView.setAdapter(infoAdapter);



        if (CheckNetConnection.isNetworkAvailable(getContext())){
            fetchDataFromNet();
        } else {
            fetchDataFromDb();
        }





        return rootView;
    }

    private void fetchDataFromDb() {
        new FetchMilestonesAsyncTask(this).execute(getContext());
    }

    private void fetchDataFromNet() {

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(NetConstants.API_BASE_URL)
                .build();
        ApiClient webservice = retrofit.create(ApiClient.class);

        Call milestones = webservice.getAllMilestones();

        milestones.enqueue(new Callback<List<Milestone>>() {
            @Override
            public void onResponse(Call<List<Milestone>> call, Response<List<Milestone>> response) {

                Log.d("Milestones", "fetching from internet");

                //show milestones
                infoAdapter.setMilestones(response.body());
                //insert newly fetched data into roomDb
                new InsertMilestonesAsyncTask(response.body()).execute(getContext());

            }

            @Override
            public void onFailure(Call<List<Milestone>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onTaskComplete(List<Milestone> milestones) {
        infoAdapter.setMilestones(milestones);
    }
}
