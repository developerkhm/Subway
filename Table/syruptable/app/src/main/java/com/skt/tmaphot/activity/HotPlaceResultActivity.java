package com.skt.tmaphot.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.location.GPSData;
import com.skt.tmaphot.net.model.HotplaceModel;
import com.skt.tmaphot.net.service.ServiceGenerator;
import com.skt.tmaphot.activity.main.hotplace.HotPlaceRecyclerViewDataAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HotPlaceResultActivity extends BaseActivity {

    // 핫플레이스 & 검색결과
    private RecyclerView recyclerView;
    private HotPlaceRecyclerViewDataAdapter hotPlaceRecyclerViewDataAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private ProgressBar bottomProgressBar;

    private boolean isloading = false;  // 로딩 여부
    private int currentPage = 0;
    private int sortType = 1;   //1 인기순, 2 거리순
    private final int per_page = 30;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search_result_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        bottomProgressBar = (ProgressBar)findViewById(R.id.result_bottom_progress);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView = (RecyclerView) findViewById(R.id.hotplace_recycler_view);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        hotPlaceRecyclerViewDataAdapter = new HotPlaceRecyclerViewDataAdapter(new ArrayList<HotplaceModel>());
        hotPlaceRecyclerViewDataAdapter.setHasStableIds(true);
        recyclerView.setAdapter(hotPlaceRecyclerViewDataAdapter);
//        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 200);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("TEST1234", "sdfsdfsdfsdfsdfsdf");
                fetchData(sortType);
            }
        });


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                Log.d("TTA1", "AAAAAAAAAAAA: " + newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
//
////                int visibleItemCount = manager.getChildCount();
////                int totalItemCount = manager.getItemCount();
////                int[] firstVisibleItems = manager.findFirstVisibleItemPositions(null);
////                int pastVisibleItems = 0;
////
////                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
////                    pastVisibleItems = firstVisibleItems[0];
////                }
////
////                if ((visibleItemCount + pastVisibleItems) == totalItemCount && !isloading) {
////                    fetchData(sortType);
////                }
////
////                int lastVisibleItemPosition = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPositions()
////                int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
////                if (lastVisibleItemPosition == itemTotalCount) {
////                    Toast.makeText(getContext(), "Last Position", Toast.LENGTH_SHORT).show();
////                }
//
//
//            }
//        });


        fetchData(sortType);
    }

    private synchronized void fetchData(int sortType) {
        bottomProgressBar.setVisibility(View.VISIBLE);
        isloading = true;
        currentPage++;

        ServiceGenerator.createService().getHotplaceList(currentPage, per_page, GPSData.getInstance().getLatitude(), GPSData.getInstance().getLongitude(), sortType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<HotplaceModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<HotplaceModel> hotplaceModels) {

                        hotPlaceRecyclerViewDataAdapter.loadData(hotplaceModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isloading = false;
//                        progressOFF();
                        bottomProgressBar.setVisibility(View.INVISIBLE);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        isloading = false;
                        bottomProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
        // The minimum amount of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 5;
        // The current offset index of data you have loaded
        private int currentPage = 0;
        // The total number of items in the dataset after the last load
        private int previousTotalItemCount = 0;
        // True if we are still waiting for the last set of data to load.
        private boolean loading = true;
        // Sets the starting page index
        private int startingPageIndex = 0;

        RecyclerView.LayoutManager mLayoutManager;

        public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
        }

        public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
            visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        }

        public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
            this.mLayoutManager = layoutManager;
            visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        }

        public int getLastVisibleItem(int[] lastVisibleItemPositions) {
            int maxSize = 0;
            for (int i = 0; i < lastVisibleItemPositions.length; i++) {
                if (i == 0) {
                    maxSize = lastVisibleItemPositions[i];
                }
                else if (lastVisibleItemPositions[i] > maxSize) {
                    maxSize = lastVisibleItemPositions[i];
                }
            }
            return maxSize;
        }

        // This happens many times a second during a scroll, so be wary of the code you place here.
        // We are given a few useful parameters to help us work out if we need to load some more data,
        // but first we check if we are waiting for the previous load to finish.
        @Override
        public void onScrolled(RecyclerView view, int dx, int dy) {
            int lastVisibleItemPosition = 0;
            int totalItemCount = mLayoutManager.getItemCount();

            if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
                // get maximum element within the list
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
            } else if (mLayoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            } else if (mLayoutManager instanceof LinearLayoutManager) {
                lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            }

            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.loading = true;
                }
            }
            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            // threshold should reflect how many total columns there are too
            if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
                currentPage++;
                onLoadMore(currentPage, totalItemCount, view);
                loading = true;
            }
        }

        // Call this method whenever performing new searches
        public void resetState() {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = 0;
            this.loading = true;
        }

        // Defines the process for actually loading more data based on page
        public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

    }
}
