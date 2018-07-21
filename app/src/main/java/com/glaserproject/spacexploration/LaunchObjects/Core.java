package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

class Core implements Parcelable {
    String core_serial;
    String flight;
    String block;
    boolean reused;
    boolean land_success;
    String landing_type;
    String landing_vehicle;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.core_serial);
        dest.writeString(this.flight);
        dest.writeString(this.block);
        dest.writeByte(this.reused ? (byte) 1 : (byte) 0);
        dest.writeByte(this.land_success ? (byte) 1 : (byte) 0);
        dest.writeString(this.landing_type);
        dest.writeString(this.landing_vehicle);
    }

    public Core() {
    }

    protected Core(Parcel in) {
        this.core_serial = in.readString();
        this.flight = in.readString();
        this.block = in.readString();
        this.reused = in.readByte() != 0;
        this.land_success = in.readByte() != 0;
        this.landing_type = in.readString();
        this.landing_vehicle = in.readString();
    }

    public static final Parcelable.Creator<Core> CREATOR = new Parcelable.Creator<Core>() {
        @Override
        public Core createFromParcel(Parcel source) {
            return new Core(source);
        }

        @Override
        public Core[] newArray(int size) {
            return new Core[size];
        }
    };
}
