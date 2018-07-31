package com.glaserproject.spacexploration.RvAdapters;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.Utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecycleView adapter for Launches
 */

public class LaunchesAdapter extends RecyclerView.Adapter<LaunchesAdapter.LaunchesViewHolder> {


    private List<Launch> launches;
    private final onClickHandler mClickHandler;

    //Constructor for clickHandler
    public LaunchesAdapter(onClickHandler clickHandler) {
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

        Launch currentLaunch = launches.get(position);

        //cancel timer if it exists
        if (holder.timer != null) {
            holder.timer.cancel();
        }

        //if launch time > current time, run timer
        if (new Date().getTime() < currentLaunch.getLaunch_date_unix() * 1000L) {

            //set Alpha for flight to be flied
            holder.launchBaseCard.setAlpha(0.6f);

            //get time to flight
            long timeTo = (currentLaunch.getLaunch_date_unix() * 1000L) - new Date().getTime();

            //start timer
            holder.timer = new CountDownTimer(timeTo, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //update view on every tick
                    String time = DateUtils.formateMillisTo(millisUntilFinished);
                    holder.launchDate.setText(time);
                }

                @Override
                public void onFinish() {
                    holder.launchDate.setText(R.string.launches_tile_t_zero);
                }
            }.start();
        }


        holder.bind(position);

    }


    @Override
    public int getItemCount() {
        if (launches == null) {
            return 0;
        }
        return launches.size();
    }


    public class LaunchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.launch_title)
        TextView launchTitle;
        @BindView(R.id.launch_date)
        TextView launchDate;
        @BindView(R.id.launch_detail_tv)
        TextView launchDetailTv;

        @BindView(R.id.launch_patch_iv)
        ImageView launchPatchIv;

        @BindView(R.id.launch_card_base)
        CardView launchBaseCard;

        CountDownTimer timer;

        public LaunchesViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        void bind(int index) {

            Launch currentLaunch = launches.get(index);

            //load Patch icon or set app logo as placeholder for loading or for no patch
            Picasso.get()
                    .load(currentLaunch.getLinks().getMission_patch_small())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(launchPatchIv);

            //set Mission name and details
            launchTitle.setText(currentLaunch.getMission_name());
            if (currentLaunch.getDetails() == null) {
                launchDetailTv.setVisibility(View.INVISIBLE);
            } else {
                launchDetailTv.setText(currentLaunch.getDetails());
            }


            long currentTime = new Date().getTime() / 1000;

            //if flown already - set date with string
            if (currentLaunch.getLaunch_date_unix() < currentTime) {

                //set readable date from millis
                String formattedDate = DateUtils.formateDate(currentLaunch.getLaunch_date_unix());

                //getting context from launchDate textview
                String launchDateFlown = launchDate.getContext().getString(R.string.launch_tile_flied_on_text) + formattedDate;
                launchDate.setText(launchDateFlown);
            }


        }

        //register click and send it to Fragment
        @Override
        public void onClick(View v) {
            mClickHandler.onClick(launches.get(getAdapterPosition()));
        }
    }


    //interface for the click
    public interface onClickHandler {
        void onClick(Launch launch);
    }

}
