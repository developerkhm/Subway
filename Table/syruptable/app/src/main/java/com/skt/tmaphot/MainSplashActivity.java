package com.skt.tmaphot;

import android.Manifest;
import android.app.Activity;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.skt.tmaphot.activity.review.multiple.models.Image;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.location.GPSTracker;

import java.util.ArrayList;
import java.util.List;

public class MainSplashActivity extends AppCompatActivity {

    private Context mContext;
    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    //GPS
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    GPSTracker gpsTracker;
    private String TAG = "gps";

    private Handler mHandler;

    @Override
    protected void onStart() {
        super.onStart();

        if (BaseApplication.getInstance().isNetworkConnected(this))  // 네트워크 확인
        {
            if (Build.VERSION.SDK_INT < 23) {
                //Do not need to check the permission
                initStart();
            } else {
                if (checkAndRequestPermissions(0)) {
                    //If you have already permitted the permission
                    initStart();
                }
            }
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //화면 꺼짐 방지

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT /* layout_width */, ViewGroup.LayoutParams.MATCH_PARENT );
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        imageView.setImageResource(R.drawable.img_splash_logo);
        setContentView(imageView);


        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int message = msg.what;

//            if (message == GPSTracker.getInstance().LOCATION_SUCCESS
//                    || msg.what == GPSTracker.getInstance().LOCATION_LASTKNOWN
//                    || msg.what == GPSTracker.getInstance().LOCATION_UPDATE)
                if (message == GPSTracker.getInstance().LOCATION_SUCCESS) {
                    Log.d("getgps", "handler LOCATION_SUCCESS");
                    startMainActivity();
                }

                if (message == GPSTracker.getInstance().LOCATION_UPDATE) {
                    Log.d("getgps", "handler LOCATION_UPDATE");

                    startMainActivity();
                }

                if (message == GPSTracker.getInstance().LOCATION_FAIL) {
                    Log.d("getgps", "handler LOCATION_FAIL");
                    GPSTracker.getInstance().failGps(mContext);
                }

            }
        };


    }

    private void startMainActivity() {
        Log.d("getgps", "startMainActivity");
        BaseApplication.getInstance().ActivityStart(new Intent(this, MainActivity.class), null);
    }




    @Override
    protected void onRestart() {
        super.onRestart();
        initStart();
    }


    public void initStart() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            isGPSEnabled = false;
        }

        if (isGPSEnabled) {
            initGpsService();
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
//                                    initGpsService();
                                    Toast.makeText(MainSplashActivity.this, "위치 정보를 가져오는데 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 2000);

                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void initGpsService() {
        Log.d("getgps", "makeNewGpsService");
        if (gpsTracker == null) {
            gpsTracker = GPSTracker.getInstance(this, mHandler);
        }
        gpsTracker.startGetLocation();
    }

    public boolean checkAndRequestPermissions(int requestCode) {
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

        if (requestCode == 0) {

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
            if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
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
        }

        if (requestCode == 1) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
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

                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION) || permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            initStart();
                            return;
                        } else {
                            checkAndRequestPermissions(1);
                            return;
                        }
                    }
                }

                initStart();


//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //Permission Granted Successfully. Write working code here.
//                    initStart();
//                } else {
//                    initStart();
//                }
//                break;
        }
    }
}
