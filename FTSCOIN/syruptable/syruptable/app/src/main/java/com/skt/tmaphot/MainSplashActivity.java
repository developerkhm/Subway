package com.skt.tmaphot;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.skt.tmaphot.location.GPSData;
import com.skt.tmaphot.location.GPSTracker;

import java.util.ArrayList;
import java.util.List;

public class MainSplashActivity extends AppCompatActivity {

    private Context mContext;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    //GPS
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    public Handler mHandler;
    GPSTracker gpsTracker;
    public static int RENEW_GPS = 1;
    public static int SEND_PRINT = 2;


    private String TAG = "gps";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //화면 꺼짐 방지

        mContext = this;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
//                if (msg.what == RENEW_GPS) {
//                    makeNewGpsService();
//                }
//                if (msg.what == SEND_PRINT) {
//                    logPrint((GPSData) msg.obj);
//                }
            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            //Do not need to check the permission
            initStart(2000);
        } else {
            if (checkAndRequestPermissions()) {
                //If you have already permitted the permission
                initStart(2000);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initStart(0);
    }

    public void startMainActivity(int delayTime) {
        makeNewGpsService();


        // 현재 스플래쉬가 view 방식으로 되어있어 임시 구현
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(this, MainActivity.class);
                //Intent intent = new Intent(SplashActivity.this, SyrupMainActivity.class);
                //Intent intent = new Intent(SplashActivity.this, NewSyrupMainActivity.class);
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("latitude", GPSData.LATITUDE);
                intent.putExtra("longitude",GPSData.LONGITUDE);

                BaseApplication.getInstance().ActivityStart(intent, null);
                finish();

            }
        }, delayTime);
    }

    public void initStart(int delayTime) {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            isGPSEnabled = false;
        }

        if (isGPSEnabled) {
            startMainActivity(delayTime);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(
                    "정확한 위치를 위해 GPS 활성화가 필요합니다.")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {
                                    // Sent user to GPS settings screen
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    BaseApplication.getInstance().ActivityStart(intent, null);
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    startMainActivity(0);
                                    Toast.makeText(MainSplashActivity.this, "위치 정보를 가져오는데 실패 하였습니다.", Toast.LENGTH_SHORT).show();

                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void makeNewGpsService() {
        Log.d("getgps", "makeNewGpsService");
        if (gpsTracker == null) {
            gpsTracker = GPSTracker.getInstance(this, mHandler);
            gpsTracker.startGetLocation();
        } else {
            gpsTracker.Update();
        }
    }

    public void logPrint(GPSData gps) {
//        GPSData.setLATITUDE(gps.getLatitude());
//        GPSData.LONGITUDE = gps.getLongitude();
    }

    public boolean checkAndRequestPermissions() {
        int permissionFineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCoarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionReadphone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionWriteExternalStroage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionReadExternalStroage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        int permissionReadphoneNumvers = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS);
//        int permissionCallPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
//        int permissionReadSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
//        int permissionReadExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionFineLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionReadphone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionWriteExternalStroage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionReadExternalStroage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(permissionCamera != PackageManager.PERMISSION_GRANTED)
        {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
//        if (permissionReadphoneNumvers != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_NUMBERS);
//        }
//        if (permissionCallPhone != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
//        }
//        if (permissionReadSMS != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
//        }
//        if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted Successfully. Write working code here.
                    initStart(0);
                } else {
                    //You did not accept the request can not use the functionality.
                    Toast.makeText(this, "권한 정보 동의에 실패하여 3초 후에 앱을 종료합니다.", Toast.LENGTH_LONG).show();
                    appKill();
                }
                break;
        }
    }

    private void appKill() {
        gpsTracker.stopUsingGPS();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
