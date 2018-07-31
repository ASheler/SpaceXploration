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

public class DataConverters {

    @TypeConverter
    public String fromRocket (Rocket rocket) {
        if (rocket == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Rocket>(){}.getType();
        return gson.toJson(rocket, type);
    }


    @TypeConverter
    public Rocket toRocket(String rocketString) {
        if (rocketString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Rocket>() {}.getType();
        return gson.fromJson(rocketString, type);
    }


    @TypeConverter
    public String fromTelemetry (Telemetry telemetry) {
        if (telemetry == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Telemetry>(){}.getType();
        return gson.toJson(telemetry, type);
    }


    @TypeConverter
    public Telemetry toTelemetry (String telemetryString) {
        if (telemetryString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Telemetry>() {}.getType();
        return gson.fromJson(telemetryString, type);
    }


    @TypeConverter
    public String fromReuse (Reuse reuse) {
        if (reuse == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Reuse>(){}.getType();
        return gson.toJson(reuse, type);
    }


    @TypeConverter
    public Reuse toReuse(String reuseString) {
        if (reuseString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Reuse>() {}.getType();
        return gson.fromJson(reuseString, type);
    }


    @TypeConverter
    public String fromLaunchSite (LaunchSite launchSite) {
        if (launchSite == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<LaunchSite>(){}.getType();
        return gson.toJson(launchSite, type);
    }


    @TypeConverter
    public LaunchSite toLaunchSite(String launchSiteString) {
        if (launchSiteString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<LaunchSite>() {}.getType();
        return gson.fromJson(launchSiteString, type);
    }

    @TypeConverter
    public String fromLinks (Links links) {
        if (links == null){
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<Links>(){}.getType();
        return gson.toJson(links, type);
    }


    @TypeConverter
    public Links toLinks(String linksString) {
        if (linksString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Links>() {}.getType();
        return gson.fromJson(linksString, type);
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
        return gson.fromJson(milestoneString, type);
    }

    @TypeConverter
    public String fromMilestone(Milestone milestone) {
        if (milestone == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Milestone>(){}.getType();
        return gson.toJson(milestone, type);
    }

    @TypeConverter
    public MilestoneLinks toMilestoneLinks(String linksString) {
        if (linksString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<MilestoneLinks>() {}.getType();
        return gson.fromJson(linksString, type);
    }

    @TypeConverter
    public String fromMilestoneLinks(MilestoneLinks milestoneLinks) {
        if (milestoneLinks == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<MilestoneLinks>(){}.getType();
        return gson.toJson(milestoneLinks, type);
    }

    @TypeConverter
    public Headquarters toHeadquarters(String headquartersString) {
        if (headquartersString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Headquarters>() {}.getType();
        return gson.fromJson(headquartersString, type);
    }

    @TypeConverter
    public String fromHeadquarters(Headquarters headquarters) {
        if (headquarters == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Headquarters>(){}.getType();
        return gson.toJson(headquarters, type);
    }

}
