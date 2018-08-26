package com.skt.tmaphot;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.location.GPSTracker;

import java.util.ArrayList;
import java.util.List;

public class MainSplashActivity extends AppCompatActivity {

    private Context mContext;
    private final int PERMISSIONS_ALL_REQUEST = 0;
    private final int PERMISSIONS_GPS_FAIL = 1;
    private final int RESULT_GSP_SETTING_ENABLE = 2;

    private final int REQUEST_ID_MULTIPLE_PERMISSIONS = 11;

    //GPS
    private LocationManager locationManager;
    private GPSTracker gpsTracker;
    private boolean isGPSEnabled;
    private boolean isGspEnableAgree = true;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   //화면 꺼짐 방지

//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT /* layout_width */, ViewGroup.LayoutParams.MATCH_PARENT);
//        ImageView imageView = new ImageView(mContext);
//        imageView.setLayoutParams(layoutParams);
//        imageView.setBackgroundColor(getResources().getColor(R.color.colorGreen));
//        imageView.setImageResource(R.drawable.img_splash_logo);
        setContentView(R.layout.activity_splash_layout);
        ContentLoadingProgressBar contentLoadingProgressBar = (ContentLoadingProgressBar) findViewById(R.id.splash_progressbar);
//        contentLoadingProgressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, android.graphics.PorterDuff.Mode.MULTIPLY);
        contentLoadingProgressBar.setVisibility(View.VISIBLE);
        contentLoadingProgressBar.setIndeterminate(true);

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

        init();
    }

    private void init() {
        // 네트워크 확인
        if (BaseApplication.getInstance().isNetworkConnected(this)) {
            // GPS 활성화
            if (checkGpsEnable()) {
                if (Build.VERSION.SDK_INT < 23) {
                    startGpsService();
                } else {
                    if (checkAndRequestPermissions(PERMISSIONS_ALL_REQUEST)) {
                        startGpsService();
                    }
                }
            }
        }

    }

    private void startMainActivity() {
        Log.d("getgps", "startMainActivity");
//        Intent intent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(getBaseContext(), LoadingActivity.class);
        intent.putExtra(BaseApplication.ACTIVITY_KEY, LoadingActivity.MAINACTIVITY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);   // no history
        startActivity(intent);
//        BaseApplication.getInstance().ActivityStart(intent, null);
    }

    public boolean checkGpsEnable() {
        Log.d("99T", "checkGpsEnable");
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception ex) {
            isGPSEnabled = false;
        }

        if (!isGPSEnabled) {

            new MaterialStyledDialog.Builder(this)
//                    .setTitle(R.string.app_name)
                    .setDescription("정확한 위치 정보를 위해 GPS 활성화가 필요합니다.")
//                    .setStyle(Style.HEADER_WITH_TITLE)
//                    .setHeaderColor(R.color.colorBlack)
                    .setHeaderColor(R.color.text_gray_d4)
                    .setHeaderDrawable(R.drawable.ic_sms_failed)
                    .setHeaderScaleType(ImageView.ScaleType.FIT_CENTER)
                    .setPositiveText("네")
                    .withDialogAnimation(true)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            startActivityForResult(intent, RESULT_GSP_SETTING_ENABLE);
                        }
                    })
                    .setNegativeText("아니요")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            isGspEnableAgree = false;
                            CommonUtil.getInstance().custom_toast(mContext, "위치 정보 활성화에 실패 하였습니다.");
                            retryGpsEnablePopup(); // 종료할건지????
                        }
                    })
                    .show();

