package com.skt.tmaphot.fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.MainActivity;
import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.IRecyclerItem;
import com.skt.tmaphot.activity.IRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.NestedScrollingView;
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
import com.skt.tmaphot.activity.main.menu.MainMenuRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.menu.MainMenuRecyclerViewHolder;
import com.skt.tmaphot.activity.main.menu.MainMenuRecyclerViewItem;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewItem;
import com.skt.tmaphot.activity.main.review.more.RealReviewActivity;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;
import com.skt.tmaphot.activity.search.SearchActivity;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.location.GPSData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainFragment extends BaseFragment {

    public int MESSAGE_REVIEW_COUPON_NOTIFY = 1004;
    public int MESSAGE_HOTPLACE_NOTIFY = 1004;
    public int MESSAGE_RECYLER_NOTIFY = 1004;


    private ViewGroup container;

    private NestedScrollingView nestedScrollView;

    // 리얼리뷰
    private RecyclerView realReviewRecyclerView;
    private ArrayList<IRecyclerItem> realReviewItemList;
    private RealReviewRecyclerViewDataAdapter realReviewRecyclerViewDataAdapter;

    // 주변 할인 쿠폰
    private RecyclerView couponRecyclerView;
    private ArrayList<IRecyclerItem> couponItemList;
    private CouponRecyclerViewDataAdapter couponRecyclerViewDataAdapter;

    // 주변 할딧 맛집
    private RecyclerView hotdealRecyclerView;
    private ArrayList<IRecyclerItem> hotdealItemList;
    private HotdealRecyclerViewDataAdapter hotdealRecyclerViewDataAdapter;

    // 핫플레이스
    private List<HotplaceGridViewItem> hotplace_Pop_ItemList, hotplace_Distance_ItemList;
    private ExpandableHeightGridView hotplaceGridview;
    private boolean ishotplaceItemLoad;
    private HotplaceGridAdapter hotplaceGridAdapter;
    private boolean ishotplaceModeDistance;

    // 메뉴
    private RecyclerView menuRecyclerView;
    private ArrayList<MainMenuRecyclerViewItem> mainMenuRecyclerViewItems;
    private MainMenuRecyclerViewDataAdapter menuRecyclerViewDataAdapter;

    ///////////////////배너/////////////////
    private RollingAdapter rollingAdapter;
    private RollingAutoManager rollingAutoManager;
    private ViewPager rollingViewPager;
    private RollingIndicatorView rollingIndicatorView;

    //더보기
    private TextView reviewMoreTextView, couponMoreTextview, hotdealMoreTextview;


    //리스너
    private CustomRecyclerViewOnScrollListener onScrollListenerRecyclerView;

    private CardView searchbar;
    private TextView hotplace_pop, hotplace_distance;

    //임시
    private int food_type = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ABCDE", "Fragment onCreateView");
        this.container = container;
        rootView = inflater.inflate(R.layout.fragment_main_layout, container, false);

        //초기 View 세팅
        initView();

        //banner
        initEventBannerSet();

        // 주변할인 쿠폰
        initRecylerViewSet(couponRecyclerView, true);

        // 리얼후기
        initRecylerViewSet(realReviewRecyclerView, true);

        // 주변 핫딜 맛집
        initRecylerViewSet(hotdealRecyclerView, true);

        // 메뉴
        initRecylerViewSet(menuRecyclerView, false);

        // 핫플레이스
        initHotplaceSet();

        return rootView;
    }


    private void initRecylerViewSet(final RecyclerView recyclerView, final boolean init) {

        getActivity().runOnUiThread(

                (new Runnable() {
                    @Override
                    public void run() {

                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);
                        if (menuRecyclerViewDataAdapter == null)
                            recyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 15, 0, 0));


                        switch (recyclerView.getId()) {

                            case R.id.menu_recycler_view:

                                if (mainMenuRecyclerViewItems == null)
                                    mainMenuRecyclerViewItems = new ArrayList<MainMenuRecyclerViewItem>();

                                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("55", "", R.drawable.img_main_menu_1));
                                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("88", "", R.drawable.img_main_menu_2));
                                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("22", "", R.drawable.img_main_menu_3));
                                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("222", "", R.drawable.img_main_menu_4));
                                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("545", "", R.drawable.img_main_menu_5));
                                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("235235", "", R.drawable.img_main_menu_6));
                                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("2355", "", R.drawable.img_main_menu_7));
                                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("23525", "", R.drawable.img_main_menu_8));

                                menuRecyclerViewDataAdapter = new MainMenuRecyclerViewDataAdapter(getActivity(), mainMenuRecyclerViewItems);
                                menuRecyclerViewDataAdapter.setOnMenuCilckListener(onMenuCilckListener); // 메뉴 리스너 등록
                                recyclerView.setAdapter(menuRecyclerViewDataAdapter);
                                //                loadData(false, recyclerView);
                                recyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0));
                                break;

                            case R.id.coupon_recycler_view:

                                if (couponItemList != null)
                                    couponItemList.clear();

                                couponItemList = new ArrayList<IRecyclerItem>();

                                couponRecyclerViewDataAdapter = new CouponRecyclerViewDataAdapter(getActivity(), couponItemList);
                                recyclerView.setAdapter(couponRecyclerViewDataAdapter);
                                loadData(recyclerView);
                                break;


                            case R.id.review_recycler_view:

                                if (realReviewItemList != null)
                                    realReviewItemList.clear();


                                realReviewItemList = new ArrayList<IRecyclerItem>();

                                realReviewRecyclerViewDataAdapter = new RealReviewRecyclerViewDataAdapter(getActivity(), realReviewItemList);
                                recyclerView.setAdapter(realReviewRecyclerViewDataAdapter);
                                loadData(recyclerView);
                                break;


                            case R.id.hotdeal_recycler_view:

                                if (hotdealItemList != null)
                                    hotdealItemList.clear();


                                hotdealItemList = new ArrayList<IRecyclerItem>();

                                hotdealRecyclerViewDataAdapter = new HotdealRecyclerViewDataAdapter(getActivity(), hotdealItemList);
                                recyclerView.setAdapter(hotdealRecyclerViewDataAdapter);
                                loadData(recyclerView);
                                break;
                        }

                        // Scroll RecyclerView a little to make later scroll take effect.
                        recyclerView.scrollToPosition(0);
                        recyclerView.setNestedScrollingEnabled(false);
                        recyclerView.setHasFixedSize(true);

