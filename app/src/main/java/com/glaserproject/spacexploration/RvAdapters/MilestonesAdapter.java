package com.glaserproject.spacexploration.RvAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MilestonesAdapter extends RecyclerView.Adapter<MilestonesAdapter.InfoViewHolder> {

    private List<Milestone> milestones;

    public void setMilestones (List<Milestone> milestones){
        this.milestones = milestones;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.milestone_tile, parent, false);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        holder.bind(position);
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.milestone_name)
        TextView milestoneName;

        public InfoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

        void bind(int index){
            milestoneName.setText(milestones.get(index).getTitle());
        }

    }

    @Override
    public int getItemCount() {
        if (milestones == null){
            return 0;
        }
        return milestones.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
