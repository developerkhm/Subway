package com.skt.tmaphot.activity.main.store.review;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.net.model.store.SocialListItem;

import java.util.ArrayList;
import java.util.List;

public class SocialReviewTotalActivity extends BaseActivity {
    //소셜 리뷰리스트
    private RecyclerView socialReviewRecyclerView;
    private SocialReviewRecyclerViewAdapter socialReviewRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    private List<SocialListItem> socialListItems;

    private boolean isScrolling = true;
    int currentItems, totalItems, scrollOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialreview_total_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        socialListItems = getIntent().getParcelableArrayListExtra("social");
        Log.d("social12", "socialListItems total size : " + socialListItems.size());
        setSocialReview();
    } //END

    private void setSocialReview() {

        layoutManager = new LinearLayoutManager(baceContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        socialReviewRecyclerView = (RecyclerView) findViewById(R.id.soicalreview_total_recyler);
        socialReviewRecyclerView.setLayoutManager(layoutManager);
        socialReviewRecyclerViewAdapter = new SocialReviewRecyclerViewAdapter(new ArrayList<>(socialListItems.subList(0, 5)));
        socialReviewRecyclerView.setAdapter(socialReviewRecyclerViewAdapter);
        socialReviewRecyclerView.setFocusable(false);
        socialReviewRecyclerView.setHasFixedSize(true);
        socialReviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));

        socialReviewRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if (isScrolling && (currentItems + scrollOutItems + 1 == totalItems)) {
                    isScrolling = false;

                    Log.d("social12", "isScrolling itemTotal : " + totalItems);

                    fetchData(totalItems);
                }
            }
        });
    }

    private void fetchData(int totalItems) {
        if (totalItems + 5 < socialListItems.size()) {

            Log.d("social12", "[1] fetchData : " + totalItems + " = " + (totalItems + 5));
            Log.d("social12", "[1] socialListItems total size : " + socialListItems.size());
            socialReviewRecyclerViewAdapter.fetchData(new ArrayList<>(socialListItems.subList(totalItems, totalItems + 5)));
            isScrolling = true;
            return;
        } else {
            socialReviewRecyclerViewAdapter.fetchData(new ArrayList<>(socialListItems.subList(totalItems, socialListItems.size())));
            Log.d("social12", "[2] fetchData : " + totalItems + " = " + socialListItems.size());
            Log.d("social12", "[2] socialListItems total size : " + socialListItems.size());
            isScrolling = false;
            return;
        }
    }
}


