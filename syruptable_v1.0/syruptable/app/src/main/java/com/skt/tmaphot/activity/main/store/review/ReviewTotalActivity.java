package com.skt.tmaphot.activity.main.store.review;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;
import com.skt.tmaphot.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class ReviewTotalActivity extends BaseActivity {

    //리뷰리스트
    private RecyclerView reviewRecyclerView;
    private ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
    private List<ReviewItem> reviewItemList;
    private LinearLayoutManager layoutManager;
    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_total_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //////////////////////////////////////////////////////////
        getLoadDate(); //dummy
        setReview();
    } //END

    private void getLoadDate() {
        // dummydata
        if(reviewItemList == null)
            reviewItemList = new ArrayList<ReviewItem>();

        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));

    }

    private void setReview() {
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        reviewRecyclerView = (RecyclerView) findViewById(R.id.review_total_recyler);
        reviewRecyclerView.setLayoutManager(layoutManager);
        reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(reviewItemList);
        reviewRecyclerView.setAdapter(reviewRecyclerViewAdapter);
        reviewRecyclerView.setFocusable(false);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));
        reviewRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItem = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!mLoading && lastVisibleItem == totalItem - 1) {
                    mLoading = true;
                    // Scrolled to bottom. Do something here.
                    getLoadDate();

                    reviewRecyclerViewAdapter.notifyDataSetChanged();

                    mLoading = false;
                }
            }
        });
    }
}


