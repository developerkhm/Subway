package com.skt.tmaphot.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.skt.tmaphot.location.GPSData;
import com.skt.tmaphot.location.GPSTracker;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    //GPS
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    public Handler mHandler;
    GPSTracker gps;
    public static int RENEW_GPS = 1;
    public static int SEND_PRINT = 2;

    private String TAG = "gps";
//
//    @BindView(R.id.splash_img)
//    ImageView mainImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.splash_main);
//        ButterKnife.bind(this);
        // 이미지없어서 임시, View로 처리, 나중에 Style 처리, 그래서 임시로 딜레이 시작 페이지
//        CommonUtil.getInstance().mainContex = this;
//        CommonUtil.getInstance().loadImage(this, R.drawable.img_splash_logo, mainImage);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == RENEW_GPS) {
                    makeNewGpsService();
                }
                if (msg.what == SEND_PRINT) {
                    logPrint((GPSData) msg.obj);
                }
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
                //         Intent intent = new Intent(this, MainActivity.class);
//                Intent intent = new Intent(SplashActivity.this, SyrupMainActivity.class);
                Intent intent = new Intent(SplashActivity.this, NewSyrupMainActivity.class);
                intent.putExtra("latitude", GPSData.latitude);
                intent.putExtra("longitude",GPSData.longitude);
                startActivity(intent);
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
                                    startActivity(intent);
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    startMainActivity(0);
                                    Toast.makeText(SplashActivity.this, "위치 정보를 가져오는데 실패 하였습니다.", Toast.LENGTH_SHORT).show();

                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void makeNewGpsService() {
        Log.d("getgps", "makeNewGpsService");
        if (gps == null) {
            gps = new GPSTracker(this, mHandler);
        } else {
            gps.Update();
        }
    }

    public void logPrint(GPSData gps) {
        GPSData.latitude = gps.getLatitude();
        GPSData.longitude = gps.getLongitude();
    }


    public boolean checkAndRequestPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionReadphone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionReadphoneNumvers = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS);
        int permissionCallPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int permissionReadSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int permissionReadExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionReadphone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionReadphoneNumvers != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_NUMBERS);
        }
        if (permissionCallPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (permissionReadSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

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
                    Toast.makeText(this, "권한 정보 동의에 실패하여 3초 후에 앱을 종료합니다.", Toast.LENGTH_SHORT).show();
                    appKill();
                }
                break;
        }
    }

    private void appKill() {
        this.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
