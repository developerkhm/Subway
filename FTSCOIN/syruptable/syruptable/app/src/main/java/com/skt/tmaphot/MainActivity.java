package com.skt.tmaphot;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.activity.bottom.EventFragment;
import com.skt.tmaphot.activity.bottom.MainFragment;
import com.skt.tmaphot.activity.bottom.RealReviewFragment;
import com.skt.tmaphot.location.GPSData;
import com.skt.tmaphot.location.GPSTracker;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private GPSTracker gpsTracker;
    private Handler mMainHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == GPSData.LOCATION_UPDATE) {
                initGpsTransferAddress();
                topAppbarText.setText(GPSData.LOCATION_ADDRESS);
                if(gpsTracker != null)
                    gpsTracker.stopUsingGPS();
            }
        }
    };

    private FragmentManager fragmentManager;
    private  DrawerLayout drawer;
    public BottomNavigationView navigation;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ABCDE", "MainActivity onCreate");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main_layout);
        baceContext = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        topAppbarText = (TextView) findViewById(R.id.appbar_location_txt);
        GPSTracker gpsTracker = GPSTracker.getInstance(baceContext, mMainHandler);
        if(gpsTracker.locationSendCount > 0)
        {
            initGpsTransferAddress();
            topAppbarText.setText(GPSData.LOCATION_ADDRESS);
            gpsTracker.stopUsingGPS();
        }
        fragmentManager = getSupportFragmentManager();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(0);

        ///////////////////////////////////////init/////////////////////////////////////////////////


        ImageView test = (ImageView) findViewById(R.id.nav_image_profile);
        MainApplication.loadResRoundImage(baceContext, R.drawable.img_default_login_user, test);

        fragmentManager.beginTransaction().add(R.id.fragment_content, new MainFragment()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ABCDE", "MainActivity onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ABCDE", "MainActivity onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_alarm:
                return true;

            case R.id.action_map:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:
                break;
            case R.id.nav_login:

                break;
            case R.id.nav_mypage:

                break;
            case R.id.nav_cart:

                break;

            case R.id.nav_noti:

                break;
            case R.id.nav_board:

                break;
            case R.id.nav_review:

                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if(toolbar.getVisibility() == View.GONE){
                toolbar.setVisibility(View.VISIBLE);
            }


            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    fragmentManager.beginTransaction().replace(R.id.fragment_content, new MainFragment()).commit();
                    return true;
                case R.id.navigation_review:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content, new RealReviewFragment()).commit();
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_event:
                    fragmentManager.beginTransaction().replace(R.id.fragment_content, new EventFragment()).commit();
                    toolbar.setVisibility(View.GONE);
                    return true;
            }
            return false;    //원래 false
        }
    };


    private void initGpsTransferAddress() {
        Log.d("getgps", "initGPSTransferAddress Call :");
        Log.d("getgps", "initGPSTransferAddress Call GPSData.LATITUD :" + GPSData.LATITUDE + "  GPSData.LONGITUDE :" + GPSData.LONGITUDE);
        Geocoder gcd = new Geocoder(baceContext, Locale.getDefault());
        List<Address> addresses = null;

        try {


            //        Log.d("GEO", "addresses.get(0).getAdminArea() " + addresses.get(0).getAdminArea());   //동
            //        Log.d("GEO", "addresses.get(0).getSubLocality() " + addresses.get(0).getSubLocality());   //동
            //        Log.d("GEO", "addresses.get(0).getThoroughfare() " + addresses.get(0).getThoroughfare());   //동
            //        Log.d("GEO", "addresses.get(0).getAdminArea() " + addresses.get(0).getAdminArea());   //동
            //        Log.d("GEO", "addresses.get(0).getLocality() " + addresses.get(0).getLocality());   //동
            //        Log.d("GEO", "addresses.get(0).getAddressLine(0).toString() " + addresses.get(0).getAddressLine(0).toString());   //동

                /*
                addresses.get(0).getAdminArea() 경기도
                addresses.get(0).getSubLocality() null
                addresses.get(0).getThoroughfare() 덕풍동
                addresses.get(0).getAdminArea() 경기도
                addresses.get(0).getLocality() 하남시
                addresses.get(0).getAddressLine(0).toString() 대한민국 경기도 하남시 덕풍동 산62-3
                */

            addresses = gcd.getFromLocation(GPSData.LATITUDE, GPSData.LONGITUDE, 1);

            String t = null;
            if (addresses.size() > 0) {
                if (addresses.get(0).getSubLocality() != null) {
                    t = addresses.get(0).getSubLocality();   //구
                } else {
                    t = addresses.get(0).getLocality();   //시
                }

                String tt = addresses.get(0).getThoroughfare();     //동
                Log.d("getgps", "initGPSTransferAddress addresses GPSData.LATITUD :" + GPSData.LATITUDE + "  GPSData.LONGITUDE :" + GPSData.LONGITUDE);

                GPSData.LOCATION_ADDRESS = t + " " + tt + " ▼";
                Log.d("getgps", "initGPSTransferAddress LOCATION_ADDRESS :" + GPSData.LOCATION_ADDRESS);
//                ((MainActivity)getActivity()).topAppbarText.setText(GPSData.LOCATION_ADDRESS);
//                locationAddress.setText(GPSData.LOCATION_ADDRESS);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;

            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                backPressedTime = tempTime;
                Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
