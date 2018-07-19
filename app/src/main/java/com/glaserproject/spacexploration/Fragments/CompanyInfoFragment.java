package com.glaserproject.spacexploration.Fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.ViewModels.MainViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class CompanyInfoFragment extends Fragment {

    public CompanyInfoFragment() {
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    TextView textView;
    private MainViewModel viewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AndroidSupportInjection.inject(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.init();
        viewModel.getLaunches().observe(this, launches -> updateUI(launches));
    }


    private void updateUI(List<Launch> launches){
        textView.setText(launches.get(1).getMission_name());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmnet_company_info, container, false);

        textView = rootView.findViewById(R.id.textView3);
        textView.setText("hovno");

        return rootView;
    }
}
