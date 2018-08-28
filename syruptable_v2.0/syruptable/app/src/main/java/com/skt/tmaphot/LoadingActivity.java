package com.skt.tmaphot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

//        BaseApplication.getInstance().progressON(this, "");

        BaseApplication.getInstance().ActivityStart(new Intent(this, MainActivity.class), null);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