//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(
//                    "정확한 위치를 위해 GPS 활성화가 필요합니다.")
//                    .setPositiveButton("예",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
//                                    startActivityForResult(intent, RESULT_GSP_SETTING_ENABLE);
//                                }
//                            })
//                    .setNegativeButton("아니요",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    isGspEnableAgree = false;
//                                    dialog.cancel();
//                                    CommonUtil.getInstance().custom_toast(mContext, "위치 정보 활성화에 실패 하였습니다.");
//                                    retryGpsEnablePopup(); // 종료할건지????
//                                }
//                            });
//            AlertDialog alert = builder.create();
//            alert.show();
        }

        return isGPSEnabled;
    }

    public void startGpsService() {
        Log.d("getgps", "makeNewGpsService");
        if (gpsTracker == null) {
            gpsTracker = GPSTracker.getInstance(this, mHandler);
        }
        gpsTracker.startGetLocation();
    }

    private void retryGpsEnablePopup() {

        new MaterialStyledDialog.Builder(this)
//                .setTitle(R.string.app_name)
                .setDescription("종료 하시겠습니까?  \n \"아니요\" 위치정보 설정 화면으로 이동 \n \"네\" 종료")
//                .setStyle(Style.HEADER_WITH_TITLE)
//                .setHeaderColor(R.color.colorBlack)
                .setHeaderColor(R.color.text_gray_d4)
                .setHeaderDrawable(R.drawable.ic_sms_failed)
                .setHeaderScaleType(ImageView.ScaleType.FIT_CENTER)
                .setPositiveText("네")
                .withDialogAnimation(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .setNegativeText("아니요")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        startActivityForResult(intent, RESULT_GSP_SETTING_ENABLE);
                    }
                })
                .show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(
//                "종료 하시겠습니까? \"아니요\" 선택시 위치정보 설정 화면으로 이동, \"예\"를 선택하면 종료 됩니다.")
//                .setPositiveButton("예",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                finish();
//                            }
//                        })
//                .setNegativeButton("아니요",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                                startActivityForResult(intent, RESULT_GSP_SETTING_ENABLE);
//                            }
//                        });
//        AlertDialog alert = builder.create();
//        alert.show();
    }

    private void retryGpsSettingPopup() {

        new MaterialStyledDialog.Builder(this)
//                .setTitle(R.string.app_name)
                .setDescription("위치 정보 권한이 필요합니다.\n \"아니요\" 앱을 종료\n \"동의\" 설정화면 이동\n (화면 하단 \'권한\'설정, \'위치 정보\'를 활성화)")
//                .setStyle(Style.HEADER_WITH_TITLE)
//                .setHeaderColor(R.color.colorBlack)
                .setHeaderColor(R.color.text_gray_d4)
                .setHeaderDrawable(R.drawable.ic_sms_failed)
                .setHeaderScaleType(ImageView.ScaleType.FIT_CENTER)
                .setPositiveText("네")
                .withDialogAnimation(true)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, RESULT_GSP_SETTING_ENABLE);
                    }
                })
                .setNegativeText("아니요")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(
//                "위치 정보 권한이 필요합니다. \"아니요\" 선택시 앱을 종료합니다. \"동의\" 선택시 설정화면 이동합니다. 화면 하단에 \"권한\"을 터치하여 \"위치 정보\"를 활성화 해주세요.")
//                .setPositiveButton("예",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
//                                startActivityForResult(intent, RESULT_GSP_SETTING_ENABLE);
//                            }
//                        })
//                .setNegativeButton("아니요",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                finish();
//                            }
//                        });
//        AlertDialog alert = builder.create();
//        alert.show();
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

        if (requestCode == PERMISSIONS_ALL_REQUEST) {

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
            Log.d("99T", "checkAndRequestPermissions false");
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        Log.d("99T", "checkAndRequestPermissions true");
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_GSP_SETTING_ENABLE)
            init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:

                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];

                    if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            init();
                            return;
                        } else {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                                    // Permission이 허가되지 않은 경우의 처리를 적는다
                                    checkAndRequestPermissions(PERMISSIONS_GPS_FAIL);
                                } else {
                                    // "다시 묻지 않기"를 설정했다면 그 부분의 처리로 온다
                                    retryGpsSettingPopup();
                                }
                            } else {
                                checkAndRequestPermissions(PERMISSIONS_GPS_FAIL);
                            }

                            return;
                        }
                    }
                }
                break;
        }
    }
}
