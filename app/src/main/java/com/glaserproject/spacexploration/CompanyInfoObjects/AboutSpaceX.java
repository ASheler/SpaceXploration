package com.glaserproject.spacexploration.CompanyInfoObjects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.glaserproject.spacexploration.Room.DataConverters;

@Entity(tableName = "about_spacex")
public class AboutSpaceX {

    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String founder;
    String founded;
    int employees;
    int vehicles;
    int launch_sites;
    int test_sites;
    String ceo;
    String cto;
    String coo;
    String cto_propulsion;
    long valuation;
    @TypeConverters(DataConverters.class)
    Headquarters headquarters;
    String summary;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFounder() {
        return founder;
    }

    public String getFounded() {
        return founded;
    }

    public int getEmployees() {
        return employees;
    }

    public int getVehicles() {
        return vehicles;
    }

    public int getLaunch_sites() {
        return launch_sites;
    }

    public int getTest_sites() {
        return test_sites;
    }

    public String getCeo() {
        return ceo;
    }

    public String getCto() {
        return cto;
    }

    public String getCoo() {
        return coo;
    }

    public String getCto_propulsion() {
        return cto_propulsion;
    }

    public long getValuation() {
        return valuation;
    }

    public Headquarters getHeadquarters() {
        return headquarters;
    }

    public String getSummary() {
        return summary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setFounded(String founded) {
        this.founded = founded;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    public void setVehicles(int vehicles) {
        this.vehicles = vehicles;
    }

    public void setLaunch_sites(int launch_sites) {
        this.launch_sites = launch_sites;
    }

    public void setTest_sites(int test_sites) {
        this.test_sites = test_sites;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public void setCto(String cto) {
        this.cto = cto;
    }

    public void setCoo(String coo) {
        this.coo = coo;
    }

    public void setCto_propulsion(String cto_propulsion) {
        this.cto_propulsion = cto_propulsion;
    }

    public void setValuation(long valuation) {
        this.valuation = valuation;
    }

    public void setHeadquarters(Headquarters headquarters) {
        this.headquarters = headquarters;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}