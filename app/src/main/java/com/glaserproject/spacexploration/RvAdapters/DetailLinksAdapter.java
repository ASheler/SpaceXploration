package com.glaserproject.spacexploration.RvAdapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.glaserproject.spacexploration.LaunchObjects.Links;
import com.glaserproject.spacexploration.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailLinksAdapter extends RecyclerView.Adapter<DetailLinksAdapter.LinksViewHolder> {

    private Links links;
    private onClickHandler clickHandler;


    public DetailLinksAdapter(Links links, onClickHandler clickHandler) {
        this.links = links;
        this.clickHandler = clickHandler;
    }


    @NonNull
    @Override
    public DetailLinksAdapter.LinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.detail_links_tile, parent, false);
        return new LinksViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull DetailLinksAdapter.LinksViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (links == null){
            return 0;
        }
        return 9;
    }

    public class LinksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.link_icon)
        ImageView linkIcon;
        @BindView(R.id.link_description)
        TextView linkDesc;


        public LinksViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }


        void bind(int position){
            if (getLink(position) != null){
                linkIcon.setImageResource(getResourceId(position));
                itemView.setOnClickListener(this);
            } else {
                linkIcon.setImageResource(getShadowResourceId(position));
            }
            linkIcon.setContentDescription(getIconDesc(position));
            linkDesc.setText(getIconDesc(position));
        }



        @Override
        public void onClick(View v) {
            clickHandler.onClick(getLink(getAdapterPosition()));
        }
    }

    private String getLink(int position){

        switch (position){
            case 0:
                return links.getMission_patch();
            case 1:
                return links.getMission_patch_small();
            case 2:
                return links.getReddit_campaign();
            case 3:
                return links.getReddit_launch();
            case 4:
                return links.getReddit_recovery();
            case 5:
                return links.getReddit_media();
            case 6:
                return links.getPresskit();
            case 7:
                return links.getArticle_link();
            case 8:
                return links.getWikipedia();
            case 9:
                return links.getVideo_link();
        }
        return null;

    }

    private int getResourceId(int position){

        switch (position){
            case 0:
                return R.drawable.ic_mission_patch;
            case 1:
                return R.drawable.ic_mission_patch;
            case 2:
                return R.drawable.ic_reddit;
            case 3:
                return R.drawable.ic_reddit;
            case 4:
                return R.drawable.ic_reddit;
            case 5:
                return R.drawable.ic_reddit;
            case 6:
                return R.drawable.ic_presskit;
            case 7:
                return R.drawable.ic_article;
            case 8:
                return R.drawable.ic_wikipedia;
            case 9:
                return R.drawable.ic_youtube;
        }
        return 0;

    }


    private String getIconDesc(int position){

        switch (position){
            case 0:
                return "Mission Patch";
            case 1:
                return "Mission Patch Small";
            case 2:
                return "Campaign thread";
            case 3:
                return "Launch thread";
            case 4:
                return "Recovery thread";
            case 5:
                return "Media thread";
            case 6:
                return "Presskit";
            case 7:
                return "Article";
            case 8:
                return "Wikipedia";
            case 9:
                return "Video";
        }
        return null;

    }


    private int getShadowResourceId(int position){

        switch (position){
            case 0:
                return R.drawable.ic_mission_patch_gray;
            case 1:
                return R.drawable.ic_mission_patch_gray;

            case 2:
                return R.drawable.ic_reddit_gray;
            case 3:
                return R.drawable.ic_reddit_gray;

            case 4:
                return R.drawable.ic_reddit_gray;

            case 5:
                return R.drawable.ic_reddit_gray;

            case 6:
                return R.drawable.ic_presskit_gray;

            case 7:
                return R.drawable.ic_article_gray;

            case 8:
                return R.drawable.ic_wikipedia_gray;

            case 9:
                return R.drawable.ic_youtube_gray;

        }
        return 0;

    }



    //interface for the click
    public interface onClickHandler {
        void onClick(String url);
    }

}
