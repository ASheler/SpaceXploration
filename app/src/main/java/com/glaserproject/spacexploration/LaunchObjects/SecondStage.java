package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

class SecondStage implements Parcelable {

    private int block;
    private List<Payload> payloads;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.block);
        dest.writeList(this.payloads);
    }

    public SecondStage() {
    }

    protected SecondStage(Parcel in) {
        this.block = in.readInt();
        this.payloads = new ArrayList<>();
        in.readList(this.payloads, Payload.class.getClassLoader());
    }

    public static final Parcelable.Creator<SecondStage> CREATOR = new Parcelable.Creator<SecondStage>() {
        @Override
        public SecondStage createFromParcel(Parcel source) {
            return new SecondStage(source);
        }

        @Override
        public SecondStage[] newArray(int size) {
            return new SecondStage[size];
        }
    };
}
