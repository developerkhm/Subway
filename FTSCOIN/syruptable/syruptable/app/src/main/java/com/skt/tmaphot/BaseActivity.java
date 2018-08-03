package com.skt.tmaphot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.activity.FoodAreaListActivity;
import com.skt.tmaphot.activity.area.SelectionAreaActivity;
import com.skt.tmaphot.activity.blog.MyBlogActivity;
import com.skt.tmaphot.activity.blog.MyBlogStoreReviewActivity;
import com.skt.tmaphot.activity.main.coupon.more.CouponListActivity;
import com.skt.tmaphot.activity.main.hotdeal.more.HotdealListActivity;
import com.skt.tmaphot.activity.main.review.more.RealReviewActivity;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;
import com.skt.tmaphot.activity.review.ReviewWriteActivity;
import com.skt.tmaphot.activity.search.SearchActivity;
import com.skt.tmaphot.location.GPSData;
import com.tsengvn.typekit.TypekitContextWrapper;

public class BaseActivity extends AppCompatActivity {

    public Context baceContext;
    public TextView topAppbarText;
    protected Toolbar toolbar;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d("ABCDE", "BaseActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ABCDE", "BaseActivity onStart");
        topAppbarText = (TextView) findViewById(R.id.appbar_location_txt);

        if (baceContext != null) {
            if (baceContext instanceof MainActivity
                    || baceContext instanceof CouponListActivity
                    || baceContext instanceof RealReviewActivity
                    || baceContext instanceof HotdealListActivity
                    || baceContext instanceof StoreInfoActivity) {
                topAppbarText.setText(GPSData.LOCATION_ADDRESS);
                Log.d("LOCATION", "GPS : " + GPSData.LOCATION_ADDRESS);
            }

            if (baceContext instanceof SelectionAreaActivity) {
                topAppbarText.setText("지역검색");
            }

            if (baceContext instanceof ReviewWriteActivity) {
                topAppbarText.setText("리뷰작성");
            }

            if (baceContext instanceof FoodAreaListActivity) {
                topAppbarText.setText("검색결과");
            }

            if (baceContext instanceof SearchActivity) {
                topAppbarText.setText("검색");
            }

            if (baceContext instanceof MyBlogActivity) {
                topAppbarText.setText("마이홈");
            }

            if (baceContext instanceof MyBlogStoreReviewActivity) {
                topAppbarText.setText("리뷰보기");
            }
            if (baceContext instanceof ReviewWriteActivity) {
                topAppbarText.setText("리뷰작성");
            }
        } else {

//            if (topAppbarText != null) {
//                topAppbarText.setText(GPSData.LOCATION_ADDRESS);
//                Log.d("BASE", "GPS : " + GPSData.LOCATION_ADDRESS);
//            }
        }

        if (toolbar != null) {
            Log.d("GOOD", "good good good");

            if(!(baceContext instanceof MainActivity))
            {
                setSupportActionBar(toolbar);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
            }

            toolbar.setOnClickListener(onClickListenerLocationAddres);
        }
    }

    private View.OnClickListener onClickListenerLocationAddres = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(baceContext, "클릭!!!!!!!!", Toast.LENGTH_SHORT).show();

            if (baceContext instanceof MainActivity) {
                    MainApplication.ActivityStart(new Intent(BaseActivity.this, SelectionAreaActivity.class), null);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("ABCDE", "BaseActivity onCreateOptionsMenu");

        if (baceContext != null) {
            if (baceContext instanceof MainActivity) {
                getMenuInflater().inflate(R.menu.appbar_main_menu, menu);
            }

            if (baceContext instanceof SelectionAreaActivity
                    || baceContext instanceof FoodAreaListActivity
                    || baceContext instanceof CouponListActivity
                    || baceContext instanceof RealReviewActivity
                    || baceContext instanceof HotdealListActivity
                    || baceContext instanceof StoreInfoActivity) {
                getMenuInflater().inflate(R.menu.appbar_main_map_menu, menu);
            }

        } else {
//            getMenuInflater().inflate(R.menu.appbar_main_menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            if(baceContext instanceof Activity){
                ((Activity)baceContext).finish();
            }
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
        Log.d("ABCDE", "BaseActivity onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

