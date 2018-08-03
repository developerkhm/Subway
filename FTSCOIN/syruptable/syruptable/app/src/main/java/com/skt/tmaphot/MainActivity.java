package com.skt.tmaphot;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import android.widget.ImageView;

import com.skt.tmaphot.fragment.MainFragment;
import com.skt.tmaphot.location.GPSData;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


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
        initGPSTransferAddress();
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ///////////////////////////////////////init/////////////////////////////////////////////////


        ImageView test = (ImageView)findViewById(R.id.nav_image_profile);
        MainApplication.loadResRoundImage(baceContext, R.drawable.img_default_login_user, test);

        MainFragment mainFragment = new MainFragment();
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragment_content, mainFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ABCDE", "MainActivity onStart");
    }

    @Override
    public void onBackPressed() {

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
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };


    private void initGPSTransferAddress() {
        Intent intent = getIntent();

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

            addresses = gcd.getFromLocation(intent.getDoubleExtra("latitude", 0), intent.getDoubleExtra("longitude", 0), 1);
            String t = null;
            if (addresses.size() > 0)
            {
                if (addresses.get(0).getSubLocality() != null) {
                    t = addresses.get(0).getSubLocality();   //구
                } else {
                    t = addresses.get(0).getLocality();   //시
                }

                String tt = addresses.get(0).getThoroughfare();     //동

                GPSData.LOCATION_ADDRESS = t + " " + tt + " ▼";
//                ((MainActivity)getActivity()).topAppbarText.setText(GPSData.LOCATION_ADDRESS);
//                locationAddress.setText(GPSData.LOCATION_ADDRESS);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
