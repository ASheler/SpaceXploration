package com.glaserproject.spacexploration.RvAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.R;
import com.glaserproject.spacexploration.Utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecycleView adapter for Company Info
 * <p>
 * Adapter has 2 layout that will get inflated - one with company description
 * and other with milestones
 */

public class CompanyInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Milestone> milestones;
    private AboutSpaceX aboutSpaceX;

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
        notifyDataSetChanged();
    }

    public void setAboutSpaceX(AboutSpaceX aboutSpaceX) {
        this.aboutSpaceX = aboutSpaceX;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        //select different layout for first item - company info
        if (position == 0) {
            View view = inflater.inflate(R.layout.company_info_tile, parent, false);
            return new CompanyInfoViewHolder(view);
        } else {

            //select different layout for all other items - milestones
            View view = inflater.inflate(R.layout.milestone_tile, parent, false);
            return new MilestonesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Bind views
        if (holder.getItemViewType() == 0) {
            CompanyInfoViewHolder infoViewHolder = (CompanyInfoViewHolder) holder;
            infoViewHolder.bind();
        } else {
            MilestonesViewHolder milestonesHolder = (MilestonesViewHolder) holder;
            //due to insertion of first item, we have to move position
            milestonesHolder.bind(position - 1);
        }
    }

    @Override
    public int getItemCount() {
        if (milestones == null) {
            return 0;
        }
        //due to insertion of first item, we have to increase size of rv
        return milestones.size() + 1;
    }

    //Company Info ViewHolder
    public class CompanyInfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.company_summary_tv)
        TextView companySummaryTv;
        @BindView(R.id.company_name_tv)
        TextView companyNameTv;
        @BindView(R.id.about_spacex_cardview)
        CardView aboutSpacexCard;
        @BindView(R.id.milestones_header_tv)
        TextView milestonesHeader;

        public CompanyInfoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind() {
            //if data != null, setText to textviews
            if (aboutSpaceX != null) {

                companyNameTv.setText(aboutSpaceX.getName());
                companySummaryTv.setText(aboutSpaceX.getSummary());
            } else {
                //if data == null, hide UI
                aboutSpacexCard.setVisibility(View.GONE);
                milestonesHeader.setVisibility(View.GONE);
            }

        }
    }

    //Milestones ViewHolder
    public class MilestonesViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.milestone_name)
        TextView milestoneName;
        @BindView(R.id.milestone_date)
        TextView milestoneDate;
        @BindView(R.id.milestone_details)
        TextView milestoneDetails;

        public MilestonesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

        void bind(int index) {
            //get current Milestone
            Milestone currentMilestone = milestones.get(index);

            //set text to TextViews
            milestoneName.setText(currentMilestone.getTitle());
            milestoneDetails.setText(currentMilestone.getDetails());
            milestoneDate.setText(DateUtils.formateDate(currentMilestone.getEvent_date_unix()));
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
