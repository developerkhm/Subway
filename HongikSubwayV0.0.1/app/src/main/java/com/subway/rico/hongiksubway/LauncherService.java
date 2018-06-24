package com.subway.rico.hongiksubway;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;


public class LauncherService extends Service {

    private Thread runningThead;
    private boolean running = true;
    private final String mayPackage = "com.subway.rico.hongiksubway";
    private final int runningTreahdTime = 30000;


    @Override
    public IBinder onBind(Intent intent) {
        // Service 객체와 (화면단 Activity 사이에서)
        // 통신(데이터를 주고받을) 때 사용하는 메서드
        // 데이터를 전달할 필요가 없으면 return null;
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("subway-service", "Service onCreate Init");
        running = true;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행

//        Notification notiEx = new NotificationCompat.Builder(ExampleService.this)
//                .setContentTitle("Title Example")
//                .setContentText("Content Text Example")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .build();

        startForeground(1, new Notification());
        Log.d("subway-service", "Service onStartCommand startForeground");

        runningThead = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Log.i("subway-service", "Service onRunning");

                        Thread.sleep(runningTreahdTime);
                        String[] currentApp = get();
                        if (currentApp != null && currentApp.length != 0) {
                            if (!(currentApp[0].equals(mayPackage))) {
                                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent1);
                                Log.i("subway-service", "Service Restart TopActivity : " + currentApp[0]);
                            }
                        } else {
                            if (currentApp.length == 0) {

                                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent1);
                                Log.i("subway-service", "Service Restart TopActivity None Subway");
                            }
                        }
                    } catch (InterruptedException e) {

                        Log.i("subway-service", "Service Thread interrupted: " + e.getMessage());
                    }
                }
                Log.i("subway-service", "Service stopSelf");
                stopSelf();
            }
        });

        runningThead.start();

//        return super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        // 서비스가 종료될 때 실행
        Log.d("subway-service", "Service onDestroy");
    }


    public String[] get() {
        if (Build.VERSION.SDK_INT < 21)
            return getPreLollipop();
        else
            return getLollipop();
    }

    private String[] getPreLollipop() {
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> tasks =
                activityManager().getRunningTasks(1);
        ActivityManager.RunningTaskInfo currentTask = tasks.get(0);
        ComponentName currentActivity = currentTask.topActivity;
        return new String[]{currentActivity.getPackageName()};
    }

    private String[] getLollipop() {
        final int PROCESS_STATE_TOP = 2;

        try {
            Field processStateField = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");

            List<ActivityManager.RunningAppProcessInfo> processes =
                    activityManager().getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo process : processes) {
                if (
                    // Filters out most non-activity processes
                        process.importance <= ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                                &&
                                // Filters out processes that are just being
                                // _used_ by the process with the activity
                                process.importanceReasonCode == 0
                        ) {
                    int state = processStateField.getInt(process);

                    if (state == PROCESS_STATE_TOP)
                        /*
                         If multiple candidate processes can get here,
                         it's most likely that apps are being switched.
                         The first one provided by the OS seems to be
                         the one being switched to, so we stop here.
                         */
                        return process.pkgList;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return new String[]{};
    }

    private ActivityManager activityManager() {
        return (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
    }
}

