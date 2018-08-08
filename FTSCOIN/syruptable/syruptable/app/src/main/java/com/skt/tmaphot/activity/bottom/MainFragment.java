package com.skt.tmaphot.activity.bottom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.skt.tmaphot.MainActivity;
import com.skt.tmaphot.R;
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
import com.skt.tmaphot.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainFragment extends BaseFragment {

    private NestedScrollingView nestedScrollView;

    // 리얼리뷰
    private RecyclerView realReviewRecyclerView;
    private List<RealReviewRecyclerViewItem> realReviewItemList;
    private RealReviewRecyclerViewDataAdapter realReviewRecyclerViewDataAdapter;

    // 주변 할인 쿠폰
    private RecyclerView couponRecyclerView;
    private List<CouponRecyclerViewItem> couponItemList;
    private CouponRecyclerViewDataAdapter couponRecyclerViewDataAdapter;

    // 주변 할딧 맛집
    private RecyclerView hotdealRecyclerView;
    private List<HotdealRecyclerViewItem> hotdealItemList;
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

    //배너
    private RollingAdapter rollingAdapter;
    private RollingAutoManager rollingAutoManager;
    private ViewPager rollingViewPager;
    private RollingIndicatorView rollingIndicatorView;

    //더보기
    private TextView reviewMoreTextView, couponMoreTextview, hotdealMoreTextview;

    //리스너
    private HorizontalRecyclerViewOnScrollListener onScrollListenerRecyclerView;

    private CardView searchbar;
    private TextView hotplace_pop, hotplace_distance;

    //임시
    private int food_type = 100;

    private ExecutorService executorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ABCDE", "Fragment onCreateView");
        rootView = inflater.inflate(R.layout.fragment_main_layout, container, false);
        rootView.findViewById(R.id.toolbar).setVisibility(View.GONE);

        executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        //초기 View 세팅
        initView();

        //banner
        initEventBannerSet();

        // 주변할인 쿠폰
        initRecylerViewSet(couponRecyclerView);

        // 리얼후기
        initRecylerViewSet(realReviewRecyclerView);

        // 주변 핫딜 맛집
        initRecylerViewSet(hotdealRecyclerView);

        // 메뉴
        initRecylerViewSet(menuRecyclerView);

        // 핫플레이스
        initHotplaceSet();

        return rootView;
    }


    private void initRecylerViewSet(RecyclerView recyclerView) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 10);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        if (recyclerView.getId() != R.id.menu_recycler_view) {
            recyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 15, 0, 0));
            recyclerView.addOnScrollListener(new HorizontalRecyclerViewOnScrollListener());
        }

        switch (recyclerView.getId()) {

            case R.id.menu_recycler_view:

                if (mainMenuRecyclerViewItems == null)
                    mainMenuRecyclerViewItems = new ArrayList<>();

                menuRecyclerViewDataAdapter = new MainMenuRecyclerViewDataAdapter(getActivity(), mainMenuRecyclerViewItems);
                menuRecyclerViewDataAdapter.setOnMenuCilckListener(onMenuCilckListener); // 메뉴 리스너 등록
                recyclerView.setAdapter(menuRecyclerViewDataAdapter);

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("55", "", R.drawable.img_main_menu_1));
                        mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("88", "", R.drawable.img_main_menu_2));
                        mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("22", "", R.drawable.img_main_menu_3));
                        mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("222", "", R.drawable.img_main_menu_4));
                        mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("545", "", R.drawable.img_main_menu_5));
                        mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("235235", "", R.drawable.img_main_menu_6));
                        mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("2355", "", R.drawable.img_main_menu_7));
                        mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("23525", "", R.drawable.img_main_menu_8));

                        menuRecyclerViewDataAdapter.notifyDataSetChanged();

                    }
                });
                break;

            case R.id.coupon_recycler_view:

                if (couponItemList == null)
                    couponItemList = new ArrayList<>();

                couponRecyclerViewDataAdapter = new CouponRecyclerViewDataAdapter(couponItemList, CouponRecyclerViewDataAdapter.COUPON_ITEM_MAIN_LAYOUT);
                couponRecyclerViewDataAdapter.setHasStableIds(true);
                recyclerView.setAdapter(couponRecyclerViewDataAdapter);

                loadData(recyclerView);
                break;

            case R.id.review_recycler_view:

                if (realReviewItemList == null)
                    realReviewItemList = new ArrayList<>();

                realReviewRecyclerViewDataAdapter = new RealReviewRecyclerViewDataAdapter(realReviewItemList);
                realReviewRecyclerViewDataAdapter.setHasStableIds(true);
                recyclerView.setAdapter(realReviewRecyclerViewDataAdapter);

                loadData(recyclerView);
                break;

            case R.id.hotdeal_recycler_view:

                if (hotdealItemList == null)
                    hotdealItemList = new ArrayList<>();

                hotdealRecyclerViewDataAdapter = new HotdealRecyclerViewDataAdapter(hotdealItemList, HotdealRecyclerViewDataAdapter.HOTDEAL_ITEM_MAIN_LAYOUT);
                hotdealRecyclerViewDataAdapter.setHasStableIds(true);
                recyclerView.setAdapter(hotdealRecyclerViewDataAdapter);

                loadData(recyclerView);
                break;
        }

        recyclerView.scrollToPosition(0);

    }

    private void loadData(final RecyclerView recyclerView) {

        if (recyclerView.getId() == R.id.menu_recycler_view) {
            return;
        }

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                int currItemListSize = 0;   // 현재 리스트 사이즈
                int newItemIndex = 0;       // 아이템 끝 index
                int loadMoreItemCount = 5;  // 로드할 아이템 개수

                switch (recyclerView.getId()) {

                    case R.id.coupon_recycler_view:
                        currItemListSize = couponItemList.size();
                        for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {
                            String url = "http://cfd.tourtips.com/@cms_1024x768/2016032550/gjf72o/%EB%A7%88%EB%8B%90%EB%9D%BC_%EC%95%84%EB%A6%AC%EC%8A%A4%ED%86%A0%ED%81%AC%EB%9E%98%ED%8A%B8_%EC%9D%8C%EC%8B%9D_TTB(3).JPG";
                            couponItemList.add(new CouponRecyclerViewItem(String.valueOf(i), url, "상점이름", "메뉴", "음식종류", "200m", "10", "80,000", "56,000"));
                        }
                        newItemIndex = couponItemList.size() - 1;
                        couponRecyclerViewDataAdapter.reLoadData(couponItemList);
                        break;

                    case R.id.review_recycler_view:
                        currItemListSize = realReviewItemList.size();
                        for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {
                            String url = "http://cfile214.uf.daum.net/image/110EBE0E49A774D7AC1983";
                            realReviewItemList.add(new RealReviewRecyclerViewItem(String.valueOf(i), url));
                        }
                        newItemIndex = realReviewItemList.size() - 1;
                        realReviewRecyclerViewDataAdapter.reLoadData(realReviewItemList);
                        break;
                    case R.id.hotdeal_recycler_view:
                        currItemListSize = hotdealItemList.size();
                        for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {
                            String url = "http://www.slist.kr/news/photo/201602/853_3916_1257.jpg";
                            hotdealItemList.add(new HotdealRecyclerViewItem(String.valueOf(i), url, "3", "피자", "불고기피자","300","20"));
                        }
                        newItemIndex = hotdealItemList.size() - 1;
                        hotdealRecyclerViewDataAdapter.reLoadData(hotdealItemList);
                        break;
                }
            }
        });
    }


    private void initEventBannerSet() {

        rollingAdapter = new RollingAdapter(getActivity(), setEventDummyBannerData(), new RollingAdapter.OnAdapterItemClickListener() {
            @Override
            public void onItemClick(RollingModel object, int position) {
//                Toast.makeText(getActivity(), position + " items click!", Toast.LENGTH_SHORT).show();
            }
        });

        rollingViewPager.setAdapter(rollingAdapter);
        rollingIndicatorView.setViewPager(rollingViewPager);
        rollingAutoManager = new RollingAutoManager(rollingViewPager, rollingAdapter, rollingIndicatorView);
        rollingAutoManager.setRollingTime(5500);
    }

    private List<RollingModel> setEventDummyBannerData() {

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
                    Log.d("TEST1234", "Scroll DOWN");
                    if (((MainActivity) getActivity()).navigation.getVisibility() == View.VISIBLE)
                        ((MainActivity) getActivity()).slideDown(((MainActivity) getActivity()).navigation);
                }
                if (scrollY < oldScrollY) {
                    Log.i("TAAA", "Scroll UP");
                    Log.d("TEST1234", "Scroll UP");
//                    if(((MainActivity)getActivity()).navigation.getVisibility() == View.VISIBLE)
//                        ((MainActivity)getActivity()).navigation.setVisibility(View.GONE);
                }

                if (scrollY == 0) {
                    Log.i("TAAA", "TOP SCROLL");
                    if (((MainActivity) getActivity()).navigation.getVisibility() == View.GONE)
                        ((MainActivity) getActivity()).slideUp(((MainActivity) getActivity()).navigation);
                }

                if (scrollY == ((v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()))) {
                    Log.i("TAAA", "BOTTOM SCROLL");
//                    ((MainActivity)getActivity()).navigation.setVisibility(View.VISIBLE);
                    loadHotplaceItem(ishotplaceModeDistance);
                }
            }
        });

        nestedScrollView.setScrollListener(new NestedScrollingView.NestedScrollViewScrollStateListener() {
            @Override
            public void onNestedScrollViewStateChanged(int state) {
                Log.d("TEST1234", "onNestedScrollViewStateChanged");
                if (state == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("TEST1234", "VISIBLE");
//                    ((MainActivity)getActivity()).navigation.setVisibility(View.VISIBLE);
                }
            }
        });


        rollingIndicatorView = (RollingIndicatorView) rootView.findViewById(R.id.indicator_view);
        rollingViewPager = (ViewPager) rootView.findViewById(R.id.viewPager);

        searchbar = (CardView) rootView.findViewById(R.id.main_searchbar);
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStart(new Intent(getActivity(), SearchActivity.class), null);
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
            hotplace_Pop_ItemList = new ArrayList<>();

        hotplaceGridAdapter = new HotplaceGridAdapter(getContext(), hotplace_Pop_ItemList);
        hotplaceGridview.setAdapter(hotplaceGridAdapter);

        //데이터 로드
        loadHotplaceItem(ishotplaceModeDistance);
    }

    private void loadHotplaceItem(final boolean mode) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {

//                BaseApplication.getInstance().progressON(getActivity(),"");
                ishotplaceItemLoad = true;

                if (!mode) {

                    Log.d("QQQ", "pop dataload");

//                    if(hotplace_Pop_ItemList == null);
//                        hotplace_Pop_ItemList = new ArrayList<HotplaceGridViewItem>();

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://static.hubzum.zumst.com/hubzum/2017/11/06/13/a0e8c70dba7248598e4ba30a390b487c_0x576c.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));


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
//                BaseApplication.getInstance().progressOFF();
            }
        });
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
                    ActivityStart(new Intent(getActivity(), CouponListActivity.class), null);
                    break;
                case R.id.main_txt_review_more:
                    ActivityStart(new Intent(getActivity(), RealReviewActivity.class), null);
                    break;
                case R.id.main_txt_hotdeal_more:
                    ActivityStart(new Intent(getActivity(), HotdealListActivity.class), null);
                    break;
            }
        }
    };

    View.OnClickListener onClickListenerHotplaceType = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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
            ActivityStart(new Intent(getActivity(), StoreInfoActivity.class), null);
        }
    };

    MainMenuRecyclerViewHolder.OnMenuCilckListener onMenuCilckListener = new MainMenuRecyclerViewHolder.OnMenuCilckListener() {
        @Override
        public void menuOnClick(View v, int id, int position) {
            // 임시구현
            food_type = position;   // 음식 타입 파라미터 서버로 전송 데이터는 받아오기만 하면됨

            realReviewItemList.clear();
            loadData(realReviewRecyclerView);

            hotdealItemList.clear();
            loadData(hotdealRecyclerView);

            reloadHotPlace(ishotplaceModeDistance);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        executorService.shutdown(); //스레드풀 종료
    }

    private class HorizontalRecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

        public HorizontalRecyclerViewOnScrollListener() {

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            int firstCompleteVisibleItemPosition = -1;
            int lastCompleteVisibleItemPosition = -1;
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();

            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            firstCompleteVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            lastCompleteVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();


            if (lastCompleteVisibleItemPosition == (totalItemCount - 1 - 1)) {  // 미리 로드하기 위해서 꼼 수를 써보자 마지막이 아니라, 앞전에

                // dy > 0 means scroll to up, dx > 0 means scroll to left at ending.
                if (dy > 0 || dx > 0) {

                    if (dy > 0) {    // Scroll to top

                    }

                    if (dx > 0) {   // Scroll to left
                        loadData(recyclerView);
                    }
                }
            }
        }
    }
}
