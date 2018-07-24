package com.skt.tmaphot.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.banner.RollingAdapter;
import com.skt.tmaphot.activity.main.banner.RollingAutoManager;
import com.skt.tmaphot.activity.main.banner.RollingIndicatorView;
import com.skt.tmaphot.activity.main.banner.RollingModel;
import com.skt.tmaphot.activity.main.coupon.CouponRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.coupon.CouponRecyclerViewItem;
import com.skt.tmaphot.activity.main.coupon.more.CouponListActivity;
import com.skt.tmaphot.activity.main.hotdeal.HotdealRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.hotdeal.HotdealRecyclerViewItem;
import com.skt.tmaphot.activity.main.hotdeal.more.HotdealListActivity;
import com.skt.tmaphot.activity.main.hotplace.ExpandableHeightGridView;
import com.skt.tmaphot.activity.main.hotplace.HotplaceGridAdapter;
import com.skt.tmaphot.activity.main.hotplace.HotplaceGridViewItem;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewItem;
import com.skt.tmaphot.activity.main.review.more.RealReviewActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

public class NewSyrupMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int MESSAGE_UPDATE_REVIEW_RECYCLER_VIEW = 1004;
    private final int MESSAGE_UPDATE_COUPON_RECYCLER_VIEW = 1005;
    private final int MESSAGE_UPDATE_HOTDEAL_RECYCLER_VIEW = 1006;
    private final int MESSAGE_UPDATE_HOTPLACE_GRID_VIEW = 1007;


    private String MESSAGE_REVIEW_NEW_ITEM_INDEX = "KEY";
    private String MESSAGE_COUPON_NEW_ITEM_INDEX = "KEY";
    private Handler Handler;
    private NestedScrollView nestedScrollView;

    //////////////리싸이클 뷰//////////////////
    // 리얼리뷰
    private RecyclerView realReviewRecyclerView;
    private List<RealReviewRecyclerViewItem> realReviewItemList;
    private RealReviewRecyclerViewDataAdapter realReviewRecyclerViewDataAdapter;
    private ProgressBar realReviewLoadingProgressBar;

    // 주변 할인 쿠폰
    private RecyclerView couponRecyclerView;
    private List<CouponRecyclerViewItem> couponItemList;
    private CouponRecyclerViewDataAdapter couponRecyclerViewDataAdapter;
    private ProgressBar couponLoadingProgressBar;

    // 주변 할딧 맛집
    private RecyclerView hotdealRecyclerView;
    private List<HotdealRecyclerViewItem> hotdealItemList;
    private HotdealRecyclerViewDataAdapter hotdealRecyclerViewDataAdapter;
    private ProgressBar hotdealLoadingProgressBar;


    // 핫플레이스
    private List<HotplaceGridViewItem> hotplaceGridViewItemList;
    private ExpandableHeightGridView hotplaceGridview;
    private boolean ishotplaceItemLoad;
    private HotplaceGridAdapter hotplaceGridAdapter;


    ///////////////////배너/////////////////
    private RollingAdapter rollingAdapter;
    private RollingAutoManager rollingAutoManager;
    private ViewPager rollingViewPager;
    private RollingIndicatorView rollingIndicatorView;
    private TextView rollingTextView;
    private ImageView searchbarImgView, menu1ImgView, menu2ImgView, menu3ImgView, menu4ImgView;

    private TextView reviewMoreTextView, couponMoreTextview, hotdealMoreTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ButterKnife.bind(this);

        setContentView(R.layout.activity_syrup_main_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (Handler == null) {
            Handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    // If the message want to refresh list view.
                    if (msg.what == MESSAGE_UPDATE_REVIEW_RECYCLER_VIEW) {
                        // Refresh list view after add item data.
                        realReviewRecyclerViewDataAdapter.notifyDataSetChanged();
                        realReviewLoadingProgressBar.setVisibility(View.GONE);
                        Log.d("RRR" ,"MESSAGE_UPDATE_REVIEW_RECYCLER_VIEW");
                    }

                    if (msg.what == MESSAGE_UPDATE_COUPON_RECYCLER_VIEW) {
                        // Refresh list view after add item data.
                        couponRecyclerViewDataAdapter.notifyDataSetChanged();
                        couponLoadingProgressBar.setVisibility(View.GONE);
                    }

                    if (msg.what == MESSAGE_UPDATE_HOTDEAL_RECYCLER_VIEW) {
                        // Refresh list view after add item data.
                        hotdealRecyclerViewDataAdapter.notifyDataSetChanged();
                        hotdealLoadingProgressBar.setVisibility(View.GONE);
                    }

                    if (msg.what == MESSAGE_UPDATE_HOTPLACE_GRID_VIEW) {
                        // Refresh list view after add item data.
                        hotplaceGridAdapter.notifyDataSetChanged();
                    }



//                    Bundle bundle = msg.getData();
//                    int newItemIndex = bundle.getInt(MESSAGE_REVIEW_NEW_ITEM_INDEX);
//                    recyclerView.scrollToPosition(newItemIndex - 1);

                }
            };
        }

        ///////////////////////////////////////init/////////////////////////////////////////////////

        //초기 View 세팅
        initView();

        //GPS 주소변환
        setGPSTrans();

        //banner
        setEventBanner();

        //searchbar
        MainApplication.loadImage(this, R.drawable.img_main_searchbar, searchbarImgView);

        // 리얼후기
        setRealReview(realReviewRecyclerView);

        // 주변할인 쿠폰
        setRealReview(couponRecyclerView);

        // 주변 핫딜 맛집
        setRealReview(hotdealRecyclerView);

        //menu
        setMainMenu();

        //핫플레이스 그리드
        setHotplace();

        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////


        // Create the recyclerview.
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.custom_refresh_recycler_view);
//        // Create the grid layout manager with 2 columns.
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        // Set layout manager.
//        recyclerView.setLayoutManager(layoutManager);
//
//        // Create car recycler view data adapter with car item list.
//        customRecyclerViewDataAdapter = new CustomRecyclerViewDataAdapter(this, itemList);
//        // Set data adapter.
//        recyclerView.setAdapter(customRecyclerViewDataAdapter);
//
//        // Scroll RecyclerView a little to make later scroll take effect.
//        recyclerView.scrollToPosition(0);
//
//        recyclerView.setNestedScrollingEnabled(false);

