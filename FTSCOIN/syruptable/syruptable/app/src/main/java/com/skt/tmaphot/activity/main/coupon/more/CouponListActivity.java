package com.skt.tmaphot.activity.main.coupon.more;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.skt.tmaphot.R;
import com.skt.tmaphot.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CouponListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private CouponListRecyclerViewDataAdapter adapter;
    private List<CouponListRecyclerViewItem> couponListRecyclerViewItemList;
    private LinearLayoutManager layoutManager;

    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //////////////////////////////////////////////////////////

        getLoadDate(); //dummy

        recyclerView = (RecyclerView) findViewById(R.id.main_coupon_list_recyclerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CouponListRecyclerViewDataAdapter(this, couponListRecyclerViewItemList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(20));
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



                    mLoading = false;
                }
            }
        });


    } //END

    private void getLoadDate() {

        if (couponListRecyclerViewItemList == null)
            couponListRecyclerViewItemList = new ArrayList<CouponListRecyclerViewItem>();


        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://image.sportsseoul.com/2018/02/23/news/20180223095152_(7).jpg",
                "id", "전주순대국", "뽁음순대", "한식","30","50,000원","30,000원"));

        couponListRecyclerViewItemList.add(new CouponListRecyclerViewItem(
                "http://image.sportsseoul.com/2018/02/23/news/20180223095152_(7).jpg",
                "id", "전주순대국", "뽁음순대", "한식","30","50,000원","30,000원"));



       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               if(adapter != null)
                    adapter.notifyDataSetChanged();
           }
       });
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            outRect.left = space;
//            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
//            if(parent.getChildAdapterPosition(view) == 0) {
//                outRect.top = space;
//            }
        }
    }
}


