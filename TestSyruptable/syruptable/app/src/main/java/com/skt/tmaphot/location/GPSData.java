package com.skt.tmaphot.location;

public class GPSData {
    public static String LOCATION_ADDRESS;
    public static double LATITUDE;
    public static double LONGITUDE;

    public GPSData(double latitude_, double longitude_) {
        this.LATITUDE = latitude_;
        this.LONGITUDE = longitude_;
    }

    public double getLatitude() {
        return LATITUDE;
    }

    public double getLongitude() {
        return LONGITUDE;
    }
}