//        Button btn = (Button)findViewById(R.id.btn_more);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(NewSyrupMainActivity.this, RealReviewActivity.class));
//            }
//        });

//        Button temp = (Button)findViewById(R.id.btn_temp);
//        temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(NewSyrupMainActivity.this, CouponListActivity.class));
//            }
//        });

//        Button temp2 = (Button)findViewById(R.id.btn_temp2);
//        temp2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(NewSyrupMainActivity.this, ReviewWriteActivity3.class));
//            }
//        });


    }//END

    private void setRealReview(RecyclerView recyclerView) {

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Set layout manager.
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(15));


        switch (recyclerView.getId()) {
            case R.id.review_recycler_view:


                // 리얼 후기 임시 더미 데이터 세팅
                if (realReviewItemList == null) {
                    realReviewItemList = new ArrayList<RealReviewRecyclerViewItem>();

                    realReviewItemList.add(new RealReviewRecyclerViewItem("http://static.hubzum.zumst.com/hubzum/2017/08/25/15/04c2ac2b30204bfba26f059152b2e52e_0x520c.jpg"));
                    realReviewItemList.add(new RealReviewRecyclerViewItem("http://static.hubzum.zumst.com/hubzum/2017/08/25/15/04c2ac2b30204bfba26f059152b2e52e_0x520c.jpg"));
                    realReviewItemList.add(new RealReviewRecyclerViewItem("http://static.hubzum.zumst.com/hubzum/2017/08/25/15/04c2ac2b30204bfba26f059152b2e52e_0x520c.jpg"));
                    realReviewItemList.add(new RealReviewRecyclerViewItem("http://static.hubzum.zumst.com/hubzum/2017/08/25/15/04c2ac2b30204bfba26f059152b2e52e_0x520c.jpg"));
                    realReviewItemList.add(new RealReviewRecyclerViewItem("http://static.hubzum.zumst.com/hubzum/2017/08/25/15/04c2ac2b30204bfba26f059152b2e52e_0x520c.jpg"));
                    realReviewItemList.add(new RealReviewRecyclerViewItem("http://static.hubzum.zumst.com/hubzum/2017/08/25/15/04c2ac2b30204bfba26f059152b2e52e_0x520c.jpg"));
                }


                // Create car recycler view data adapter with car item list.
                realReviewRecyclerViewDataAdapter = new RealReviewRecyclerViewDataAdapter(this, realReviewItemList);
                // Set data adapter.
                recyclerView.setAdapter(realReviewRecyclerViewDataAdapter);

                break;

            case R.id.coupon_recycler_view:


                // 리얼 후기 임시 더미 데이터 세팅
                if (couponItemList == null) {
                    couponItemList = new ArrayList<CouponRecyclerViewItem>();

                    couponItemList.add(new CouponRecyclerViewItem("http://cfile213.uf.daum.net/image/18424F204BE3DAFBE18C4D",
                            "황제짜장", "수제피자", "200m", "50%"));
                    couponItemList.add(new CouponRecyclerViewItem("http://cfile213.uf.daum.net/image/18424F204BE3DAFBE18C4D",
                            "황제짜장", "수제피자", "200m", "50%"));
                    couponItemList.add(new CouponRecyclerViewItem("http://cfile213.uf.daum.net/image/18424F204BE3DAFBE18C4D",
                            "황제짜장", "수제피자", "200m", "50%"));
                    couponItemList.add(new CouponRecyclerViewItem("http://cfile213.uf.daum.net/image/18424F204BE3DAFBE18C4D",
                            "황제짜장", "수제피자", "200m", "50%"));
                    couponItemList.add(new CouponRecyclerViewItem("http://cfile213.uf.daum.net/image/18424F204BE3DAFBE18C4D",
                            "황제짜장", "수제피자", "200m", "50%"));
                    couponItemList.add(new CouponRecyclerViewItem("http://cfile213.uf.daum.net/image/18424F204BE3DAFBE18C4D",
                            "황제짜장", "수제피자", "200m", "50%"));

                }


                // Create car recycler view data adapter with car item list.
                couponRecyclerViewDataAdapter = new CouponRecyclerViewDataAdapter(this, couponItemList);
                // Set data adapter.
                recyclerView.setAdapter(couponRecyclerViewDataAdapter);

                break;

            case R.id.hotdeal_recycler_view:

                if (hotdealItemList == null) {
                    hotdealItemList = new ArrayList<HotdealRecyclerViewItem>();

                    hotdealItemList.add(new HotdealRecyclerViewItem("https://i.ytimg.com/vi/P_WOCaUELCA/maxresdefault.jpg",
                            "황제짜장", "수제피자", "200m", "50%"));
                    hotdealItemList.add(new HotdealRecyclerViewItem("https://i.ytimg.com/vi/P_WOCaUELCA/maxresdefault.jpg",
                            "황제짜장", "수제피자", "200m", "50%"));
                    hotdealItemList.add(new HotdealRecyclerViewItem("https://i.ytimg.com/vi/P_WOCaUELCA/maxresdefault.jpg",
                            "황제짜장", "수제피자", "200m", "50%"));
                    hotdealItemList.add(new HotdealRecyclerViewItem("https://i.ytimg.com/vi/P_WOCaUELCA/maxresdefault.jpg",
                            "황제짜장", "수제피자", "200m", "50%"));
                    hotdealItemList.add(new HotdealRecyclerViewItem("https://i.ytimg.com/vi/P_WOCaUELCA/maxresdefault.jpg",
                            "황제짜장", "수제피자", "200m", "50%"));
                    hotdealItemList.add(new HotdealRecyclerViewItem("https://i.ytimg.com/vi/P_WOCaUELCA/maxresdefault.jpg",
                            "황제짜장", "수제피자", "200m", "50%"));
                    hotdealItemList.add(new HotdealRecyclerViewItem("https://i.ytimg.com/vi/P_WOCaUELCA/maxresdefault.jpg",
                            "황제짜장", "수제피자", "200m", "50%"));
                    hotdealItemList.add(new HotdealRecyclerViewItem("https://i.ytimg.com/vi/P_WOCaUELCA/maxresdefault.jpg",
                            "황제짜장", "수제피자", "200m", "50%"));

                }


                // Create car recycler view data adapter with car item list.
                hotdealRecyclerViewDataAdapter = new HotdealRecyclerViewDataAdapter(this, hotdealItemList);
                // Set data adapter.
                recyclerView.setAdapter(hotdealRecyclerViewDataAdapter);

                break;


        }


        // Scroll RecyclerView a little to make later scroll take effect.
        recyclerView.scrollToPosition(0);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("RRR","onScrolled");

                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int firstCompleteVisibleItemPosition = -1;
                int lastCompleteVisibleItemPosition = -1;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    firstCompleteVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
                    lastCompleteVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                } else if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    firstCompleteVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    lastCompleteVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                }

                String message = "";

                // Means scroll at beginning ( top to bottom or left to right).
