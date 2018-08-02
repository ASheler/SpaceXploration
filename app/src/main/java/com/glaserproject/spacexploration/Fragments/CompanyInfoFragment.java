package com.glaserproject.spacexploration.Fragments;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.glaserproject.spacexploration.AppConstants.BundleKeys;
import com.glaserproject.spacexploration.AsyncTasks.CheckIfInfoInDb;
import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.NetUtils.CheckNetConnection;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.RvAdapters.CompanyInfoAdapter;
import com.glaserproject.spacexploration.ViewModels.AboutSpacexViewModel;
import com.glaserproject.spacexploration.ViewModels.MilestonesViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * Fragment for Company Info display
 */

public class CompanyInfoFragment extends Fragment implements CheckIfInfoInDb.CheckInfoListener {

    public CompanyInfoFragment() {
    }


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SaveCompanyInfoRvPositionListener positionListener;
    private Parcelable recyclerViewState;

    private MilestonesViewModel milestonesViewModel;
    private AboutSpacexViewModel aboutSpacexViewModel;

    private CompanyInfoAdapter infoAdapter;

    @BindView(R.id.company_info_rv)
    RecyclerView recyclerView;
    @BindView(R.id.no_data_message_tv)
    TextView noDataMessageTv;
    @BindView(R.id.company_info_progress_bar)
    ProgressBar companyInfoProgressBar;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Attach Listener for saving rv position on configuration change
        try {
            positionListener = (SaveCompanyInfoRvPositionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement rvPositionListener");
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AndroidSupportInjection.inject(this);

        aboutSpacexViewModel = ViewModelProviders.of(this, viewModelFactory).get(AboutSpacexViewModel.class);
        aboutSpacexViewModel.init();
        aboutSpacexViewModel.getAboutSpaceX().observe(getViewLifecycleOwner(), this::updateAbout);

        milestonesViewModel = ViewModelProviders.of(this, viewModelFactory).get(MilestonesViewModel.class);
        milestonesViewModel.init();
        milestonesViewModel.getMilestones().observe(getViewLifecycleOwner(), this::updateRv);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmnet_company_info, container, false);

        //Bind views
        ButterKnife.bind(this, rootView);

        //Setup RV
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        infoAdapter = new CompanyInfoAdapter();
        recyclerView.setAdapter(infoAdapter);

        //get rv state from Bundle from MainActivity
        Bundle bundle = getArguments();
        if (bundle != null) {
            recyclerViewState = bundle.getParcelable(BundleKeys.INFO_RV_POSITION_KEY);
        }


        //fetch Data from Internet
        if (CheckNetConnection.isNetworkAvailable(Objects.requireNonNull(getContext()))) {
            //Hide message saying there's no data
            noDataMessageTv.setVisibility(View.GONE);

        } else {
            //no connection - check if we have data stored
            checkIfDataInDb();
        }


        return rootView;
    }

    //check db if we have data
    private void checkIfDataInDb() {
        new CheckIfInfoInDb(this).execute(getContext());
    }

    @Override
    public void onDbChecked(Boolean dbIsFull) {
        if (!dbIsFull) {
            //hide Loading Bar, show error message
            companyInfoProgressBar.setVisibility(View.GONE);
            noDataMessageTv.setVisibility(View.VISIBLE);
        }
    }

    //update Rv with milestones
    private void updateRv(List<Milestone> milestones) {
        infoAdapter.setMilestones(milestones);
        if (recyclerViewState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
        //hide progressBar
        companyInfoProgressBar.setVisibility(View.GONE);
    }

    //updateRv with AboutSpaceX data
    private void updateAbout(AboutSpaceX aboutSpaceX) {
        infoAdapter.setAboutSpaceX(aboutSpaceX);
        //hide proressbar
        companyInfoProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //save rv position
        recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        positionListener.saveInfoRvPosition(recyclerViewState);
    }


    //interface for rv position save
    public interface SaveCompanyInfoRvPositionListener {
        void saveInfoRvPosition(Parcelable position);
    }

}
