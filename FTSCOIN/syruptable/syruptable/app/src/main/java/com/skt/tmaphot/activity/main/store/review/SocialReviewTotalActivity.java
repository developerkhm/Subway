package com.skt.tmaphot.activity.main.store.review;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class SocialReviewTotalActivity extends BaseActivity {

    //소셜 리뷰리스트
    private RecyclerView socialReviewRecyclerView;
    private SocialReviewRecyclerViewAdapter socialReviewRecyclerViewAdapter;
    private List<SocialReviewItem> socialReviewItemList;
    private LinearLayoutManager layoutManager;
    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialreview_total_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //////////////////////////////////////////////////////////
        getLoadDate(); //dummy
        setSocialReview();

    } //END

    private void getLoadDate() {

        // dummydata
        if(socialReviewItemList == null)
            socialReviewItemList = new ArrayList<SocialReviewItem>();

        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
    }

    private void setSocialReview() {

        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        socialReviewRecyclerView = (RecyclerView) findViewById(R.id.soicalreview_total_recyler);
        socialReviewRecyclerView.setLayoutManager(layoutManager);
        socialReviewRecyclerViewAdapter = new SocialReviewRecyclerViewAdapter(socialReviewItemList);
        socialReviewRecyclerView.setAdapter(socialReviewRecyclerViewAdapter);
        socialReviewRecyclerView.setFocusable(false);
        socialReviewRecyclerView.setHasFixedSize(true);
        socialReviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));
        socialReviewRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItem = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!mLoading && lastVisibleItem == totalItem - 1) {
                    mLoading = true;
                    // Scrolled to bottom. Do something here.
                    getLoadDate();

                    socialReviewRecyclerViewAdapter.notifyDataSetChanged();

                    mLoading = false;
                }
            }
        });
    }
}


