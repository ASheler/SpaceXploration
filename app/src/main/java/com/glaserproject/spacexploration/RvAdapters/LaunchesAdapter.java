package com.glaserproject.spacexploration.RvAdapters;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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

        Launch currentLaunch = launches.get(position);

        if (holder.timer != null){
            holder.timer.cancel();
        }

        if (new Date().getTime() < currentLaunch.getLaunch_date_unix()*1000L){

            //set gray background
            holder.launchBaseCard.setCardBackgroundColor(ContextCompat.getColor(holder.launchBaseCard.getContext(), R.color.card_gray_background));
            //set lower elevation
            holder.launchBaseCard.setCardElevation(2.3f);

            long timeTo = (currentLaunch.getLaunch_date_unix()*1000L) - new Date().getTime();

            holder.timer = new CountDownTimer(timeTo, 100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String time = convertMillisToReadable(millisUntilFinished);
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

    private String convertMillisToReadable(long millisUntilFinished) {

        //create Strings from millis
        String daysToLaunch = String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
        String hoursToLaunch = String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)));
        String minutesToLaunch = String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)));
        String secsToLaunch = String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

        //String Builder to put that into one string
        StringBuilder sb = new StringBuilder();

        sb.append("T - ");

        //don't display days if there is none
        if (!daysToLaunch.equals("00")){
            sb.append(daysToLaunch);
            sb.append("d ");
        }
        sb.append(hoursToLaunch);
        sb.append(":");
        sb.append(minutesToLaunch);
        sb.append(":");
        sb.append(secsToLaunch);

        return sb.toString();
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


        void bind (int index){

            Launch currentLaunch = launches.get(index);

            //load Patch icon or set app logo as placeholder for loading or for no patch
            Picasso.get()
                    //.load(currentLaunch.getLinks().getMission_patch_small())
                    .load(R.mipmap.ic_launcher)
                    //TODO: change placeholder to actual link
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(launchPatchIv);

            launchTitle.setText(currentLaunch.getMission_name());
            if (currentLaunch.getDetails() == null) {
                launchDetailTv.setVisibility(View.INVISIBLE);
            } else {
                launchDetailTv.setText(currentLaunch.getDetails());
            }
            launchDetailTv.setText(currentLaunch.getDetails());


            long currentTime = new Date().getTime() / 1000;

            //if flown already - set date with string
            if (currentLaunch.getLaunch_date_unix() < currentTime) {

                //set readable date from millis
                Date date = new java.util.Date(launches.get(index).getLaunch_date_unix()*1000L);
                SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String formattedDate = sdf.format(date);

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
