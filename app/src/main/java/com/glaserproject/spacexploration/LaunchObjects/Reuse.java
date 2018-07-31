package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class Reuse implements Parcelable {

    private boolean core;
    private boolean side_core1;
    private boolean side_core2;
    private boolean fairings;
    private boolean capsule;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.core ? (byte) 1 : (byte) 0);
        dest.writeByte(this.side_core1 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.side_core2 ? (byte) 1 : (byte) 0);
        dest.writeByte(this.fairings ? (byte) 1 : (byte) 0);
        dest.writeByte(this.capsule ? (byte) 1 : (byte) 0);
    }

    public Reuse() {
    }

    Reuse(Parcel in) {
        this.core = in.readByte() != 0;
        this.side_core1 = in.readByte() != 0;
        this.side_core2 = in.readByte() != 0;
        this.fairings = in.readByte() != 0;
        this.capsule = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Reuse> CREATOR = new Parcelable.Creator<Reuse>() {
        @Override
        public Reuse createFromParcel(Parcel source) {
            return new Reuse(source);
        }

        @Override
        public Reuse[] newArray(int size) {
            return new Reuse[size];
        }
    };
}
