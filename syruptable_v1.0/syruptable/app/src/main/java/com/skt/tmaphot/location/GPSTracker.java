package com.skt.tmaphot.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.MainSplashActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class GPSTracker implements LocationListener {

    private Context mContext;
    private final String LOG_TAG = "getgps";

    public final int LOCATION_SUCCESS = 900;
    public final int LOCATION_FAIL = 901;
    public final int LOCATION_LASTKNOWN = 902;
    public final int LOCATION_UPDATE = 903;

    private int locationFailCount = 0;
    private final int locationReCount = 3;

    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;

    private boolean canGetLocation = false;
    private boolean alreayGetLocation = false;
    private boolean isTimeOut = false;

    private int initDelayTime_ms = 9000;


    Location locationData; // location

    private final double default_latitude = 37.540705;
    private final double default_longitude = 126.956764;


    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 1 미터
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000; // 1초

    // Declaring a Location Manager
    protected LocationManager locationManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private Handler mHandler;

    private static GPSTracker instance;

    public static GPSTracker getInstance(Context context, Handler handler) {

        if (instance == null) {
            instance = new GPSTracker(context, handler);
        } else {
            instance.mContext = context;
            instance.mHandler = handler;
        }
        return instance;
    }

    public static GPSTracker getInstance() {
        return instance;
    }


    private GPSTracker(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
    }

//    public void Update() {
//        Log.d(LOG_TAG, "update GPSTracker");
//        if (!getLocation()) {
//            getGoogleClientLocation();
//        }
//    }

    public void startGetLocation() {
        alreayGetLocation = false;
        if (!getLocation()) {
            getGoogleClientLocation();
        }
        Log.d("getgps", ">>>>>startGetLocation");
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!alreayGetLocation && !isTimeOut) {
                    isTimeOut = true;
                    Log.d("getgps", "alreayGetLocation");
                    mHandler.sendEmptyMessage(LOCATION_FAIL);
                    stopUsingGPS();
                }
            }
        }, initDelayTime_ms);

    }

    public void reStartGetLocation() {
        alreayGetLocation = false;
        if (!getLocation()) {
            getGoogleClientLocation();
        }
    }

    @SuppressLint("MissingPermission")
    public boolean getLocation() {


        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            List<String> listPermissionsNeeded = new ArrayList<>();
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        }

        isTimeOut = false;

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
                    sendLocation(latitude, longitude, LOCATION_SUCCESS);
                    canGetLocation = true;
                } else {
                    locationFailCount++;
                    canGetLocation = false;
                    if (locationFailCount < locationReCount) {
                        reStartGetLocation();
                        return;
                    }

                    Log.d(LOG_TAG, "[FAIL]getGoogleClientLocation:default_GSP setting");
                    Log.d(LOG_TAG, "getGoogleClientLocation:exception ", task.getException());
                    // GSP, 네트워크, 구글 다 실패시, 최근에 잡았던 위치 가져오기(마지막 위치)
                    double latitude = 0;
                    double longitude = 0;
                    if (locationManager != null) {
                        locationData = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (locationData != null) {
                            Log.d(LOG_TAG, "[[[[ getLastKnownLocation ]]]] ");
                            latitude = locationData.getLatitude();
                            longitude = locationData.getLongitude();
                            canGetLocation = true;
                            sendLocation(latitude, longitude, LOCATION_LASTKNOWN);
                        } else { //이것 마저 실패하면 기본 값으로 세팅
                            Log.d(LOG_TAG, "[[[[ FAIL DEFAULT GPS VALUE SEND ]]]] ");
                            sendLocation(default_latitude, default_longitude, LOCATION_FAIL);    // 임시
                            CommonUtil.getInstance().custom_toast(mContext, "위치 정보에 실패 하였습니다. 종료 후 다시 실행해 주세요.");
                            canGetLocation = false;
                            stopUsingGPS();
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
            Log.d(LOG_TAG, "[[[[[[[[[[[[[[[[[[[ ===========stopUsingGPS============= ]]]]]]]]]]]]]]");
        }
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LOG_TAG", "[onLocationChanged UPDATE:]");
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        sendLocation(latitude, longitude, LOCATION_UPDATE);
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

    private void sendLocation(double latitude, double longitude, int Result) {
        Log.d(LOG_TAG, "[sendLocation] latitude: " + latitude + " longitude: " + longitude);
        alreayGetLocation = true;
        locationFailCount = 0;
        GPSData.getInstance().setLatitude(latitude);
        GPSData.getInstance().setLongitude(longitude);
        mHandler.sendEmptyMessage(Result);
        stopUsingGPS();
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

    public boolean failGps(final Context ctx) {

        new MaterialStyledDialog.Builder(ctx)
                .setTitle(R.string.app_name)
                .setDescription("위치 정보가 정확하지 않습니다.\n재탐색 하시겠습니까?")
                .setStyle(Style.HEADER_WITH_TITLE)
                .setHeaderColor(R.color.colorBlack)
                .setPositiveText("네")
                .withDialogAnimation(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        GPSTracker.getInstance().startGetLocation();
                    }
                })
                .setNegativeText("아니요")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        GPSTracker.getInstance().stopUsingGPS();
                        if(ctx instanceof BaseActivity){
                            ((BaseActivity)ctx).progressOFF();
                        }

                        if (ctx instanceof MainSplashActivity) {
                            ((MainSplashActivity) ctx).finish();
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    }
                })
                .show();


//        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
////        builder.setTitle("다이얼로그 제목임")
//        builder.setMessage("위치 정보가 정확하지 않습니다.\n재탐색 하시겠습니까?")
//                .setPositiveButton("네", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        GPSTracker.getInstance().startGetLocation();
//                    }
//                })
//                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        GPSTracker.getInstance().stopUsingGPS();
//                        if(ctx instanceof BaseActivity){
//                            ((BaseActivity)ctx).progressOFF();
//                        }
//
//                        if (ctx instanceof MainSplashActivity) {
//                            ((MainSplashActivity) ctx).finish();
//                            android.os.Process.killProcess(android.os.Process.myPid());
//                            System.exit(1);
//                        }
//                    }
//                });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();

        return false;
    }
}