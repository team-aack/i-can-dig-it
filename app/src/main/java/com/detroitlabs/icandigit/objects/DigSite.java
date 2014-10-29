package com.detroitlabs.icandigit.objects;

/**
 * Created by Borham on 10/28/14.
 */
public class DigSite {
    private long timeStamp;  //long to hold the current time in milliseconds
    private double lat;
    private double lng;

    public DigSite(long timeStamp, double lat, double lng) {
        this.timeStamp = timeStamp;
        this.lat = lat;
        this.lng = lng;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
