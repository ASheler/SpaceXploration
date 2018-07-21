package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

class Payload implements Parcelable {

    String payload_id;
    String cap_serial;
    boolean reused;
    List<String> customers;
    String payload_type;
    String payload_mass_kg;
    String payload_mass_lbs;
    String orbit;
    OrbitParams orbit_params;
    String mass_returned_kg;
    String mass_returned_lbs;
    String flight_time_sec;
    String cargo_manifest;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.payload_id);
        dest.writeString(this.cap_serial);
        dest.writeByte(this.reused ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.customers);
        dest.writeString(this.payload_type);
        dest.writeString(this.payload_mass_kg);
        dest.writeString(this.payload_mass_lbs);
        dest.writeString(this.orbit);
        dest.writeParcelable(this.orbit_params, flags);
        dest.writeString(this.mass_returned_kg);
        dest.writeString(this.mass_returned_lbs);
        dest.writeString(this.flight_time_sec);
        dest.writeString(this.cargo_manifest);
    }

    public Payload() {
    }

    protected Payload(Parcel in) {
        this.payload_id = in.readString();
        this.cap_serial = in.readString();
        this.reused = in.readByte() != 0;
        this.customers = in.createStringArrayList();
        this.payload_type = in.readString();
        this.payload_mass_kg = in.readString();
        this.payload_mass_lbs = in.readString();
        this.orbit = in.readString();
        this.orbit_params = in.readParcelable(OrbitParams.class.getClassLoader());
        this.mass_returned_kg = in.readString();
        this.mass_returned_lbs = in.readString();
        this.flight_time_sec = in.readString();
        this.cargo_manifest = in.readString();
    }

    public static final Parcelable.Creator<Payload> CREATOR = new Parcelable.Creator<Payload>() {
        @Override
        public Payload createFromParcel(Parcel source) {
            return new Payload(source);
        }

        @Override
        public Payload[] newArray(int size) {
            return new Payload[size];
        }
    };
}
