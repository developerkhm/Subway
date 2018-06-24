package com.subway.rico.sinchonsubway;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.subway.rico.sinchonsubway.common.CommonUtil;
import com.subway.rico.sinchonsubway.exit.ExitActivity;
import com.subway.rico.sinchonsubway.hood.HoodActivity;
import com.subway.rico.sinchonsubway.station.StationActivity;

import org.apache.log4j.Logger;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 112;
    private boolean isAuto = true;
    private LogConfigurator logConfigurator;
    private Handler mHandler = new Handler();
    private String mServicePackageName = "com.subway.rico.sinchonsubway.LauncherService";

    private AutoRun mAutorun;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.subway.rico.sinchonsubway.R.layout.main_activity);
        checkPermission();
//        setReceiver();
        initStart();
    }

    private void initStart() {
        CommonUtil.getInstance().MainActivity = MainActivity.this;

        Button btn1 = (Button) findViewById(com.subway.rico.sinchonsubway.R.id.st_button1);
        Button btn2 = (Button) findViewById(com.subway.rico.sinchonsubway.R.id.st_button2);
        Button btn3 = (Button) findViewById(com.subway.rico.sinchonsubway.R.id.st_button3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAuto = false;
                savePreferences(1);

                Intent intent = new Intent(MainActivity.this, StationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAuto = false;
                savePreferences(2);

                Intent intent = new Intent(MainActivity.this, HoodActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAuto = false;
                savePreferences(3);

                Intent intent = new Intent(MainActivity.this, ExitActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    // 값 불러오기
    private int getPreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getInt("state", 1);
    }

    // 값 저장하기
    private void savePreferences(int state) {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("state", state);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    private void removePreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("hi");
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    private void removeAllPreferences() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onBackPressed() {

    }

//    private void requestAppPermissions() {
//        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            return;
//        }
//
//        if (hasReadPermissions() && hasWritePermissions()) {
//            return;
//        }
//
//        ActivityCompat.requestPermissions(this,
//                new String[] {
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                }, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
//    }
//
//    private boolean hasReadPermissions() {
//        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
//    }
//
//    private boolean hasWritePermissions() {
//        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
//    }

    /**
     * Permission check.
     */
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

//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        REQUEST_WRITE_STORAGE_REQUEST_CODE);

                // MY_PERMISSION_REQUEST_STORAGE is an
                // app-defined int constant

            } else {
                // 다음 부분은 항상 허용일 경우에 해당이 됩니다.
                initLogwite();
                autoStart();
                startService();
            }
        } else {
            initLogwite();
            autoStart();
            startService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    initLogwite();
                    autoStart();
                    startService();
                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);

//                    Log.d(TAG, "Permission always deny");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private void initLogwite() {
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
                if (!isAuto)
                    return;

                switch (getPreferences()) {
                    case 1:
                        Intent intent = new Intent(MainActivity.this, StationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, HoodActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, ExitActivity.class);
                        intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent3);
                        break;
                    default:
                }
            }
        }, 5000);
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

//    private void setReceiver() {
//        mAutorun = new AutoRun();
//        IntentFilter filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
//        this.registerReceiver(mAutorun, filter);
//    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isAuto = true;
        autoStart();
    }
}
