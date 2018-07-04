package com.skt.tmaphot.location;

public class GPSData {

    public GPSData(double latitude_, double longitude_) {
        this.latitude = latitude_;
        this.longitude = longitude_;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static double latitude;
    public static double longitude;
}
