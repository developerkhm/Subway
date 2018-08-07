package com.skt.tmaphot.activity.main.hotdeal.more;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.R;

import java.util.ArrayList;
import java.util.List;

public class HotdealListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private HotdealListRecyclerViewDataAdapter adapter;
    private List<HotdealListRecyclerViewItem> hotdealListRecyclerViewItemList;
    private LinearLayoutManager layoutManager;
    private boolean loading = true;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotdeal_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        getLoadDate(); //dummy

        recyclerView = (RecyclerView) findViewById(R.id.hotdeal_list_recyclerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HotdealListRecyclerViewDataAdapter(this, hotdealListRecyclerViewItemList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (loading) {
                    if (dy > 0) //check for scroll down
                    {
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", " Reached Last Item");
                            getLoadDate();

                            loading = true;
                        }

                    }
                }
            }
        });

    }

    private void getLoadDate() {

        if (hotdealListRecyclerViewItemList == null)
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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter != null)
                    adapter.notifyDataSetChanged();
            }
        });

    }
}


