package com.glaserproject.spacexploration.LaunchObjects;

import java.util.List;

class Payload {

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

}
