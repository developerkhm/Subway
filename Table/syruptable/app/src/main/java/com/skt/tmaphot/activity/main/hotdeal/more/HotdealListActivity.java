package com.skt.tmaphot.activity.main.hotdeal.more;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.hotdeal.HotdealRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.hotdeal.HotdealRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HotdealListActivity extends BaseActivity {

    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private HotdealRecyclerViewDataAdapter adapter;
    private List<HotdealRecyclerViewItem> hotdealListRecyclerViewItemList;
    private LinearLayoutManager layoutManager;
    private boolean loading = true;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotdeal_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (hotdealListRecyclerViewItemList == null)
            hotdealListRecyclerViewItemList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.hotdeal_list_recyclerview);
        layoutManager = new LinearLayoutManager(baceContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HotdealRecyclerViewDataAdapter(hotdealListRecyclerViewItemList, HotdealRecyclerViewDataAdapter.HOTDEAL_ITEM_MORE_LAYOUT);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
//        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 40);

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
                            getLoadDate();
                        }
                    }
                }
            }
        });

        getLoadDate(); //dummy
    }

    private void getLoadDate() {

        loading = false;

        if (hotdealListRecyclerViewItemList == null)
            hotdealListRecyclerViewItemList = new ArrayList<>();

        int currItemListSize = 0;   // 현재 리스트 사이즈
        int newItemIndex = 0;       // 아이템 끝 index
        int loadMoreItemCount = 10;  // 로드할 아이템 개수

        currItemListSize = hotdealListRecyclerViewItemList.size();

        for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {

            String url = "http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg";
            hotdealListRecyclerViewItemList.add(new HotdealRecyclerViewItem(String.valueOf(i), url, "3", "피자", "불고기피자", "300", "20"));
        }

        newItemIndex = hotdealListRecyclerViewItemList.size() - 1;
        adapter.reLoadData(hotdealListRecyclerViewItemList);
        loading = true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}


