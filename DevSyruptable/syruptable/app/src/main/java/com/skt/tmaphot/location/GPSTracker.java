package com.skt.tmaphot.location;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.skt.tmaphot.SplashActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;


public class GPSTracker implements LocationListener {

    private final String LOG_TAG = "getgps";
    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    private final double  default_latitude = 37.540705;
    private double  default_longitude = 126.956764;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 미터
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 2; // 5초

    // Declaring a Location Manager
    protected LocationManager locationManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private Handler mHandler;

    public GPSTracker(Context context, Handler handler) {
        Log.d(LOG_TAG, "new GPSTracker");
        this.mContext = context;
        this.mHandler = handler;
        startGetLocation();
    }

    public void Update() {
        Log.d(LOG_TAG, "update GPSTracker");
        startGetLocation();
    }

    private void startGetLocation() {
        if (getLocation() == null) {
            canGetLocation = false;
            getGoogleClientLocation();
        }
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.d(LOG_TAG, "getLocation() isGPSEnabled=" + isGPSEnabled + " isNetworkEnabled=" + isNetworkEnabled);

            if (!isGPSEnabled && !isNetworkEnabled) {
                this.canGetLocation = false;
                Log.d(LOG_TAG, "GPS [================Disabled================]");
                // gps가 사용처리 못햇을때 처리 해야됨
                // no network provider is enabled


            } else {

                Log.d(LOG_TAG, "[================GPS Enabled===================]");
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        Log.d(LOG_TAG, "Call GPS_PROVIDER");
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.d(LOG_TAG, "GPS latitude: " + latitude + " GPS longitude: " + longitude);
                                sendLocation(latitude, longitude);
                                this.canGetLocation = true;
                            }
                        }
                    }
                }
                // First get location from Network Provider
                if (location == null && isNetworkEnabled) {
                    Log.d(LOG_TAG, "Call NETWORK_PROVIDER");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d(LOG_TAG, "NETWORK latitude: " + latitude + " NETWORK longitude: " + longitude);
                            sendLocation(latitude, longitude);
                            this.canGetLocation = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "getLocation:Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return location;
    }

    @SuppressWarnings("MissingPermission")
    public void getGoogleClientLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        OnCompleteListener<Location> mCompleteListener = new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    location = task.getResult();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.d(LOG_TAG, "GoogleClient: " + latitude + "longitude: " + longitude);
                    sendLocation(latitude, longitude);
                    canGetLocation = true;
                } else {
                    sendLocation(default_latitude, default_longitude);    // 임시
                    Log.d(LOG_TAG, "[FAIL]getGoogleClientLocation:default_GSP setting");
                    Log.w(LOG_TAG, "getGoogleClientLocation:exception ", task.getException());
                    canGetLocation = false;
                }
            }
        };

        mFusedLocationClient.getLastLocation().addOnCompleteListener(mCompleteListener);
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            sendLocation(latitude, longitude);
            Log.d("onLocationChanged", "latitude: " + latitude + "longitude: " + longitude);
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(mContext, "onProviderDisabled " + provider, Toast.LENGTH_SHORT).show();
        mHandler.sendEmptyMessage(SplashActivity.RENEW_GPS);
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(mContext, "onProviderEnabled " + provider, Toast.LENGTH_SHORT).show();
        mHandler.sendEmptyMessage(SplashActivity.RENEW_GPS);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Toast.makeText(mContext, "onStatusChanged " + provider + " : " + status, Toast.LENGTH_SHORT).show();
        mHandler.sendEmptyMessage(SplashActivity.RENEW_GPS);
    }

    private void sendLocation(double latitude, double longitude) {
        Log.d(LOG_TAG, "sendLocation latitude: " + latitude + " longitude: " + longitude);
        Message msg = mHandler.obtainMessage();
        msg.what = SplashActivity.SEND_PRINT;
        msg.obj = new GPSData(latitude, longitude);
        mHandler.sendMessage(msg);
    }

    public static String printBundle(Bundle extras) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("extras = " + extras);
            sb.append("\n");
            if (extras != null) {
                Set keys = extras.keySet();
                sb.append("++ bundle key count = " + keys.size());
                sb.append("\n");

                for (String _key : extras.keySet()) {
                    sb.append("key=" + _key + " : " + extras.get(_key) + ",");
                }
                sb.append("\n");
            }
        } catch (Exception e) {

        } finally {

        }
        return sb.toString();
    }
}