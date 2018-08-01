package com.skt.tmaphot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.skt.tmaphot.activity.area.SelectionAreaActivity;
import com.skt.tmaphot.location.GPSData;
import com.tsengvn.typekit.TypekitContextWrapper;

public class BaseActivity extends AppCompatActivity {

    protected TextView locationAddress;
    protected Toolbar toolbar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();

        locationAddress = (TextView) findViewById(R.id.appbar_location_txt);
        if (locationAddress != null) {
            locationAddress.setText(GPSData.LOCATION_ADDRESS);
            Log.d("BASE", "GPS : " + GPSData.LOCATION_ADDRESS);
        }

        if (toolbar != null)
            toolbar.setOnClickListener(onClickListenerLocationAddres);
    }

    private View.OnClickListener onClickListenerLocationAddres = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            MainApplication.ActivityStart(new Intent(BaseActivity.this, SelectionAreaActivity.class), null);


            switch (view.getId()) {

            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.syrup_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_map) {
            return true;
        }

        if (id == R.id.action_alarm) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}

