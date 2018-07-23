package com.glaserproject.spacexploration.RvAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchesAdapter extends RecyclerView.Adapter<LaunchesAdapter.LaunchesViewHolder> {


    private List<Launch> launches;
    private final onClickHandler mClickHandler;

    //Constructor for clickHandler
    public LaunchesAdapter (onClickHandler clickHandler){
        this.mClickHandler = clickHandler;
    }

    //set Launches method
    public void setLaunches(List<Launch> launches) {
        this.launches = launches;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public LaunchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.launch_tile, parent, false);
        return new LaunchesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchesViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (launches == null){
            return 0;
        }
        return launches.size();
    }


    public class LaunchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        @BindView(R.id.launch_title)
        TextView launchTitle;
        @BindView(R.id.launch_date)
        TextView launchDate;

        public LaunchesViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        void bind (int index){

            Launch currentLaunch = launches.get(index);
            launchTitle.setText(launches.get(index).getMission_name());

            //set readable date from millis
            Date date = new java.util.Date(launches.get(index).getLaunch_date_unix()*1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String formattedDate = sdf.format(date);

            long date1 = new Date().getTime() / 1000;

            if (currentLaunch.getLaunch_date_unix() < date1){
                launchDate.setText("flied on " + formattedDate);

            }else {
                launchDate.setText("will fly " + formattedDate);
            }
            //launchDate.setText(formattedDate);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(launches.get(getAdapterPosition()));
        }
    }


    public interface onClickHandler {
        void onClick(Launch launch);
    }

}
