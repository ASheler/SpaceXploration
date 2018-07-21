package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

class FirstStage implements Parcelable {

    List<Core> cores;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.cores);
    }

    public FirstStage() {
    }

    protected FirstStage(Parcel in) {
        this.cores = new ArrayList<Core>();
        in.readList(this.cores, Core.class.getClassLoader());
    }

    public static final Parcelable.Creator<FirstStage> CREATOR = new Parcelable.Creator<FirstStage>() {
        @Override
        public FirstStage createFromParcel(Parcel source) {
            return new FirstStage(source);
        }

        @Override
        public FirstStage[] newArray(int size) {
            return new FirstStage[size];
        }
    };
}
