package com.skt.tmaphot.activity.main.coupon.more;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CouponListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private CouponListRecyclerViewDataAdapter adapter;
    private List<CouponListRecyclerViewItem> couponListRecyclerViewItemList;
    private LinearLayoutManager layoutManager;

    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        locationTextview = (TextView) findViewById(R.id.appbar_location_txt);
        locationTextview.setText(MainApplication.LOCATION_ADDRESS);
        //////////////////////////////////////////////////////////

        getLoadDate(); //dummy

        recyclerView = (RecyclerView) findViewById(R.id.main_coupon_list_recyclerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CouponListRecyclerViewDataAdapter(this, couponListRecyclerViewItemList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));

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

    private void getLoadDate() {

        if (couponListRecyclerViewItemList == null)
            couponListRecyclerViewItemList = new ArrayList<CouponListRecyclerViewItem>();


        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "https://png.pngtree.com/element_origin_min_pic/16/10/25/ee6d5e601c9d19fc98dc4add819b9c77.jpg",
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
        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            outRect.left = space;
//            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
//            if(parent.getChildAdapterPosition(view) == 0) {
//                outRect.top = space;
//            }
        }
    }
}


