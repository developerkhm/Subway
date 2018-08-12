package com.skt.tmaphot;

import android.app.Activity;
import android.app.ActivityManager;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.activity.HotPlaceResultActivity;
import com.skt.tmaphot.activity.area.SelectionAreaActivity;
import com.skt.tmaphot.activity.blog.MyBlogActivity;
import com.skt.tmaphot.activity.blog.MyBlogStoreReviewActivity;
import com.skt.tmaphot.activity.main.coupon.more.CouponListActivity;
import com.skt.tmaphot.activity.main.hotdeal.more.HotdealListActivity;
import com.skt.tmaphot.activity.main.review.more.RealReviewActivity;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;
import com.skt.tmaphot.activity.main.store.review.ReviewTotalActivity;
import com.skt.tmaphot.activity.main.store.review.SocialReviewTotalActivity;
import com.skt.tmaphot.activity.review.ReviewWriteActivity;
import com.skt.tmaphot.activity.review.multiple.activities.AlbumSelectActivity;
import com.skt.tmaphot.activity.review.multiple.activities.ImageSelectActivity;
import com.skt.tmaphot.activity.search.SearchActivity;
import com.skt.tmaphot.location.GPSData;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.List;

public class
BaseActivity extends AppCompatActivity {
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
        getTopActivity();
        Log.d("ABCDE", "BaseActivity onStart");

        if (baceContext != null) {

            if (toolbar != null) {

                if (!(baceContext instanceof MainActivity)) {
                    setSupportActionBar(toolbar);
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    actionBar.setHomeButtonEnabled(true);
                    actionBar.setDisplayShowTitleEnabled(false);
                }

                topAppbarText = (TextView) findViewById(R.id.appbar_location_txt);
                topAppbarText.setText(GPSData.getInstance().gpsTransferAddress(baceContext));
                Log.d("getgps", "BaseActivity onStart GPS: " + GPSData.getInstance().getLocation_address());

                if (baceContext instanceof SelectionAreaActivity) {
                    topAppbarText.setText("지역검색");
                }

                if (baceContext instanceof ReviewWriteActivity) {
                    topAppbarText.setText("리뷰작성");
                }

                if (baceContext instanceof HotPlaceResultActivity) {
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

                if (baceContext instanceof AlbumSelectActivity) {
                    topAppbarText.setText("앨범선택");
                }

                if (baceContext instanceof ImageSelectActivity) {
                    topAppbarText.setText("사진선택");
                }

                if (baceContext instanceof ReviewTotalActivity) {
                    topAppbarText.setText("리뷰");
                }

                if (baceContext instanceof SocialReviewTotalActivity) {
                    topAppbarText.setText("소셜리뷰");
                }

                toolbar.setOnClickListener(onClickListenerLocationAddres);
            }
        } else {


        }
    }

    private View.OnClickListener onClickListenerLocationAddres = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (baceContext instanceof MainActivity) {
                Log.d("getgps", "BacsStartActivity");
                ActivityStart(new Intent(BaseActivity.this, SelectionAreaActivity.class), null);
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
                    || baceContext instanceof HotPlaceResultActivity
                    || baceContext instanceof CouponListActivity
                    || baceContext instanceof RealReviewActivity
                    || baceContext instanceof HotdealListActivity
                    || baceContext instanceof StoreInfoActivity) {
                getMenuInflater().inflate(R.menu.appbar_main_map_menu, menu);
            }

            if (baceContext instanceof ImageSelectActivity) {
                getMenuInflater().inflate(R.menu.appbar_review_select_menu, menu);
            }

            if (baceContext instanceof ReviewWriteActivity) {
                getMenuInflater().inflate(R.menu.appbar_main_review_write_menu, menu);
            }

        } else {
//            getMenuInflater().inflate(R.menu.appbar_main_menu, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (baceContext instanceof Activity) {
                ((Activity) baceContext).finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.action_map) {
            ActivityStart(new Intent(baceContext, MapWebViewActivity.class), null);
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

    public void getTopActivity() {
        ActivityManager manager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = manager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo info = list.get(0);

        Log.d("aa", info.topActivity.getClassName());

//        try {
//            Class top_Class = Class.forName(info.topActivity.getClassName());
//            Log.d("Top_Class", "" + top_Class);
//
//
//
//        } catch (ClassNotFoundException e) { e.printStackTrace(); }


//        if(info.topActivity.getClassName().equals("com.example.test7_20.Lab4Activity")){
//
//        }else {
//
//        }
    }

    public void progressON() {
        BaseApplication.getInstance().progressON(this, null);
    }

    public void progressON(String message) {
        BaseApplication.getInstance().progressON(this, message);
    }

    public void progressOFF() {
        BaseApplication.getInstance().progressOFF();
    }

    public void ActivityStart(Intent intent, Bundle bundle) {
        BaseApplication.getInstance().ActivityStart(intent, bundle);
    }

    public void loadImage(Context context, Object res, ImageView view, boolean isRound) {
        BaseApplication.getInstance().loadImage(context, res, view, isRound);
    }
}