//                    if (firstCompleteVisibleItemPosition == 0) {
//                        // dy < 0 means scroll to bottom, dx < 0 means scroll to right at beginning.
//                        if (dy < 0 || dx < 0) {
//                            // Means scroll to bottom.
//                            if (dy < 0) {
//                                loadData(true);
//                            }
//
//                            // Means scroll to right.
//                            if (dx < 0) {
//                                loadData(true);
//                            }
//                        }
//                    }
//                     Means scroll at ending ( bottom to top or right to left )
                ////////////////////////////// 미리 로드하기 위해서 꼼 수를 써보자 마지막이 아니라, 앞전에
                if (lastCompleteVisibleItemPosition == (totalItemCount - 1 -2)) {
                    Log.d("RRR","lastCompleteVisibleItemPosition");
                    // dy > 0 means scroll to up, dx > 0 means scroll to left at ending.
                    if (dy > 0 || dx > 0) {
                        // Scroll to top
//                        Log.d("RRR","Scroll to top");
//                        if (dy > 0) {
//                            loadData(false, recyclerView);
//                        }

                        // Scroll to left
                        if (dx > 0) {
                            Log.d("RRR","Scroll to left");
                            loadData(false, recyclerView);
                        }
                    }
                }

                if (message.length() > 0) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadData(final boolean insertDataAtBeginning, final RecyclerView recyclerView) {

        Thread workerThread = new Thread() {
            @Override
            public void run() {
                Log.d("RRR","loadData");
                try {
                    switch (recyclerView.getId()) {
                        case R.id.review_recycler_view:
                            Log.d("RRR","reviewRecycle");
//                            realReviewLoadingProgressBar.setVisibility(View.VISIBLE);

                            int currItemListSize = realReviewItemList.size();
                            int newItemIndex = 0;
                            int loadMoreItemCount = 5;

                            // Only add one RecyclerView item.
                            for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {
                                Log.d("RRR","for");
                                RealReviewRecyclerViewItem newViewItem = new RealReviewRecyclerViewItem("https://www.lwt.co.kr/datas/factory/main_img/006059");


                                if (insertDataAtBeginning) {
                                    realReviewItemList.add(i - currItemListSize, newViewItem);
                                    newItemIndex = 0;
                                } else {
                                    realReviewItemList.add(newViewItem);
                                    newItemIndex = realReviewItemList.size() - 1;
                                }

                            }


                            Message message = new Message();
                            message.what = MESSAGE_UPDATE_REVIEW_RECYCLER_VIEW;
                            Bundle bundle = new Bundle();
                            bundle.putInt(MESSAGE_REVIEW_NEW_ITEM_INDEX, newItemIndex);
                            message.setData(bundle);
                            Handler.sendMessage(message);


                            break;

                        case R.id.coupon_recycler_view:

//                            couponLoadingProgressBar.setVisibility(View.VISIBLE);

                            int currItemListSizeT = couponItemList.size();
                            int newItemIndexT = 0;
                            int loadMoreItemCountT = 5;

                            // Only add one RecyclerView item.
                            for (int i = currItemListSizeT; i < currItemListSizeT + loadMoreItemCountT; i++) {

                                CouponRecyclerViewItem newViewItem = new CouponRecyclerViewItem("http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                                        "황제짜장", "수제피자", "200m", "50%");

                                if (insertDataAtBeginning) {
                                    couponItemList.add(i - currItemListSizeT, newViewItem);
                                    newItemIndexT = 0;
                                } else {
                                    couponItemList.add(newViewItem);
                                    newItemIndexT = couponItemList.size() - 1;
                                }
                            }

                            Message message1 = new Message();
                            message1.what = MESSAGE_UPDATE_COUPON_RECYCLER_VIEW;
                            Handler.sendMessage(message1);
                            break;

                        case R.id.hotdeal_recycler_view:
                            Log.d("RRR","hotdeal_recycler_view");
//                            couponLoadingProgressBar.setVisibility(View.VISIBLE);

                            int currItemListSizeTT = hotdealItemList.size();
                            int newItemIndexTT = 0;
                            int loadMoreItemCountTT = 5;

                            // Only add one RecyclerView item.
                            for (int i = currItemListSizeTT; i < currItemListSizeTT + loadMoreItemCountTT; i++) {

                                HotdealRecyclerViewItem newViewItem = new HotdealRecyclerViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                                        "황제짜장", "수제피자", "200m", "50%");

                                if (insertDataAtBeginning) {
                                    hotdealItemList.add(i - currItemListSizeTT, newViewItem);
                                    newItemIndexTT = 0;
                                } else {
                                    hotdealItemList.add(newViewItem);
                                    newItemIndexTT = hotdealItemList.size() - 1;
                                }
                            }

                            Log.d("RRR","hotdeal_recycler_view Message");
                            Message message2 = new Message();
                            message2.what = MESSAGE_UPDATE_HOTDEAL_RECYCLER_VIEW;
                            Handler.sendMessage(message2);
                            break;
                    }

                } catch (Exception ex) {
                    Log.d("RRR", ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };

        workerThread.start();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.syrup_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            return true;
        }

        if (id == R.id.action_user) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:
                break;
            case R.id.nav_login:

                break;
            case R.id.nav_mypage:

                break;
            case R.id.nav_cart:

                break;

            case R.id.nav_noti:

                break;
            case R.id.nav_board:

                break;
            case R.id.nav_review:

                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        rollingAutoManager.onRollingStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rollingAutoManager.onRollingStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rollingAutoManager.onRollingDestroy();
    }


    private void setEventBanner() {


        rollingTextView.post(new Runnable() {
            @Override
            public void run() {
                rollingTextView.setSingleLine(false);
                rollingTextView.setText(Html.fromHtml("어서오세요.<br/><b>여름에 가야할 새로운 매장!</b>"));
                rollingTextView.setTextColor(Color.WHITE);
                rollingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            }
        });


        rollingAdapter = new RollingAdapter(this, setEventBannerData(), new RollingAdapter.OnAdapterItemClickListener() {
            @Override
            public void onItemClick(RollingModel object, int position) {
                Toast.makeText(NewSyrupMainActivity.this, position + " items click!", Toast.LENGTH_SHORT).show();
            }
        });

        rollingViewPager.setAdapter(rollingAdapter);
        rollingIndicatorView.setViewPager(rollingViewPager);
        rollingAutoManager = new RollingAutoManager(rollingViewPager, rollingAdapter, rollingIndicatorView);
        rollingAutoManager.setRollingTime(5500);
    }

    private List<RollingModel> setEventBannerData() {

        List<RollingModel> list = new ArrayList<>();
        list.add(new RollingModel("1", "https://media-cdn.tripadvisor.com/media/photo-s/0b/ef/52/df/caption.jpg"));
        list.add(new RollingModel("2", "http://samtanartmine.com/img/cont8_8.jpg"));
        list.add(new RollingModel("3", "https://img.siksinhot.com/place/1480735356478788.jpg?w=307&h=300&c=Y"));
        list.add(new RollingModel("4", "http://magazine.hankyung.com/magazinedata/images/photo/201402/2dd2a176d727991d2fc7fd59d7449899.jpg"));
        return list;
    }

    private void setMainMenu() {

        int menu[] = new int[]{R.drawable.img_main_menu_1,
                R.drawable.img_main_menu_2,
                R.drawable.img_main_menu_3,
                R.drawable.img_main_menu_4};


        MainApplication.loadImage(this, menu[0], menu1ImgView);
        MainApplication.loadImage(this, menu[1], menu2ImgView);
        MainApplication.loadImage(this, menu[2], menu3ImgView);
        MainApplication.loadImage(this, menu[3], menu4ImgView);
    }

    private void setGPSTrans() {
        Intent intent = getIntent();
        String cityName = null;

        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());

        List<Address> addresses = null;

        try {

            addresses = gcd.getFromLocation(intent.getDoubleExtra("latitude", 0), intent.getDoubleExtra("longitude", 0), 1);

            if (addresses.size() > 0)

//            Log.d("GEO", addresses.get(0).getLocality());   // 구 메인,
//            Log.d("GEO", addresses.get(0).getAddressLine(0).toString());    // 국가, 시 군 구 동 번지
//            Log.d("GEO", addresses.get(0).getAdminArea());  //시
//            Log.d("GEO", addresses.get(0).getThoroughfare());   //동


//            String tt = addresses.get(0).getAddressLine(0).toString();
//            String result = tt.substring(tt.star(" "), tt.lastIndexOf(" "));

                //서울시 강남구 삼성동
                //하남시 덕풍동

                Log.d("GEO", addresses.get(0).getAdminArea() + " " + addresses.get(0).getSubLocality() + " " + addresses.get(0).getThoroughfare());   //동


        } catch (IOException e) {

            e.printStackTrace();

        }
        //임시 테스트중
        String t = null;
        if (addresses.get(0).getSubLocality() != null) {
            t = addresses.get(0).getSubLocality();   //구
        } else {
            t = addresses.get(0).getAdminArea();   //시
        }

        String tt = addresses.get(0).getThoroughfare();     //동

        TextView mGPSTextView = (TextView) findViewById(R.id.main_text_gps);
        mGPSTextView.setText(t + " " + tt);
    }

    private void initView() {

        nestedScrollView = (NestedScrollView)findViewById(R.id.main_nestedscrollview);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    Log.i("TAAA", "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    Log.i("TAAA", "Scroll UP");
                }

                if (scrollY == 0) {
                    Log.i("TAAA", "TOP SCROLL");
                }

                if (scrollY== (( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() ))) {
                    Log.i("TAAA", "BOTTOM SCROLL");
                    loadGridItem();
                }
            }
        });

        // 아직 할지 말지 모름 여기는 text 서버에서 받아오는건지.. 임시
        rollingTextView = (TextView) findViewById(R.id.main_text_banner);
        rollingIndicatorView = (RollingIndicatorView) findViewById(R.id.indicator_view);
        rollingViewPager = (ViewPager) findViewById(R.id.viewPager);

        searchbarImgView = (ImageView) findViewById(R.id.main_img_searchbar);
        searchbarImgView.setScaleType(ImageView.ScaleType.FIT_XY);

        realReviewRecyclerView = (RecyclerView) findViewById(R.id.review_recycler_view);
        realReviewLoadingProgressBar = (ProgressBar) findViewById(R.id.review_recycler_view_progressbar);

        couponRecyclerView = (RecyclerView) findViewById(R.id.coupon_recycler_view);
        couponLoadingProgressBar = (ProgressBar) findViewById(R.id.coupon_recycler_view_progressbar);

        hotdealRecyclerView = (RecyclerView) findViewById(R.id.hotdeal_recycler_view);
        hotdealLoadingProgressBar = (ProgressBar) findViewById(R.id.hotdeal_recycler_view_progressbar);

        menu1ImgView = (ImageView) findViewById(R.id.main_img_menu_1);
        menu2ImgView = (ImageView) findViewById(R.id.main_img_menu_2);
        menu3ImgView = (ImageView) findViewById(R.id.main_img_menu_3);
        menu4ImgView = (ImageView) findViewById(R.id.main_img_menu_4);


        hotplaceGridview = (ExpandableHeightGridView)findViewById(R.id.hotplace_gridview);

        reviewMoreTextView = (TextView)findViewById(R.id.main_txt_review_more);
        reviewMoreTextView.setOnClickListener(onClickListener);

        couponMoreTextview = (TextView)findViewById(R.id.main_txt_coupon_more);
        couponMoreTextview.setOnClickListener(onClickListener);

        hotdealMoreTextview = (TextView)findViewById(R.id.main_txt_hotdeal_more);
        hotdealMoreTextview.setOnClickListener(onClickListener);


    }

    private void setHotplace(){

        hotplaceGridview.setExpanded(true);


        ishotplaceItemLoad = false;


        hotplaceGridViewItemList = new ArrayList<HotplaceGridViewItem>();


        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));
        hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                "황제짜장", "수제피자", "200m", "50%","맛이어요"));


        hotplaceGridAdapter = new HotplaceGridAdapter(this, hotplaceGridViewItemList);
        hotplaceGridview.setAdapter(hotplaceGridAdapter);
        hotplaceGridview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

                Log.e("GridView", "onScrollStateChanged : " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                Log.e("GridView", "firstVisibleItem: " + firstVisibleItem + "\nvisibleItemCount: " + visibleItemCount + "\ntotalItemCount: " + totalItemCount);

                // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
                // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
                int count = totalItemCount - visibleItemCount;


                if(firstVisibleItem >= count && totalItemCount != 0 && ishotplaceItemLoad == false) {
                        //loadGridItem();
                    Log.e("GridView", "XXXXXXXXX loadGridItem : " );
                }
            }
        });
    }

    private void loadGridItem(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                ishotplaceItemLoad = true;

                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));
                hotplaceGridViewItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                        "황제짜장", "수제피자", "200m", "50%","맛이어요"));





                ishotplaceItemLoad = false;

                Message message = new Message();
                message.what = MESSAGE_UPDATE_HOTPLACE_GRID_VIEW;
                Handler.sendMessage(message);


            }
        }).start();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId())
            {
                case R.id.main_txt_review_more:
                    startActivity(new Intent(NewSyrupMainActivity.this, RealReviewActivity.class));
                    break;
                case R.id.main_txt_coupon_more:
                    startActivity(new Intent(NewSyrupMainActivity.this, CouponListActivity.class));
                    break;
                case R.id.main_txt_hotdeal_more:
                    startActivity(new Intent(NewSyrupMainActivity.this, HotdealListActivity.class));
                    break;
            }
        }
    };


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            outRect.left = space;
            outRect.right = space;
//            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
//            if(parent.getChildAdapterPosition(view) == 0) {
//                outRect.top = space;
//            }
        }
    }

}
