package com.skt.tmaphot.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.banner.RollingAdapter;
import com.skt.tmaphot.activity.main.banner.RollingAutoManager;
import com.skt.tmaphot.activity.main.banner.RollingIndicatorView;
import com.skt.tmaphot.activity.main.banner.RollingModel;
import com.skt.tmaphot.recycle.RecyclerViewDataAdapter;
import com.skt.tmaphot.recycle.SectionDataModel;
import com.skt.tmaphot.recycle.SingleItemModel;

import java.util.ArrayList;
import java.util.List;

public class TestStoreInfoActivity extends AppCompatActivity {

    private ViewPager mViewPager = null;
    private RollingAdapter mAdapter = null;
    private RollingIndicatorView mIndicatorView = null;
    private RollingAutoManager mAutoRollingManager = null;
    RecyclerView my_recycler_view;


    ArrayList<SectionDataModel> allSampleData;
//    LinearLayout sliderDotspanel;
//    private int dotscount;
//    private ImageView[] dots;

    private List<RollingModel> getData() {
        List<RollingModel> list = new ArrayList<>();

        list.add(new RollingModel("1", "https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg"));
        list.add(new RollingModel("2", "https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg"));
        list.add(new RollingModel("3", "https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg"));
        list.add(new RollingModel("4", "https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg"));
        return list;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store_info);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayShowTitleEnabled(false);
//
//        mIndicatorView = (RollingIndicatorView) findViewById(R.id.indicator_view);
//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//
//        mAdapter = new RollingAdapter(TestStoreInfoActivity.this, getData(), new RollingAdapter.OnAdapterItemClickListener() {
//            @Override
//            public void onItemClick(RollingModel object, int position) {
//                Toast.makeText(TestStoreInfoActivity.this, position + " items click!", Toast.LENGTH_SHORT).show();
//            }
//        });
//        mViewPager.setAdapter(mAdapter);
//        mIndicatorView.setViewPager(mViewPager);
//        mAutoRollingManager = new RollingAutoManager(mViewPager, mAdapter, mIndicatorView);
//        mAutoRollingManager.setRollingTime(5500);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        allSampleData = new ArrayList<SectionDataModel>();
//
//        createDummyData();
//
//        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//        my_recycler_view.setHasFixedSize(false);
//        my_recycler_view.setNestedScrollingEnabled(false);
//
//
//        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);
//        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        my_recycler_view.getLayoutManager().setAutoMeasureEnabled(true);
//
//        my_recycler_view.setAdapter(adapter);
//        my_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                Log.d("TRECYCLER", "newState : " + newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.d("TRECYCLER", "onScrolled dx: " + dx);
//                Log.d("TRECYCLER", "onScrolled dy: " + dy);
//
//                                LinearLayoutManager mLayoutManager = (LinearLayoutManager) my_recycler_view
//                        .getLayoutManager();
//
//                int visibleItemCount = my_recycler_view.getChildCount();
//                int totalItemCount = mLayoutManager.getItemCount();
//                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//
//                Log.d("TRECYCLER", "visibleItemCount: " + String.valueOf(visibleItemCount));
//                Log.d("TRECYCLER", "totalItemCount: " + String.valueOf(totalItemCount));
//                Log.d("TRECYCLER", "firstVisibleItem: " + String.valueOf(firstVisibleItem));
//            }
//        });
//
//
//        NestedScrollView ns = (NestedScrollView) findViewById(R.id.scroll_view);
//        ns.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                Log.d("RECYCLER", "NestedScrollView scrollX: " + scrollX);
//                Log.d("RECYCLER", "NestedScrollView scrollY: " + scrollY);
//                Log.d("RECYCLER", "NestedScrollView oldScrollX: " + oldScrollX);
//                Log.d("RECYCLER", "NestedScrollView oldScrollY: " + oldScrollY);
//
//
//                Log.d("RECYCLER", "NestedScrollView nestedScrollView.getChildCount() - 1: " + String.valueOf(nestedScrollView.getChildCount() - 1));
//                Log.d("RECYCLER", "NestedScrollView nestedScrollView.getChildCount: " + String.valueOf(nestedScrollView.getChildCount()));
//
//
////                LinearLayoutManager mLayoutManager = (LinearLayoutManager) my_recycler_view
////                        .getLayoutManager();
//
////                int visibleItemCount = my_recycler_view.getChildCount();
////                int totalItemCount = mLayoutManager.getItemCount();
////                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
////
////                Log.d("RECYCLERTT", "visibleItemCount: " + String.valueOf(visibleItemCount));
////                Log.d("RECYCLERTT", "totalItemCount: " + String.valueOf(totalItemCount));
////                Log.d("RECYCLERTT", "firstVisibleItem: " + String.valueOf(firstVisibleItem));
//
//                // init
////                RecyclerView.LayoutManager layoutManager = my_recycler_view.getLayoutManager();
////                RecyclerView.Adapter adapter = my_recycler_view.getAdapter();
////
////                if (layoutManager.getChildCount() > 0) {
////                    // Calculations..
////                    int indexOfLastItemViewVisible = layoutManager.getChildCount() - 1;
////                    View lastItemViewVisible = layoutManager.getChildAt(indexOfLastItemViewVisible);
////                    int adapterPosition = layoutManager.getPosition(lastItemViewVisible);
////                    boolean isLastItemVisible = (adapterPosition == adapter.getItemCount() - 1);
//
//                    // check
////                    if (isLastItemVisible)
////                        onLastItemVisible(); // callback
////                }
//
//
//                if (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1) != null) {
//                    if (((scrollY >= (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1).getMeasuredHeight() - nestedScrollView.getMeasuredHeight())) && scrollY > oldScrollY)) {
//                        //code to fetch more data for endless scrolling
//
//                        Log.d("RECYCLER", "NestedScrollViewCALL if: ");
//                    }
//                }
//                // v.getChildCount() -1 should give you the recycler view for which you be implementing endless scrolling.
//                // scrollY > oldScrollY confirms that the page is being scrolled down.
//            }
//        });
//
//
////        dotscount = viewPagerAdapter.getCount();
////        dots = new ImageView[dotscount];
////
////        for (int i = 0; i < dotscount; i++) {
////            dots[i] = new ImageView(this);
////            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
////
////            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
////
////            params.setMargins(8, 0, 8, 0);
////
////            sliderDotspanel.addView(dots[i], params);
////
////        }
////
////        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
////
////        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////            @Override
////            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////
////            }
////
////            @Override
////            public void onPageSelected(int position) {
////
////                for(int i = 0; i< dotscount; i++){
////                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
////                }
////
////                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
////
////            }
////
////            @Override
////            public void onPageScrollStateChanged(int state) {
////
////            }
////        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAutoRollingManager.onRollingStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAutoRollingManager.onRollingStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAutoRollingManager.onRollingDestroy();
    }

    public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle("Section " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
}


