package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class Rocket implements Parcelable {

    String rocket_id;
    String rocket_name;
    String rocket_type;
    FirstStage first_stage;
    SecondStage second_stage;

    public String getRocket_id() {
        return rocket_id;
    }

    public String getRocket_name() {
        return rocket_name;
    }

    public String getRocket_type() {
        return rocket_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.rocket_id);
        dest.writeString(this.rocket_name);
        dest.writeString(this.rocket_type);
        dest.writeParcelable(this.first_stage, flags);
        dest.writeParcelable(this.second_stage, flags);
    }

    public Rocket() {
    }

    protected Rocket(Parcel in) {
        this.rocket_id = in.readString();
        this.rocket_name = in.readString();
        this.rocket_type = in.readString();
        this.first_stage = in.readParcelable(FirstStage.class.getClassLoader());
        this.second_stage = in.readParcelable(SecondStage.class.getClassLoader());
    }

    public static final Parcelable.Creator<Rocket> CREATOR = new Parcelable.Creator<Rocket>() {
        @Override
        public Rocket createFromParcel(Parcel source) {
            return new Rocket(source);
        }

        @Override
        public Rocket[] newArray(int size) {
            return new Rocket[size];
        }
    };
}
