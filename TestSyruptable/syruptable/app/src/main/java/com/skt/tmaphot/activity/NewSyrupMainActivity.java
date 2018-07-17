package com.skt.tmaphot.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.R;
import com.skt.tmaphot.client.SyrupWebChromeClient2;
import com.skt.tmaphot.client.SyrupWebViewClient;
import com.skt.tmaphot.common.AndroidBridge;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NewSyrupMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Geocoder mGeoCoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syrup_main_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//      toolbar.setLogo(R.drawable.logo);
//        ImageView logo = (ImageView) findViewById(R.id.toolbar_logo);
//        logo.setImageResource(R.drawable.test);
//        logo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(NewSyrupMainActivity.this, StoreInfoActivity.class));
////                overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_in_left);
////                finish();
//            }
//        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setElevation(0);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBar.setDisplayHomeAsUpEnabled(true);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ///////init//////////////////
        Intent intent = getIntent();
        String cityName = null;

        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());

        List<Address> addresses = null;

        try {

            addresses = gcd.getFromLocation(intent.getDoubleExtra("latitude",0), intent.getDoubleExtra("longitude",0), 1);

            if (addresses.size() > 0)

//            Log.d("GEO", addresses.get(0).getLocality());   // 구 메인,
//            Log.d("GEO", addresses.get(0).getAddressLine(0).toString());    // 국가, 시 군 구 동 번지
//            Log.d("GEO", addresses.get(0).getAdminArea());  //시
//            Log.d("GEO", addresses.get(0).getThoroughfare());   //동
                

//            String tt = addresses.get(0).getAddressLine(0).toString();
//            String result = tt.substring(tt.star(" "), tt.lastIndexOf(" "));

            Log.d("GEO", addresses.get(0).getAdminArea()+ " " +addresses.get(0).getSubLocality()+ " " +addresses.get(0).getThoroughfare());   //동


        } catch (IOException e) {

            e.printStackTrace();

        }

                String t = addresses.get(0).getSubLocality();
                String tt = addresses.get(0).getThoroughfare();

        TextView mGPSTextView = (TextView)findViewById(R.id.main_text_gps);
        mGPSTextView.setText(t+ " " + tt);


    }//END

    @Override
    public void onBackPressed() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.syrup_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            return true;
        }

        if (id == R.id.action_user) {
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


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
