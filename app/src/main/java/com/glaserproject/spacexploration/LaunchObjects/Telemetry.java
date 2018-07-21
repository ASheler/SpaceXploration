package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class Telemetry implements Parcelable {

    String flight_club;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.flight_club);
    }

    public Telemetry() {
    }

    protected Telemetry(Parcel in) {
        this.flight_club = in.readString();
    }

    public static final Parcelable.Creator<Telemetry> CREATOR = new Parcelable.Creator<Telemetry>() {
        @Override
        public Telemetry createFromParcel(Parcel source) {
            return new Telemetry(source);
        }

        @Override
        public Telemetry[] newArray(int size) {
            return new Telemetry[size];
        }
    };
}
