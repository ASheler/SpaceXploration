package com.glaserproject.spacexploration.Fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.glaserproject.spacexploration.AppConstants.ExtrasKeys;
import com.glaserproject.spacexploration.LaunchDetailActivity;
import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.RvAdapters.LaunchesAdapter;
import com.glaserproject.spacexploration.ViewModels.MainViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

public class LaunchesMainFragment extends Fragment implements LaunchesAdapter.onClickHandler{

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


        //Setup RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        launchesRV.setLayoutManager(layoutManager);
        //init Adapter with ClickListener
        launchesAdapter = new LaunchesAdapter(this);

        //setDivider for items
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(launchesRV.getContext(),
                layoutManager.getOrientation());
        launchesRV.addItemDecoration(dividerItemDecoration);

        launchesRV.setAdapter(launchesAdapter);


        //hide loading bar if we have data
        if (launchesAdapter.getItemCount() != 0){
            progressBar.setVisibility(View.GONE);
        }

        return rootView;
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

        //hide progressBar if we have data
        if (launchesAdapter.getItemCount() != 0){
            progressBar.setVisibility(View.GONE);
        }
    }


    //OnClick handler for RV
    @Override
    public void onClick(Launch launch) {
        Intent intent = new Intent(getActivity(), LaunchDetailActivity.class);
        intent.putExtra(ExtrasKeys.LAUNCH_DETAIL_EXTA_KEY, launch);
        startActivity(intent);
    }



}
