package tmaphot.skt.com.mysyruptable;

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
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tmaphot.skt.com.mysyruptable.Common.PermissionList;
import tmaphot.skt.com.mysyruptable.location.GPSData;
import tmaphot.skt.com.mysyruptable.location.GPSTracker;

public class SplashActivity extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    //GPS
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    public Handler mHandler;
    GPSTracker gps = null;

    public static Double dLatitude ;  // 현재위치 경도
    public static Double dLongitude ; // 현재위치 위도


    private String TAG = "gps";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MainActivity.RENEW_GPS) {
                    makeNewGpsService();
                }
                if (msg.what == MainActivity.SEND_PRINT) {
                    logPrint((GPSData) msg.obj);
                }
            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            //Do not need to check the permission
        } else {
            if (new PermissionList(this).checkAndRequestPermissions() ) {
                //If you have already permitted the permission
                startMainActivity();
            }
        }

//        checkAndRequestPermissions();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startMainActivity();
    }

    private void startMainActivity() {
        makeNewGpsService();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void initStart() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {

        }

        if (isGPSEnabled) {
            startMainActivity();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(
                    "Your GPS module is disabled. Would you like to enable it ?")
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
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void appStop() {

    }
//
//    private void checkAndRequestPermissions() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(
//                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    ||checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
//                    != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS)
//                    != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.CALL_PHONE)
//                    != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.READ_SMS)
//                    != PackageManager.PERMISSION_GRANTED
//                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED
//                    ) {
//                // Should we show an explanation?
//                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
//                    // Explain to the user why we need to write the permission.
//                    Toast.makeText(this, "gps", Toast.LENGTH_SHORT).show();
//                }
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
//                                Manifest.permission.READ_PHONE_STATE,
//                                Manifest.permission.READ_PHONE_NUMBERS,
//                                Manifest.permission.CALL_PHONE,
//                                Manifest.permission.READ_SMS,
//                                Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_ID_MULTIPLE_PERMISSIONS);
//            } else {   // 다음 부분은 항상 허용일 경우에 해당
//                initStart();
//            }
//        } else {   // SDK < 22
//            initStart();
//        }
//    }

    private boolean checkAndRequestPermissions() {
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


        if (!listPermissionsNeeded.isEmpty()) { ActivityCompat.requestPermissions(this,
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
                    initStart();
                    //Permission Granted Successfully. Write working code here.
                } else {
                    //You did not accept the request can not use the functionality.
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }
                break;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_ID_MULTIPLE_PERMISSIONS:
//
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    initStart();
//                } else {
//                    int pid = android.os.Process.myPid();
//                    android.os.Process.killProcess(pid);
//                }
//                break;
//        }
//    }

    public void makeNewGpsService(){
        if(gps == null) {
            gps = new GPSTracker(this, mHandler);
        }else{
            gps.Update();
        }
    }

    public void logPrint(GPSData gps){
        //androidBridge.setMessage(str);
        //mWebView.loadUrl("javascript:setLocation('"+str+"')");

        this.dLatitude = gps.getLatitude();
        this.dLongitude = gps.getLongitude();

        Log.d("gps", "위도: " + dLatitude);
        Log.d("gps", "경도: " + dLongitude);

        //String str = this.dLatitude.toString()+":"+this.dLongitude.toString();
        //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }
}
