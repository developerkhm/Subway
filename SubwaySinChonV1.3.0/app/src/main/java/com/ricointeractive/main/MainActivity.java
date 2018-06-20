package com.ricointeractive.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.exit.sinchon.ExitActivity;
import com.ricointeractive.hood.sinchon.HoodActivity;
import com.ricointeractive.station.sinchon.StationActivity;
import com.ricointeractive.subwaydemosinchonstationmap.R;

import org.apache.log4j.Logger;

import de.mindpipe.android.logging.log4j.LogConfigurator;

public class MainActivity extends AppCompatActivity {

    private boolean isAuto = true;
    private LogConfigurator logConfigurator;


    @Override
    protected void onStart() {
        super.onStart();

        initStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(Environment.getExternalStorageDirectory() + "/Subway/Logs/logFile.log");
        logConfigurator.configure();

        initStart();
    }

    private void initStart() {
        Logger logger = Logger.getLogger(MainActivity.class.getSimpleName());
        logger.info(MainActivity.class.getSimpleName() + " start");
        CommonUtil.getInstance().MainActivity = MainActivity.this;

        Button btn1 = (Button) findViewById(R.id.st_button1);
        Button btn2 = (Button) findViewById(R.id.st_button2);
        Button btn3 = (Button) findViewById(R.id.st_button3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAuto = false;
                savePreferences(1);

                Intent intent = new Intent(MainActivity.this, StationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(new Intent(MainActivity.this, StationActivity.class));
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

        btn1.postDelayed(new Runnable() {
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
        }, 4000);
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

}
