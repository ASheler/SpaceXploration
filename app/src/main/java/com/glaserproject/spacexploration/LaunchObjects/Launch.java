package com.glaserproject.spacexploration.LaunchObjects;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.glaserproject.spacexploration.Room.DataConverters;

@Entity (tableName = "past_launches")
public class Launch {

    @PrimaryKey
    private int flight_number;
    private String mission_name;
    private String launch_year;
    private long launch_date_unix;
    private String launch_date_utc;
    private String launch_date_local;
    @TypeConverters(DataConverters.class)
    private Rocket rocket;
    @TypeConverters(DataConverters.class)
    private Telemetry telemetry;
    @TypeConverters(DataConverters.class)
    private Reuse reuse;
    @TypeConverters(DataConverters.class)
    private LaunchSite launch_site;
    private boolean launch_success;
    @TypeConverters(DataConverters.class)
    private Links links;
    private String details;


    public int getFlight_number() {
        return flight_number;
    }

    public String getMission_name() {
        return mission_name;
    }

    public String getLaunch_year() {
        return launch_year;
    }

    public long getLaunch_date_unix() {
        return launch_date_unix;
    }

    public String getLaunch_date_utc() {
        return launch_date_utc;
    }

    public String getLaunch_date_local() {
        return launch_date_local;
    }

    public Rocket getRocket() {
        return rocket;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

    public Reuse getReuse() {
        return reuse;
    }

    public LaunchSite getLaunch_site() {
        return launch_site;
    }

    public boolean isLaunch_success() {
        return launch_success;
    }

    public Links getLinks() {
        return links;
    }

    public String getDetails() {
        return details;
    }


    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

    public void setMission_name(String mission_name) {
        this.mission_name = mission_name;
    }

    public void setLaunch_year(String launch_year) {
        this.launch_year = launch_year;
    }

    public void setLaunch_date_unix(long launch_date_unix) {
        this.launch_date_unix = launch_date_unix;
    }

    public void setLaunch_date_utc(String launch_date_utc) {
        this.launch_date_utc = launch_date_utc;
    }

    public void setLaunch_date_local(String launch_date_local) {
        this.launch_date_local = launch_date_local;
    }

    public void setRocket(Rocket rocket) {
        this.rocket = rocket;
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void setReuse(Reuse reuse) {
        this.reuse = reuse;
    }

    public void setLaunch_site(LaunchSite launch_site) {
        this.launch_site = launch_site;
    }

    public void setLaunch_success(boolean launch_success) {
        this.launch_success = launch_success;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
