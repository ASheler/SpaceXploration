package com.glaserproject.spacexploration.Fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.glaserproject.spacexploration.AppConstants.BundleKeys;
import com.glaserproject.spacexploration.AppConstants.ExtrasKeys;
import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.AsyncTasks.CheckIfLaunchesInDb;
import com.glaserproject.spacexploration.LaunchDetailActivity;
import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.NetUtils.CheckNetConnection;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.RvAdapters.LaunchesAdapter;
import com.glaserproject.spacexploration.ViewModels.MainViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LaunchesMainFragment extends Fragment implements
        LaunchesAdapter.onClickHandler,
        CheckIfLaunchesInDb.CheckInfoListener {

    //Default empty constructor
    public LaunchesMainFragment (){
    }


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    //Rv Position
    SaveRvPositionListener saveRvPosition;
    private Parcelable recyclerViewState;


    LaunchesAdapter launchesAdapter;
    private MainViewModel mainViewModel;



    @BindView(R.id.launches_rv)
    RecyclerView launchesRV;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.no_data_message_tv)
    TextView noDataMessageTv;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_launches_main, container, false);

        ButterKnife.bind(this, rootView);


        //Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        launchesRV.setLayoutManager(layoutManager);
        //init Adapter with ClickListener
        launchesAdapter = new LaunchesAdapter(this);


        launchesRV.setAdapter(launchesAdapter);

        //get rv position from bundle
        Bundle bundle = getArguments();
        if (bundle != null){
            recyclerViewState = bundle.getParcelable(BundleKeys.LAUNCHES_RV_POSITION_KEY);
        }

        //check connection
        if (!CheckNetConnection.isNetworkAvailable(Objects.requireNonNull(getContext()))){
            //no connection - check if we have data stored
            checkIfDataInDb();
        }


        return rootView;
    }

    private void checkIfDataInDb() {
        new CheckIfLaunchesInDb(this).execute(getContext());
    }
    @Override
    public void onDbChecked(Boolean dbIsFull) {
        if (!dbIsFull){
            //hide Loading Bar, show error message
            progressBar.setVisibility(View.GONE);
            noDataMessageTv.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AndroidSupportInjection.inject(this);



        //setUp View Model
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        mainViewModel.init();
        mainViewModel.getLaunches().observe(this, this::updateUi);


    }



    private void updateUi(List<Launch> launches){
        //send data to RvAdapter
        launchesAdapter.setLaunches(launches);

        //set rv position
        if (recyclerViewState != null){
            launchesRV.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }

        //hide progressBar if we have data
        if (launchesAdapter.getItemCount() != 0){
            progressBar.setVisibility(View.GONE);
            noDataMessageTv.setVisibility(View.GONE);
        }


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Attach Listener for saving rv position on configuration change
        try {
            saveRvPosition = (SaveRvPositionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    //OnClick handler for RV
    @Override
    public void onClick(Launch launch) {
        Intent intent = new Intent(getActivity(), LaunchDetailActivity.class);
        intent.putExtra(ExtrasKeys.LAUNCH_DETAIL_EXTA_KEY, launch);
        startActivity(intent);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //get current rv state
        recyclerViewState = launchesRV.getLayoutManager().onSaveInstanceState();
        //send the state to Main Activity
        saveRvPosition.saveLaunchesRvPosition(recyclerViewState);
    }


    //sending rv position to activity
    public interface SaveRvPositionListener{
        void saveLaunchesRvPosition(Parcelable position);
    }


}
