package com.glaserproject.spacexploration.LaunchObjects;

public class Launch {

    private int flight_number;
    private String mission_name;
    private String launch_year;
    private long launch_date_unix;
    private String launch_date_utc;
    private String launch_date_local;
    private Rocket rocket;
    private Telemetry telemetry;
    private Reuse reuse;
    private LaunchSite launch_site;
    private boolean launch_success;
    private Links links;
    String details;


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


}
