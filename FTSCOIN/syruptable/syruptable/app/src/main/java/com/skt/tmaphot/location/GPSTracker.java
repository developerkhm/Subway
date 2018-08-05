package com.skt.tmaphot.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.skt.tmaphot.MainSplashActivity;

import java.util.Set;


public class GPSTracker implements LocationListener {

    private final String LOG_TAG = "getgps";
    private Context mContext;
    public int locationSendCount = 0;
    private int locationFailCount = 0;
    private final int locationReCount = 3;
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location locationData; // location
    double latitude; // latitude
    double longitude; // longitude

    private final double default_latitude = 37.540705;
    private double default_longitude = 126.956764;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 1 미터
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1초

    // Declaring a Location Manager
    protected LocationManager locationManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private Handler mHandler;

    public static GPSTracker instance;

    public static GPSTracker getInstance(Context context, Handler handler) {

        if (instance == null) {
            instance = new GPSTracker(context, handler);
        } else {
            instance.mContext = context;
            instance.mHandler = handler;
        }

        return instance;
    }


    private GPSTracker(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }


    public void Update() {
        Log.d(LOG_TAG, "update GPSTracker");
        startGetLocation();
    }

    public void startGetLocation() {
        if (!getLocation()) {
            getGoogleClientLocation();
        }
    }

    @SuppressLint("MissingPermission")
    public boolean getLocation() {


        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }

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
//                    if (location == null) {
                    Log.d(LOG_TAG, "Call GPS_PROVIDER");
                    this.canGetLocation = true;
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                        if (locationManager != null) {
//                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                            if (location != null) {
//                                latitude = location.getLatitude();
//                                longitude = location.getLongitude();
//                                Log.d(LOG_TAG, "GPS latitude: " + latitude + " GPS longitude: " + longitude);
//                                sendLocation(latitude, longitude);
//                                this.canGetLocation = true;
//                            }
//                        }
//                    }
                }
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    Log.d(LOG_TAG, "Call NETWORK_PROVIDER");
                    this.canGetLocation = true;
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                    if (locationManager != null) {
//                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        if (location != null) {
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                            Log.d(LOG_TAG, "NETWORK latitude: " + latitude + " NETWORK longitude: " + longitude);
//                            sendLocation(latitude, longitude);
//                            this.canGetLocation = true;
//                        }
//                    }
                }
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "getLocation:Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return canGetLocation;
    }

    @SuppressWarnings("MissingPermission")
    public void getGoogleClientLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        OnCompleteListener<Location> mCompleteListener = new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    locationData = task.getResult();
                    double latitude = locationData.getLatitude();
                    double longitude = locationData.getLongitude();
                    Log.d(LOG_TAG, "GoogleClient: " + latitude + "longitude: " + longitude);
                    sendLocation(latitude, longitude);
                    canGetLocation = true;
                } else {
                    locationFailCount++;

                    if (locationFailCount < locationReCount) {
                        Update();
                        return;
                    }

                    Log.d(LOG_TAG, "[FAIL]getGoogleClientLocation:default_GSP setting");
                    Log.e(LOG_TAG, "getGoogleClientLocation:exception ", task.getException());
                    // GSP, 네트워크, 구글 다 실패시, 최근에 잡았던 위치 가져오기(마지막 위치)
                    double latitude = 0;
                    double longitude = 0;
                    if (locationManager != null) {
                        locationData = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (locationData != null) {
                            Log.d(LOG_TAG, "[[[[ getLastKnownLocation ]]]] ");
                            latitude = locationData.getLatitude();
                            longitude = locationData.getLongitude();
                            sendLocation(latitude, longitude);
                        } else { //이것 마저 실패하면 기본 값으로 세팅
                            Log.d(LOG_TAG, "[[[[ FAIL DEFAULT GPS VALUE SEND ]]]] ");
                            sendLocation(default_latitude, default_longitude);    // 임시
                            Toast.makeText(mContext, "위치 정보에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                            canGetLocation = false;
                        }
                    }
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
        if (locationManager != null) {
            locationManager.removeUpdates(this);
            Log.e(LOG_TAG, "[[[[[[[[[[[[[[[[[[[ ===========stopUsingGPS============= ]]]]]]]]]]]]]]");
        }
    }

    public double getLatitude() {
        if (locationData != null) {
            latitude = locationData.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (locationData != null) {
            longitude = locationData.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOG_TAG", "[onLocationChanged:] latitude: " + latitude + "longitude: " + longitude);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        sendLocation(latitude, longitude);
    }

    @Override
    public void onProviderDisabled(String provider) {
        //Toast.makeText(mContext, "onProviderDisabled " + provider, Toast.LENGTH_SHORT).show();
//        mHandler.sendEmptyMessage(MainSplashActivity.RENEW_GPS);
    }

    @Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(mContext, "onProviderEnabled " + provider, Toast.LENGTH_SHORT).show();
//        mHandler.sendEmptyMessage(MainSplashActivity.RENEW_GPS);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Toast.makeText(mContext, "onStatusChanged " + provider + " : " + status, Toast.LENGTH_SHORT).show();
//        mHandler.sendEmptyMessage(MainSplashActivity.RENEW_GPS);
    }

    private void sendLocation(double latitude, double longitude) {
        Log.d(LOG_TAG, "[sendLocation] latitude: " + latitude + " longitude: " + longitude);
        locationFailCount = 0;
        GPSData.LATITUDE = latitude;
        GPSData.LONGITUDE = longitude;
        mHandler.sendEmptyMessage(GPSData.LOCATION_UPDATE);
        locationSendCount++;
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