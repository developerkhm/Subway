package com.skt.tmaphot.activity.main.hotdeal.more;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class HotdealListActivity extends BaseActivity {

    private Handler handler = new Handler();

    private RecyclerView recyclerView;
    private HotdealListRecyclerViewDataAdapter adapter;
    private List<HotdealListRecyclerViewItem> hotdealListRecyclerViewItemList;
    private LinearLayoutManager layoutManager;

    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotdeal_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        //////////////////////////////////////////////////////////


        getLoadDate(); //dummy


        recyclerView = (RecyclerView) findViewById(R.id.hotdeal_list_recyclerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HotdealListRecyclerViewDataAdapter(this, hotdealListRecyclerViewItemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

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

    private void getLoadDate(){

        if(hotdealListRecyclerViewItemList == null)
            hotdealListRecyclerViewItemList = new ArrayList<HotdealListRecyclerViewItem>();

        hotdealListRecyclerViewItemList.add(new HotdealListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        hotdealListRecyclerViewItemList.add(new HotdealListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        hotdealListRecyclerViewItemList.add(new HotdealListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        hotdealListRecyclerViewItemList.add(new HotdealListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        hotdealListRecyclerViewItemList.add(new HotdealListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));
        hotdealListRecyclerViewItemList.add(new HotdealListRecyclerViewItem(
                "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                "황제짜장", "수제피자", "200m", "50%"));

    };
}