//                        if (onScrollListenerRecyclerView != null) {
//                            Log.d("TOA", "onScrollListenerRecyclerView remove");
//                            recyclerView.removeOnScrollListener(onScrollListenerRecyclerView);
//                        }


                        if (init) {
                            onScrollListenerRecyclerView = new CustomRecyclerViewOnScrollListener();
                            recyclerView.addOnScrollListener(onScrollListenerRecyclerView);
                        }


//                        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//
//                            private int postion_id = position;
//
//                            @Override
//                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                                super.onScrolled(recyclerView, dx, dy);
//
//                                Log.d("RRR", "onScrolled");
//
//                                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//
//                                int firstCompleteVisibleItemPosition = -1;
//                                int lastCompleteVisibleItemPosition = -1;
//                                int visibleItemCount = layoutManager.getChildCount();
//                                int totalItemCount = layoutManager.getItemCount();
//
//                                if (layoutManager instanceof GridLayoutManager) {
//                                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
//                                    firstCompleteVisibleItemPosition = gridLayoutManager.findFirstCompletelyVisibleItemPosition();
//                                    lastCompleteVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
//                                } else if (layoutManager instanceof LinearLayoutManager) {
//                                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
//                                    firstCompleteVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
//                                    lastCompleteVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                                }
//
//                                String message = "";
//
//                                // Means scroll at beginning ( top to bottom or left to right).
////                    if (firstCompleteVisibleItemPosition == 0) {
////                        // dy < 0 means scroll to bottom, dx < 0 means scroll to right at beginning.
////                        if (dy < 0 || dx < 0) {
////                            // Means scroll to bottom.
////                            if (dy < 0) {
////                                loadData(true);
////                            }
////
////                            // Means scroll to right.
////                            if (dx < 0) {
////                                loadData(true);
////                            }
////                        }
////                    }
////                     Means scroll at ending ( bottom to top or right to left )
//                                ////////////////////////////// 미리 로드하기 위해서 꼼 수를 써보자 마지막이 아니라, 앞전에
//                                if (lastCompleteVisibleItemPosition == (totalItemCount - 1 - 2)) {
//                                    Log.d("RRR", "lastCompleteVisibleItemPosition");
//                                    // dy > 0 means scroll to up, dx > 0 means scroll to left at ending.
//                                    if (dy > 0 || dx > 0) {
//                                        // Scroll to top
////                        Log.d("RRR","Scroll to top");
////                        if (dy > 0) {
////                            loadData(false, recyclerView);
////                        }
//
//                                        // Scroll to left
//                                        if (dx > 0) {
//                                            Log.d("FAB", "Scroll end position value : " + postion_id);
//                                            loadData(postion_id, recyclerView);
//                                        }
//                                    }
//                                }
//
//                                if (message.length() > 0) {
//                                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });

                    }
                }));


    }

    private class CustomRunnable implements Runnable {

        ArrayList<IRecyclerItem> iRecyclerItems;
        IRecyclerViewDataAdapter iRecyclerViewDataAdapter;

        public CustomRunnable(ArrayList<IRecyclerItem> iRecyclerItems) {
            this.iRecyclerItems = iRecyclerItems;
        }

        public CustomRunnable(IRecyclerViewDataAdapter iRecyclerViewDataAdapter) {
            this.iRecyclerViewDataAdapter = iRecyclerViewDataAdapter;
        }

        @Override
        public void run() {
        }

    }

    private void loadData(final RecyclerView recyclerView) {

        Log.d("FAB", "loadData call");


        if (recyclerView.getId() == R.id.menu_recycler_view) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("FAB", "Thread start ");

                ArrayList<IRecyclerItem> iRecyclerItems = null;
                IRecyclerViewDataAdapter iRecyclerViewDataAdapter = null;

                int currItemListSize = 0;   // 현재 리스트 사이즈
                int newItemIndex = 0;       // 아이템 끝 index
                int loadMoreItemCount = 0;  // 로드할 아이템 개수
                int id = recyclerView.getId();

                switch (recyclerView.getId()) {

                    case R.id.coupon_recycler_view:
                        Log.d("FAB", "coupon_recycler_view set ");
                        iRecyclerItems = couponItemList;
                        loadMoreItemCount = 5;
                        iRecyclerViewDataAdapter = couponRecyclerViewDataAdapter;
                        break;

                    case R.id.review_recycler_view:
                        Log.d("FAB", "review_recycler_view set ");
                        iRecyclerItems = realReviewItemList;
                        loadMoreItemCount = 5;
                        iRecyclerViewDataAdapter = realReviewRecyclerViewDataAdapter;
                        break;

                    case R.id.hotdeal_recycler_view:
                        Log.d("FAB", "hotdeal_recycler_view set ");
                        iRecyclerItems = hotdealItemList;
                        loadMoreItemCount = 5;
                        iRecyclerViewDataAdapter = hotdealRecyclerViewDataAdapter;
                        break;
                }


                currItemListSize = iRecyclerItems.size();
                IRecyclerItem newViewItem = null;
                Log.d("FAB", "currItemListSize : " + currItemListSize);


                for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {

                    // 임시 더미 데이터 및 메뉴 클릭시 테스트 아직 정보가 없으니 갱신만 테스트

                    if (food_type == 100) {

                        if (id == R.id.coupon_recycler_view) {
                            Log.d("FAB", "coupon 100 itme add : " + i);

                            int temp = 10 + i;
                            newViewItem = new CouponRecyclerViewItem("http://img.kormedi.com/news/article/__icsFiles/artimage/2015/05/23/c_km601/432212_540.jpg",
                                    "황제짜장", "수제피자", "200m", String.valueOf(temp));
                        }

                        if (id == R.id.review_recycler_view) {
                            Log.d("FAB", "review 100 itme add : " + i);
                            newViewItem = new RealReviewRecyclerViewItem("https://www.lwt.co.kr/datas/factory/main_img/006059");
                        }

                        if (id == R.id.hotdeal_recycler_view) {
                            Log.d("FAB", "hotdeal 100 itme add : " + i);
                            newViewItem = new HotdealRecyclerViewItem("http://461cfe6bf17600ff0e6d-db982937f7c48520d2ef90d4bc24f696.r50.cf1.rackcdn.com/lps/assets/u/sspg_food4-169-.jpg",
                                    "황제짜장", "수제피자", "200m", "50%");
                        }
                    }

                    if (food_type == 0) {

                        if (id == R.id.review_recycler_view) {
                            Log.d("FAB", "review 0 itme add : " + i);
                            newViewItem = new RealReviewRecyclerViewItem("https://i.ytimg.com/vi/SZvTL5A_tNQ/maxresdefault.jpg");
                        }

                        if (id == R.id.hotdeal_recycler_view) {
                            Log.d("FAB", "hotdeal 0 itme add : " + i);
                            newViewItem = new HotdealRecyclerViewItem("http://4.bp.blogspot.com/-R29WyCcMomw/VIq-UhEndUI/AAAAAAAAAdo/vJZBIUKUAto/s1600/%EC%88%98%EC%A7%801.jpg",
                                    "황제짜장", "수제피자", "200m", "50%");
                        }

                    }


                    if (food_type == 1) {

                        if (id == R.id.review_recycler_view) {
                            Log.d("FAB", "review 1 itme add : " + i);
                            newViewItem = new RealReviewRecyclerViewItem("http://cfile10.uf.tistory.com/image/266D213B56A51A172FC174");
                        }

                        if (id == R.id.hotdeal_recycler_view) {
                            Log.d("FAB", "hotdeal 1 itme add : " + i);
                            newViewItem = new HotdealRecyclerViewItem("https://fimg4.pann.com/new/download.jsp?FileID=42599762",
                                    "황제짜장", "수제피자", "200m", "50%");
                        }

                    }


                    iRecyclerItems.add(newViewItem);
                }
                newItemIndex = iRecyclerItems.size() - 1;
                Log.d("FAB", "newItemIndex size : " + "[" + newItemIndex + "]");

                rootView.post(new CustomRunnable(iRecyclerViewDataAdapter) {

                    @Override
                    public void run() {

                        Log.d("FAB", "notifyData call ");
                        this.iRecyclerViewDataAdapter.notifyData();
                    }
                });


            }
        }).start();
    }


    private void initEventBannerSet() {

//        rollingTextView.post(new Runnable() {
//            @Override
//            public void run() {
//                rollingTextView.setSingleLine(false);
//                rollingTextView.setText(Html.fromHtml("어서오세요.<br/><b>여름에 가야할 새로운 매장!</b>"));
//                rollingTextView.setTextColor(Color.WHITE);
//                rollingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//            }
//        });


        rollingAdapter = new RollingAdapter(getActivity(), setEventBannerData(), new RollingAdapter.OnAdapterItemClickListener() {
            @Override
            public void onItemClick(RollingModel object, int position) {
                Toast.makeText(getActivity(), position + " items click!", Toast.LENGTH_SHORT).show();
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


    private void initView() {
        nestedScrollView = (NestedScrollingView) rootView.findViewById(R.id.main_nestedscrollview);
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

                if (scrollY == ((v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()))) {
                    Log.i("TAAA", "BOTTOM SCROLL");
                    loadHotplaceItem(ishotplaceModeDistance);
                }
            }
        });


        rollingIndicatorView = (RollingIndicatorView) rootView.findViewById(R.id.indicator_view);
        rollingViewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        searchbar = (CardView)rootView.findViewById(R.id.main_searchbar);
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainApplication.ActivityStart(new Intent(getActivity(), SearchActivity.class), null);
            }
        });

        realReviewRecyclerView = (RecyclerView) rootView.findViewById(R.id.review_recycler_view);

        couponRecyclerView = (RecyclerView) rootView.findViewById(R.id.coupon_recycler_view);

        hotdealRecyclerView = (RecyclerView) rootView.findViewById(R.id.hotdeal_recycler_view);
        menuRecyclerView = (RecyclerView) rootView.findViewById(R.id.menu_recycler_view);
        hotplaceGridview = (ExpandableHeightGridView) rootView.findViewById(R.id.hotplace_gridview);


        reviewMoreTextView = (TextView) rootView.findViewById(R.id.main_txt_review_more);
        reviewMoreTextView.setOnClickListener(onClickListenerMore);

        couponMoreTextview = (TextView) rootView.findViewById(R.id.main_txt_coupon_more);
        couponMoreTextview.setOnClickListener(onClickListenerMore);

        hotdealMoreTextview = (TextView) rootView.findViewById(R.id.main_txt_hotdeal_more);
        hotdealMoreTextview.setOnClickListener(onClickListenerMore);

        hotplace_pop = (TextView) rootView.findViewById(R.id.main_hotplace_pop);
        hotplace_pop.setOnClickListener(onClickListenerHotplaceType);
        hotplace_pop.setSelected(true);

        hotplace_distance = (TextView) rootView.findViewById(R.id.main_hotplace_distance);
        hotplace_distance.setOnClickListener(onClickListenerHotplaceType);
    }

    private void initHotplaceSet() {

        hotplaceGridview.setExpanded(true);
        hotplaceGridview.setOnItemClickListener(onItemClickListenerHotplace);

        if (hotplace_Pop_ItemList == null)
            hotplace_Pop_ItemList = new ArrayList<HotplaceGridViewItem>();

        hotplaceGridAdapter = new HotplaceGridAdapter(getContext(), hotplace_Pop_ItemList);
        hotplaceGridview.setAdapter(hotplaceGridAdapter);
//        hotplaceGridview.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                // TODO Auto-generated method stub
//
//                Log.e("GridView", "onScrollStateChanged : " + scrollState);
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                // TODO Auto-generated method stub
//                Log.e("GridView", "firstVisibleItem: " + firstVisibleItem + "\nvisibleItemCount: " + visibleItemCount + "\ntotalItemCount: " + totalItemCount);
//
//                // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
//                // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
//                int count = totalItemCount - visibleItemCount;
//
//
//                if (firstVisibleItem >= count && totalItemCount != 0 && ishotplaceItemLoad == false) {
//                    //loadGridItem();
//                    Log.e("GridView", "XXXXXXXXX loadGridItem : ");
//                }
//            }
//        });

        //데이터 로드
        loadHotplaceItem(ishotplaceModeDistance);
    }

    private void loadHotplaceItem(final boolean mode) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                ishotplaceItemLoad = true;

                if (!mode) {

                    Log.d("QQQ", "pop dataload");

//                    if(hotplace_Pop_ItemList == null);
//                        hotplace_Pop_ItemList = new ArrayList<HotplaceGridViewItem>();

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));
                } else {

                    Log.d("QQQ", "distance dataload");

//                    if(hotplace_Distance_ItemList == null);
//                        hotplace_Distance_ItemList = new ArrayList<HotplaceGridViewItem>();


                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));

                }

                ishotplaceItemLoad = false;
                rootView.post(new Runnable() {
                    @Override
                    public void run() {
                        hotplaceGridAdapter.notifyDataSetChanged();
                    }
                });

                hotplaceGridview.setFocusable(false);
            }
        }).start();
    }

    private void reloadHotPlace(boolean mode) {
        if (mode) {
            Log.d("GRID", "distnace mode");
            if (hotplace_Distance_ItemList == null) {
                hotplace_Distance_ItemList = new ArrayList<HotplaceGridViewItem>();
            } else {
                Log.d("GRID", "clear");
                hotplace_Distance_ItemList.clear();
            }
            hotplaceGridview.invalidateViews();
            hotplaceGridAdapter = new HotplaceGridAdapter(getActivity(), hotplace_Distance_ItemList);
            hotplaceGridview.setAdapter(hotplaceGridAdapter);

            loadHotplaceItem(mode);
        } else {

            if (hotplace_Pop_ItemList == null) {
                hotplace_Pop_ItemList = new ArrayList<HotplaceGridViewItem>();
            } else {
                hotplace_Pop_ItemList.clear();
            }

            hotplace_Pop_ItemList = new ArrayList<HotplaceGridViewItem>();
            hotplaceGridview.invalidateViews();
            hotplaceGridAdapter = new HotplaceGridAdapter(getActivity(), hotplace_Pop_ItemList);
            hotplaceGridview.setAdapter(hotplaceGridAdapter);

            loadHotplaceItem(mode);
        }
    }


    View.OnClickListener onClickListenerMore = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.main_txt_coupon_more:
                    MainApplication.ActivityStart(new Intent(getActivity(), CouponListActivity.class), null);
                    break;
                case R.id.main_txt_review_more:
                    MainApplication.ActivityStart(new Intent(getActivity(), RealReviewActivity.class), null);
                    break;
                case R.id.main_txt_hotdeal_more:
                    MainApplication.ActivityStart(new Intent(getActivity(), HotdealListActivity.class), null);
                    break;
            }
        }
    };

    View.OnClickListener onClickListenerHotplaceType = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Log.d("ABC", "click");

