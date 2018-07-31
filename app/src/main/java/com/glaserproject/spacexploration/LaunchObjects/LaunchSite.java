package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class LaunchSite implements Parcelable {

    private String site_id;
    private String site_name;
    private String site_name_long;

    public String getSite_id() {
        return site_id;
    }

    public String getSite_name() {
        return site_name;
    }

    public String getSite_name_long() {
        return site_name_long;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.site_id);
        dest.writeString(this.site_name);
        dest.writeString(this.site_name_long);
    }

    public LaunchSite() {
    }

    protected LaunchSite(Parcel in) {
        this.site_id = in.readString();
        this.site_name = in.readString();
        this.site_name_long = in.readString();
    }

    public static final Parcelable.Creator<LaunchSite> CREATOR = new Parcelable.Creator<LaunchSite>() {
        @Override
        public LaunchSite createFromParcel(Parcel source) {
            return new LaunchSite(source);
        }

        @Override
        public LaunchSite[] newArray(int size) {
            return new LaunchSite[size];
        }
    };
}
