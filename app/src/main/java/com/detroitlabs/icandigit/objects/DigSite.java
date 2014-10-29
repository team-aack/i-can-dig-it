package com.detroitlabs.icandigit.objects;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by Borham on 10/28/14.
 */
public class DigSite {
    private long timeStamp;  //long to hold the current time in milliseconds
    private Marker siteMarker;

    public DigSite(long timeStamp, Marker siteMarker) {
        this.timeStamp = timeStamp;
        this.siteMarker = siteMarker;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Marker getSiteMarker() {
        return siteMarker;
    }

    public void setSiteMarker(Marker siteMarker) {
        this.siteMarker = siteMarker;
    }
}
