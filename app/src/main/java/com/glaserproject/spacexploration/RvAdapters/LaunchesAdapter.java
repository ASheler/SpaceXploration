package com.glaserproject.spacexploration.RvAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glaserproject.spacexploration.LaunchObjects.Launch;
import com.glaserproject.spacexploration.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchesAdapter extends RecyclerView.Adapter<LaunchesAdapter.LaunchesViewHolder> {


    private List<Launch> launches;

    public LaunchesAdapter (){
    }

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


    public class LaunchesViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.launch_title)
        TextView launchTitle;

        public LaunchesViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }


        void bind (int index){
            launchTitle.setText(launches.get(index).getMission_name());
        }
    }

}
