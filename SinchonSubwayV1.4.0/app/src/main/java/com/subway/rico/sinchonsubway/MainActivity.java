package com.subway.rico.sinchonsubway;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.subway.rico.sinchonsubway.common.CommonUtil;
import com.subway.rico.sinchonsubway.exit.ExitActivity;
import com.subway.rico.sinchonsubway.hood.HoodActivity;
import com.subway.rico.sinchonsubway.station.StationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 112;
    private boolean isAuto = true;
    private LogConfigurator logConfigurator;
    private Handler mHandler = new Handler();
    private String mServicePackageName = "com.subway.rico.sinchonsubway.LauncherService";

    @BindView(R.id.st_button1)
    Button btn1;
    @BindView(R.id.st_button2)
    Button btn2;
    @BindView(R.id.st_button3)
    Button btn3;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.subway.rico.sinchonsubway.R.layout.main_activity);
        checkPermission();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isAuto = true;
        autoStart();
    }

    @Override
    public void onBackPressed() {
    }

    private void initStart() {
        CommonUtil.getInstance().MainActivity = MainActivity.this;
        ButterKnife.bind(this);
        initLogwrite();
        startService();
        autoStart();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
                }
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
            } else {   // 다음 부분은 항상 허용일 경우에 해당
                initStart();
            }
        } else {
            initStart();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! do the
                    //                    // calendar task you need to do.
                    initStart();
                } else {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private void initLogwrite() {
        try {
            logConfigurator = new LogConfigurator();
            logConfigurator.setFileName(Environment.getExternalStorageDirectory() + "/Subway/Logs/logFile.log");
            logConfigurator.configure();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    private void autoStart() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAuto) return;

                switch (CommonUtil.getInstance().getPreferences()) {
                    case 1:
                        btn1.performClick();
                        break;
                    case 2:
                        btn2.performClick();
                        break;
                    case 3:
                        btn3.performClick();
                        break;
                }
            }
        }, 5000);
    }

    @OnClick({R.id.st_button1, R.id.st_button2, R.id.st_button3})
    public void onClick(View v) {
        isAuto = false;

        Class startClass = null;
        switch (v.getId()) {
            case R.id.st_button1:
                CommonUtil.getInstance().savePreferences(1);
                startClass = StationActivity.class;
                break;
            case R.id.st_button2:
                CommonUtil.getInstance().savePreferences(2);
                startClass = HoodActivity.class;
                break;
            case R.id.st_button3:
                CommonUtil.getInstance().savePreferences(3);
                startClass = ExitActivity.class;
                break;
        }

        Intent intent = new Intent(MainActivity.this, startClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void startService() {
        // 서비스 시작하기
        Log.d("subway-service", "Service Start call");
        if (!isServiceRunningCheck()) {
            startService(new Intent(getApplicationContext(), LauncherService.class));
        }
    }

    private void stopService() {
        // 서비스 종료하기
        Log.d("subway-service", "Service Stop call");
        Intent intent = new Intent(getApplicationContext(), LauncherService.class);
        stopService(intent);
    }

    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (mServicePackageName.equals(service.service.getClassName())) {
                Log.d("subway-service", "Service Already Running");
                return true;
            }
        }
        Log.d("subway-service", "Service Not Running");
        return false;
    }
}
