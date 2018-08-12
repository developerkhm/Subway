package com.skt.tmaphot.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int[] firstVisibleItems = manager.findFirstVisibleItemPositions(null);
                int pastVisibleItems = 0;

                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if ((visibleItemCount + pastVisibleItems) == totalItemCount && !isloading) {
                    fetchData(sortType);
                }
            }
        });

        fetchData(sortType);
    }

    private synchronized void fetchData(int sortType) {
        bottomProgressBar.setVisibility(View.VISIBLE);
        isloading = true;
        currentPage++;

        ServiceGenerator.createService().getHotplaceList(currentPage, GPSData.getInstance().getLatitude(), GPSData.getInstance().getLongitude(), sortType)
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
}
