package com.skt.tmaphot.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSData {

    private static GPSData instance;
    private String location_address;
    private double latitude;
    private double longitude;

    public static GPSData getInstance() {
        if (instance == null) {
            instance = new GPSData();
        }
        return instance;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private GPSData() {
    }

    public String gpsTransferAddress(Context context) {
        Log.d("getgps", "initGPSTransferAddress Call GPSData.LATITUD :" + latitude + "  GPSData.LONGITUDE :" + longitude);
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
            String t = null;
            if (addresses.size() > 0) {
                if (addresses.get(0).getSubLocality() != null) {
                    t = addresses.get(0).getSubLocality();   //구
                } else {
                    t = addresses.get(0).getLocality();   //시
                }

                String tt = addresses.get(0).getThoroughfare();     //동
                Log.d("getgps", "initGPSTransferAddress addresses GPSData.LATITUD :" + latitude + "  GPSData.LONGITUDE :" + longitude);

                location_address = t + " " + tt + " ▼";
                Log.d("getgps", "initGPSTransferAddress LOCATION_ADDRESS :" + longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location_address;
    }
}
