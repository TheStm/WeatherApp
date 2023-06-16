package com.paint23.projektpaint;

import org.springframework.beans.factory.annotation.Value;

public class City {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    private String name;

    private String lon;

    private String lat;


}
