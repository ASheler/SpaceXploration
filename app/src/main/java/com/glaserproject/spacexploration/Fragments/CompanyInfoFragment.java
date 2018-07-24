package com.glaserproject.spacexploration.Fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glaserproject.spacexploration.AppConstants.BundleKeys;
import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.AsyncTasks.FetchAboutSpaceXDataAsyncTask;
import com.glaserproject.spacexploration.AsyncTasks.InsertAboutIntoDbAsyncTask;
import com.glaserproject.spacexploration.AsyncTasks.InsertMilestonesAsyncTask;
import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
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

public class CompanyInfoFragment extends Fragment implements FetchAboutSpaceXDataAsyncTask.AsyncTaskProgressListener {

    public CompanyInfoFragment() {
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SaveCompanyInfoRvPositionListener positionListener;
    private Parcelable recyclerViewState;


    @BindView(R.id.company_info_rv)
    RecyclerView recyclerView;

    MilestonesAdapter infoAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Attach Listener for saving rv position on configuration change
        try {
            positionListener = (SaveCompanyInfoRvPositionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

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

        Bundle bundle = getArguments();
        if (bundle != null){
            recyclerViewState = bundle.getParcelable(BundleKeys.INFO_RV_POSITION_KEY);
        }

        fetchDataFromDb();

        if (CheckNetConnection.isNetworkAvailable(getContext())){
            fetchDataFromNet();
        }


        return rootView;
    }

    private void fetchDataFromDb() {
        new FetchAboutSpaceXDataAsyncTask(this).execute(getContext());
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
                updateRv(response.body());
                //insert newly fetched data into roomDb
                new InsertMilestonesAsyncTask(response.body()).execute(getContext());

            }

            @Override
            public void onFailure(Call<List<Milestone>> call, Throwable t) {

            }
        });

        Call aboutSpacex = webservice.getAboutSpaceX();
        aboutSpacex.enqueue(new Callback<AboutSpaceX>() {

            @Override
            public void onResponse(Call<AboutSpaceX> call, Response<AboutSpaceX> response) {
                updateAbout(response.body());
                new InsertAboutIntoDbAsyncTask(response.body()).execute(getContext());
            }

            @Override
            public void onFailure(Call<AboutSpaceX> call, Throwable t) {

            }
        });
    }


    private void updateRv (List<Milestone> milestones){
        infoAdapter.setMilestones(milestones);
        if (recyclerViewState != null){
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }

    private void updateAbout(AboutSpaceX aboutSpaceX){

    }

    @Override
    public void onTaskComplete(Pair<List<Milestone>, AboutSpaceX> aboutPair) {
        updateUI(aboutPair);
    }



    private void updateUI (Pair<List<Milestone>, AboutSpaceX> aboutPair){
        infoAdapter.setMilestones(aboutPair.first);
        if (recyclerViewState != null){
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        positionListener.saveInfoRvPosition(recyclerViewState);
    }

    public interface SaveCompanyInfoRvPositionListener{
        void saveInfoRvPosition(Parcelable position);
    }

}
