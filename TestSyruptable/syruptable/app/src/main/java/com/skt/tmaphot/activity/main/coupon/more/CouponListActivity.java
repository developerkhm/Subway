package com.skt.tmaphot.activity.main.coupon.more;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.coupon.CouponRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

public class CouponListActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private RecyclerView recyclerView;
    private CouponListRecyclerViewDataAdapter adapter;
    private List<CouponListRecyclerViewItem> couponListRecyclerViewItemList;
    private LinearLayoutManager layoutManager;

    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //////////////////////////////////////////////////////////


        getLoadDate(); //dummy


        recyclerView = (RecyclerView) findViewById(R.id.main_coupon_list_recyclerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CouponListRecyclerViewDataAdapter(this, couponListRecyclerViewItemList);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItem = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!mLoading && lastVisibleItem == totalItem - 1) {
                    mLoading = true;
                    // Scrolled to bottom. Do something here.

                    getLoadDate();

                    adapter.notifyDataSetChanged();

                    mLoading = false;
                }
            }
        });

    } //END


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getLoadDate(){

        if(couponListRecyclerViewItemList == null)
            couponListRecyclerViewItemList = new ArrayList<CouponListRecyclerViewItem>();

        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
    };
}


