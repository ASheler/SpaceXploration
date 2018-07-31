package com.glaserproject.spacexploration.Fragments;

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
import com.glaserproject.spacexploration.AppConstants.NetConstants;
import com.glaserproject.spacexploration.AsyncTasks.CheckIfInfoInDb;
import com.glaserproject.spacexploration.AsyncTasks.InsertAboutIntoDbAsyncTask;
import com.glaserproject.spacexploration.AsyncTasks.InsertMilestonesAsyncTask;
import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.NetUtils.ApiClient;
import com.glaserproject.spacexploration.NetUtils.CheckNetConnection;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.RvAdapters.CompanyInfoAdapter;
import com.glaserproject.spacexploration.ViewModels.AboutSpacexViewModel;
import com.glaserproject.spacexploration.ViewModels.MilestonesViewModel;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyInfoFragment extends Fragment implements CheckIfInfoInDb.CheckInfoListener{

    public CompanyInfoFragment() {
    }


    private SaveCompanyInfoRvPositionListener positionListener;
    private Parcelable recyclerViewState;


    @BindView(R.id.company_info_rv)
    RecyclerView recyclerView;
    @BindView(R.id.no_data_message_tv)
    TextView noDataMessageTv;
    @BindView(R.id.company_info_progress_bar)
    ProgressBar companyInfoProgressBar;




    private CompanyInfoAdapter infoAdapter;


    private MilestonesViewModel milestonesViewModel;
    private AboutSpacexViewModel aboutSpacexViewModel;


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




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmnet_company_info, container, false);

        ButterKnife.bind(this, rootView);

        //Setup RV
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        infoAdapter = new CompanyInfoAdapter();
        recyclerView.setAdapter(infoAdapter);


        //init AboutSpacex ViewModel
        aboutSpacexViewModel = ViewModelProviders.of(this).get(AboutSpacexViewModel.class);
        aboutSpacexViewModel.getAboutSpaceX().observe(this, this::updateAbout);

        //init milestones ViewModel
        milestonesViewModel = ViewModelProviders.of(this).get(MilestonesViewModel.class);
        milestonesViewModel.getMilestones().observe(this, this::updateRv);






        Bundle bundle = getArguments();
        if (bundle != null){
            recyclerViewState = bundle.getParcelable(BundleKeys.INFO_RV_POSITION_KEY);
        }


        //fetch Data from Internet
        if (CheckNetConnection.isNetworkAvailable(Objects.requireNonNull(getContext()))){
            //Hide message saying there's no data
            noDataMessageTv.setVisibility(View.GONE);

            //reload Data from Net
            fetchDataFromNet();
        } else {
            //no connection - check if we have data stored
            checkIfDataInDb();
        }


        return rootView;
    }

    //check db if we have data
    private void checkIfDataInDb(){
        new CheckIfInfoInDb(this).execute(getContext());
    }
    @Override
    public void onDbChecked(Boolean dbIsFull) {
        if (!dbIsFull){
            //hide Loading Bar, show error message
            companyInfoProgressBar.setVisibility(View.GONE);
            noDataMessageTv.setVisibility(View.VISIBLE);
        }
    }




    private void fetchDataFromNet() {

        //init retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(NetConstants.API_BASE_URL)
                .build();
        ApiClient webservice = retrofit.create(ApiClient.class);

        //get Milestones
        Call<List<Milestone>> milestones = webservice.getAllMilestones();
        milestones.enqueue(new Callback<List<Milestone>>() {
            @Override
            public void onResponse(@NonNull Call<List<Milestone>> call, @NonNull Response<List<Milestone>> response) {
                //insert newly fetched data into roomDb
                new InsertMilestonesAsyncTask(response.body()).execute(getContext());

            }

            @Override
            public void onFailure(@NonNull Call<List<Milestone>> call, @NonNull Throwable t) {

            }
        });

        //call for AboutSpaceX data
        Call<AboutSpaceX> aboutSpacex = webservice.getAboutSpaceX();
        aboutSpacex.enqueue(new Callback<AboutSpaceX>() {

            @Override
            public void onResponse(@NonNull Call<AboutSpaceX> call, @NonNull Response<AboutSpaceX> response) {
                //insert into Db
                new InsertAboutIntoDbAsyncTask(response.body()).execute(getContext());
            }

            @Override
            public void onFailure(@NonNull Call<AboutSpaceX> call, @NonNull Throwable t) {

            }
        });
    }


    private void updateRv (List<Milestone> milestones){
        infoAdapter.setMilestones(milestones);
        if (recyclerViewState != null){
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
        companyInfoProgressBar.setVisibility(View.GONE);
    }

    private void updateAbout(AboutSpaceX aboutSpaceX){
        infoAdapter.setAboutSpaceX(aboutSpaceX);
        companyInfoProgressBar.setVisibility(View.GONE);
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
