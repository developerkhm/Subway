package com.skt.tmaphot.activity.main.coupon.more;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.skt.tmaphot.R;
import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.activity.main.coupon.CouponRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.coupon.CouponRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CouponListActivity extends BaseActivity {

    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private CouponRecyclerViewDataAdapter adapter;
    private List<CouponRecyclerViewItem> couponListRecyclerViewItemList;
    private LinearLayoutManager layoutManager;
    private boolean loading = true;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        if (couponListRecyclerViewItemList == null)
            couponListRecyclerViewItemList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.main_coupon_list_recyclerview);
        layoutManager = new LinearLayoutManager(baceContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CouponRecyclerViewDataAdapter(couponListRecyclerViewItemList, CouponRecyclerViewDataAdapter.COUPON_ITEM_MORE_LAYOUT);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);
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

    } //END

    private void getLoadDate() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                loading = false;
                if (couponListRecyclerViewItemList == null)
                    couponListRecyclerViewItemList = new ArrayList<>();

                int currItemListSize = 0;   // 현재 리스트 사이즈
                int newItemIndex = 0;       // 아이템 끝 index
                int loadMoreItemCount = 10;  // 로드할 아이템 개수

                currItemListSize = couponListRecyclerViewItemList.size();
                for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {

                    String url = "http://image.chosun.com/sitedata/image/201701/13/2017011300860_0.jpg";
                    couponListRecyclerViewItemList.add(new CouponRecyclerViewItem(String.valueOf(i), url, "상점이름", "메뉴", "음식종류", "200m", "10", "80,000", "56,000"));
                }

                newItemIndex = couponListRecyclerViewItemList.size() - 1;
                adapter.reLoadData(couponListRecyclerViewItemList);
                loading = true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); //스레드풀 종료
    }
}