//            view.setSelected(!view.isSelected());
//            view.setFocusable(!view.isSelected());
            switch (view.getId()) {
                case R.id.main_hotplace_pop:

                    hotplace_pop.setSelected(true);
                    hotplace_distance.setSelected(false);

                    ishotplaceModeDistance = false;
                    reloadHotPlace(ishotplaceModeDistance);
                    hotplaceGridview.setFocusable(true);
                    break;
                case R.id.main_hotplace_distance:

                    hotplace_pop.setSelected(false);
                    hotplace_distance.setSelected(true);

                    ishotplaceModeDistance = true;
                    reloadHotPlace(ishotplaceModeDistance);
                    hotplaceGridview.setFocusable(true);
                    break;
            }
        }
    };


    AdapterView.OnItemClickListener onItemClickListenerHotplace = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MainApplication.ActivityStart(new Intent(getActivity(), StoreInfoActivity.class), null);
        }
    };

    MainMenuRecyclerViewHolder.OnMenuCilckListener onMenuCilckListener = new MainMenuRecyclerViewHolder.OnMenuCilckListener() {
        @Override
        public void menuOnClick(View v, int id, int position) {
            // 임시구현
            food_type = position;

            initRecylerViewSet(realReviewRecyclerView, false);
            initRecylerViewSet(hotdealRecyclerView, false);

            reloadHotPlace(ishotplaceModeDistance);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ABCDE", "Fragment onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ABCDE", "Fragment onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        //GPS 주소변환// 임시
        Log.d("ABCDE", "Fragment onStart");
//        initGPSTransferAddress();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ABCDE", "Fragment onResume");
        rollingAutoManager.onRollingStart();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        rollingAutoManager.onRollingStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rollingAutoManager.onRollingDestroy();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private class CustomRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

        public CustomRecyclerViewOnScrollListener() {

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            Log.d("RRR", "onScrolled");

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
            if (lastCompleteVisibleItemPosition == (totalItemCount - 1 - 2)) {
                Log.d("RRR", "lastCompleteVisibleItemPosition");
                // dy > 0 means scroll to up, dx > 0 means scroll to left at ending.
                if (dy > 0 || dx > 0) {
                    // Scroll to top
//                        Log.d("RRR","Scroll to top");
//                        if (dy > 0) {
//                            loadData(false, recyclerView);
//                        }

                    // Scroll to left
                    if (dx > 0) {
                        loadData(recyclerView);
                    }
                }
            }

            if (message.length() > 0) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }

    }
}
