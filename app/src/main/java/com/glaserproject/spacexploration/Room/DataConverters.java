package com.glaserproject.spacexploration.Room;

import android.arch.persistence.room.TypeConverter;

import com.glaserproject.spacexploration.CompanyInfoObjects.Headquarters;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.CompanyInfoObjects.MilestoneLinks;
import com.glaserproject.spacexploration.LaunchObjects.LaunchSite;
import com.glaserproject.spacexploration.LaunchObjects.Links;
import com.glaserproject.spacexploration.LaunchObjects.Reuse;
import com.glaserproject.spacexploration.LaunchObjects.Rocket;
import com.glaserproject.spacexploration.LaunchObjects.Telemetry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class DataConverters {

    @TypeConverter
    public String fromRocket (Rocket rocket) {
        if (rocket == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Rocket>(){}.getType();
        String json = gson.toJson(rocket, type);
        return json;
    }


    @TypeConverter
    public Rocket toRocket(String rocketString) {
        if (rocketString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Rocket>() {}.getType();
        Rocket rocket = gson.fromJson(rocketString, type);
        return rocket;
    }


    @TypeConverter
    public String fromTelemetry (Telemetry telemetry) {
        if (telemetry == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Telemetry>(){}.getType();
        String json = gson.toJson(telemetry, type);
        return json;
    }


    @TypeConverter
    public Telemetry toTelemetry (String telemetryString) {
        if (telemetryString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Telemetry>() {}.getType();
        Telemetry telemetry = gson.fromJson(telemetryString, type);
        return telemetry;
    }


    @TypeConverter
    public String fromReuse (Reuse reuse) {
        if (reuse == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Reuse>(){}.getType();
        String json = gson.toJson(reuse, type);
        return json;
    }


    @TypeConverter
    public Reuse toReuse(String reuseString) {
        if (reuseString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Reuse>() {}.getType();
        Reuse reuse = gson.fromJson(reuseString, type);
        return reuse;
    }


    @TypeConverter
    public String fromLaunchSite (LaunchSite launchSite) {
        if (launchSite == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<LaunchSite>(){}.getType();
        String json = gson.toJson(launchSite, type);
        return json;
    }


    @TypeConverter
    public LaunchSite toLaunchSite(String launchSiteString) {
        if (launchSiteString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<LaunchSite>() {}.getType();
        LaunchSite launchSite = gson.fromJson(launchSiteString, type);
        return launchSite;
    }

    @TypeConverter
    public String fromLinks (Links links) {
        if (links == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Links>(){}.getType();
        String json = gson.toJson(links, type);
        return json;
    }


    @TypeConverter
    public Links toLinks(String linksString) {
        if (linksString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Links>() {}.getType();
        Links links = gson.fromJson(linksString, type);
        return links;
    }

    @TypeConverter
    public Date toDate(Long timestamp) {
        if (timestamp == null){
            return (null);
        }
        return new Date(timestamp);
    }

    @TypeConverter
    public Long fromDate(Date date) {
        if (date == null){
            return (null);
        }
        return date.getTime();
    }

    @TypeConverter
    public Milestone toMilestone(String milestoneString) {
        if (milestoneString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Milestone>() {}.getType();
        Milestone milestone = gson.fromJson(milestoneString, type);
        return milestone;
    }

    @TypeConverter
    public String fromMilestone(Milestone milestone) {
        if (milestone == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Milestone>(){}.getType();
        String json = gson.toJson(milestone, type);
        return json;
    }

    @TypeConverter
    public MilestoneLinks toMilestoneLinks(String linksString) {
        if (linksString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<MilestoneLinks>() {}.getType();
        MilestoneLinks links = gson.fromJson(linksString, type);
        return links;
    }

    @TypeConverter
    public String fromMilestoneLinks(MilestoneLinks milestoneLinks) {
        if (milestoneLinks == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<MilestoneLinks>(){}.getType();
        String json = gson.toJson(milestoneLinks, type);
        return json;
    }

    @TypeConverter
    public Headquarters toHeadquarters(String headquartersString) {
        if (headquartersString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Headquarters>() {}.getType();
        Headquarters headquarters = gson.fromJson(headquartersString, type);
        return headquarters;
    }

    @TypeConverter
    public String fromHeadquarters(Headquarters headquarters) {
        if (headquarters == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Headquarters>(){}.getType();
        String json = gson.toJson(headquarters, type);
        return json;
    }

}
