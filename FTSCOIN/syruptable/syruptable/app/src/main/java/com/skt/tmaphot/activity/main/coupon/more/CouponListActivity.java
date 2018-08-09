package com.skt.tmaphot.activity.main.coupon.more;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.coupon.CouponRecyclerViewDataAdapter2;
import com.skt.tmaphot.net.model.MultipleResource;
import com.skt.tmaphot.net.service.Item;
import com.skt.tmaphot.net.service.StackExchangeManager;
import com.skt.tmaphot.net.service.UsersResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import io.reactivex.subscribers.DefaultSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponListActivity extends BaseActivity {

    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private CouponRecyclerViewDataAdapter2 adapter;
    private List<Item> couponListRecyclerViewItemList;
    private LinearLayoutManager layoutManager;
//    private boolean loading = true;
    private boolean isScrolling = true;
    private int visibleItemCount, totalItemCount, pastVisiblesItems;
    int currentItems, totalItems, scrollOutItems;
    private StackExchangeManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        if (couponListRecyclerViewItemList == null)
            couponListRecyclerViewItemList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.main_coupon_list_recyclerview);
        layoutManager = new LinearLayoutManager(baceContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CouponRecyclerViewDataAdapter2(couponListRecyclerViewItemList, CouponRecyclerViewDataAdapter2.COUPON_ITEM_MORE_LAYOUT);
//        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                isScrolling = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

//                if(isScrolling && (currentItems + scrollOutItems -1 == totalItems -1)) {
                Log.d("TEST00", "currentItems  " + currentItems);
                Log.d("TEST00", "scrollOutItems  " + scrollOutItems);
                Log.d("TEST00", "totalItems  " + totalItems);
                if(isScrolling && (currentItems + scrollOutItems +1 == totalItems )) {
                    isScrolling = false;
                    Log.d("TEST00", "getLoadDate");
                    getLoadDate();

                }
            }
        });

//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
////                super.onScrollStateChanged(recyclerView, newState);
//
//                if (!recyclerView.canScrollVertically(-1)) {
////                    Log.i(TAG, "Top of list");
//                } else if (!recyclerView.canScrollVertically(1) && loading) {
////                    Log.i(TAG, "End of list");
//                    getLoadDate();
//                } else {
////                    Log.i(TAG, "idle");
//                }
//
//            }
//        });

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                if (loading) {
//                    if (dy > 0) //check for scroll down
//                    {
//                        visibleItemCount = layoutManager.getChildCount();
//                        totalItemCount = layoutManager.getItemCount();
//                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
//
//                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && true) {
//                            getLoadDate();
//                        }
//                    }
//                }
//            }
//        });

        mManager = new StackExchangeManager();

        getLoadDate(); //dummy

    } //END

    private void getLoadDate() {
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                progressON();
//                loading = false;
                if (couponListRecyclerViewItemList == null)
                    couponListRecyclerViewItemList = new ArrayList<>();

                int currItemListSize = couponListRecyclerViewItemList.size();   // 현재 리스트 사이즈
                int newItemIndex = 0;       // 아이템 끝 index
                int loadMoreItemCount = 10;  // 로드할 아이템 개수
                int loadCount = currItemListSize+ loadMoreItemCount;

//                currItemListSize = couponListRecyclerViewItemList.size();
//                for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {
//
//                    String url = "http://image.chosun.com/sitedata/image/201701/13/2017011300860_0.jpg";
//                    couponListRecyclerViewItemList.add(new CouponRecyclerViewItem(String.valueOf(i), url, "상점이름", "메뉴", "음식종류", "200m", "10", "80,000", "56,000"));
//                }
//
//                newItemIndex = couponListRecyclerViewItemList.size() - 1;
//                adapter.reLoadData(couponListRecyclerViewItemList);
        Log.d("TEST00", "refreshList !!!!!!!!!!!!!!!");
//                refreshList(loadCount);
        test();

//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        executorService.shutdown(); //스레드풀 종료
    }


    private void test(){

        Call<UsersResponse> call = mManager.mStackExchangeService.doGetListResources(10);
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {

                UsersResponse usersResponse = (UsersResponse)response.body();


                Log.d("EEE", usersResponse.getItems().size() +"");
                adapter.reLoadData(usersResponse.getItems());
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                Log.d("EEE", "Error");
            }
        });

    }



    private void refreshList(int count){
                    progressON();
        mManager.getMostPopularSQysers(10)
                .subscribe(new DefaultSubscriber<List<Item>>() {
                    @Override
                    public void onNext(List<Item> items) {

                        Log.d("TEST00", "List size:" + items.size());
//                        couponListRecyclerViewItemList.addAll(items);
                        adapter.updateUsers(items);

                        for (int i = 0; i < items.size(); i++)
                        {
                            Log.d("TEST12", "111111 : " + items.get(i).getAccountId());

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("TEST00", "onError");
                    }

                    @Override
                    public void onComplete() {
//                        loading = true;
                        progressOFF();
                        isScrolling = true;
                    }

                } );
    }
}


