package com.glaserproject.spacexploration.CompanyInfoObjects;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.glaserproject.spacexploration.Room.DataConverters;

@Entity (tableName = "milestones")
public class Milestone {

    private String title;
    private String event_date_utc;
    @PrimaryKey
    private long event_date_unix;
    private int flight_number;
    private String details;
    @TypeConverters(DataConverters.class)
    private MilestoneLinks links;

    public String getTitle() {
        return title;
    }

    public String getEvent_date_utc() {
        return event_date_utc;
    }

    public long getEvent_date_unix() {
        return event_date_unix;
    }

    public int getFlight_number() {
        return flight_number;
    }

    public String getDetails() {
        return details;
    }

    public MilestoneLinks getLinks() {
        return links;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEvent_date_utc(String event_date_utc) {
        this.event_date_utc = event_date_utc;
    }

    public void setEvent_date_unix(long event_date_unix) {
        this.event_date_unix = event_date_unix;
    }

    public void setFlight_number(int flight_number) {
        this.flight_number = flight_number;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setLinks(MilestoneLinks links) {
        this.links = links;
    }
}
