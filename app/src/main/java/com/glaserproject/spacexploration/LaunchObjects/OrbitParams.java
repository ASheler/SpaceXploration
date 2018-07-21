package com.glaserproject.spacexploration.LaunchObjects;

import android.os.Parcel;
import android.os.Parcelable;

class OrbitParams implements Parcelable {

    String reference_system;
    String regime;
    String longitude;
    String semi_major_axis_km;
    String eccentricity;
    String periapsis_km;
    String apoapsis_km;
    String inclination_deg;
    String period_min;
    String lifespan_years;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reference_system);
        dest.writeString(this.regime);
        dest.writeString(this.longitude);
        dest.writeString(this.semi_major_axis_km);
        dest.writeString(this.eccentricity);
        dest.writeString(this.periapsis_km);
        dest.writeString(this.apoapsis_km);
        dest.writeString(this.inclination_deg);
        dest.writeString(this.period_min);
        dest.writeString(this.lifespan_years);
    }

    public OrbitParams() {
    }

    protected OrbitParams(Parcel in) {
        this.reference_system = in.readString();
        this.regime = in.readString();
        this.longitude = in.readString();
        this.semi_major_axis_km = in.readString();
        this.eccentricity = in.readString();
        this.periapsis_km = in.readString();
        this.apoapsis_km = in.readString();
        this.inclination_deg = in.readString();
        this.period_min = in.readString();
        this.lifespan_years = in.readString();
    }

    public static final Parcelable.Creator<OrbitParams> CREATOR = new Parcelable.Creator<OrbitParams>() {
        @Override
        public OrbitParams createFromParcel(Parcel source) {
            return new OrbitParams(source);
        }

        @Override
        public OrbitParams[] newArray(int size) {
            return new OrbitParams[size];
        }
    };
}
