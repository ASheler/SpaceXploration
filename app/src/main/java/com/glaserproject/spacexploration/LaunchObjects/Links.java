package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class Links implements Parcelable {

    String mission_patch;
    String mission_patch_small;
    String reddit_campaign;
    String reddit_launch;
    String reddit_recovery;
    String reddit_media;
    String presskit;
    String article_link;
    String wikipedia;
    String video_link;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mission_patch);
        dest.writeString(this.mission_patch_small);
        dest.writeString(this.reddit_campaign);
        dest.writeString(this.reddit_launch);
        dest.writeString(this.reddit_recovery);
        dest.writeString(this.reddit_media);
        dest.writeString(this.presskit);
        dest.writeString(this.article_link);
        dest.writeString(this.wikipedia);
        dest.writeString(this.video_link);
    }

    public Links() {
    }

    protected Links(Parcel in) {
        this.mission_patch = in.readString();
        this.mission_patch_small = in.readString();
        this.reddit_campaign = in.readString();
        this.reddit_launch = in.readString();
        this.reddit_recovery = in.readString();
        this.reddit_media = in.readString();
        this.presskit = in.readString();
        this.article_link = in.readString();
        this.wikipedia = in.readString();
        this.video_link = in.readString();
    }

    public static final Parcelable.Creator<Links> CREATOR = new Parcelable.Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };
}
