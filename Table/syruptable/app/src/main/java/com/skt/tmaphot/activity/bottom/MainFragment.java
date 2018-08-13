package com.skt.tmaphot.activity.bottom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.BaseApplication;
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
import com.skt.tmaphot.activity.main.menu.MainMenuRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.menu.MainMenuRecyclerViewItem;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewItem;
import com.skt.tmaphot.activity.main.review.more.RealReviewActivity;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;
import com.skt.tmaphot.activity.search.SearchActivity;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.fragment.BaseFragment;
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

public class MainFragment extends BaseFragment {

    private NestedScrollingView nestedScrollView;

    // 주변 할인 쿠폰
    private RecyclerView couponRecyclerView;
    private List<CouponRecyclerViewItem> couponItemList;
    private CouponRecyclerViewDataAdapter couponRecyclerViewDataAdapter;

    // 리얼리뷰
    private RecyclerView realReviewRecyclerView;
    private List<RealReviewRecyclerViewItem> realReviewItemList;
    private RealReviewRecyclerViewDataAdapter realReviewRecyclerViewDataAdapter;

    // 주변 할딧 맛집
    private RecyclerView hotdealRecyclerView;
    private List<HotdealRecyclerViewItem> hotdealItemList;
    private HotdealRecyclerViewDataAdapter hotdealRecyclerViewDataAdapter;

    // 핫플레이스
    private RecyclerView hotplaceRecyclerView;
    private HotPlaceRecyclerViewDataAdapter hotPlaceRecyclerViewDataAdapter;
    private boolean hotplace_isLoading = false;
    private int hotplace_sortType = 1;
    private int hotplace_curruntPage = 0;
    private TextView hotplace_pop, hotplace_distance;
    private final int per_page = 20;

    // 메뉴
    private int menuType = 0;
    private RecyclerView menuRecyclerView;
    private List<MainMenuRecyclerViewItem> mainMenuRecyclerViewItems;
    private MainMenuRecyclerViewDataAdapter menuRecyclerViewDataAdapter;

    //배너
    private RollingAdapter rollingAdapter;
    private RollingAutoManager rollingAutoManager;
    private ViewPager rollingViewPager;
    private RollingIndicatorView rollingIndicatorView;

    //더보기
    private TextView reviewMoreTextView, couponMoreTextview, hotdealMoreTextview;
    private CardView searchbar;

    private ProgressBar bottomProgressBar;


    public boolean loading = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_layout, container, false);
        rootView.findViewById(R.id.toolbar).setVisibility(View.GONE);

        //초기 View 세팅
        initView();

        //banner
        initEventBannerSet();

        // 주변할인 쿠폰
        recylerSet(couponRecyclerView);

        // 리얼후기
        recylerSet(realReviewRecyclerView);

        // 주변 핫딜 맛집
        recylerSet(hotdealRecyclerView);

        // 메뉴
        recylerSet(menuRecyclerView);

        // 핫플레이스
        hotplaceSet();

        return rootView;
    }


    private void recylerSet(RecyclerView recyclerView) {

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 40);
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

                menuRecyclerViewDataAdapter = new MainMenuRecyclerViewDataAdapter(mainMenuRecyclerViewItems);
                menuRecyclerViewDataAdapter.setOnEventCilckListener(onEventCilckListener); // 메뉴 리스너 등록
                menuRecyclerViewDataAdapter.setHasStableIds(true);
                recyclerView.setAdapter(menuRecyclerViewDataAdapter);


                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("0", "", R.drawable.img_main_menu_0));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("1", "", R.drawable.img_main_menu_1));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("2", "", R.drawable.img_main_menu_2));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("3", "", R.drawable.img_main_menu_3));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("4", "", R.drawable.img_main_menu_4));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("5", "", R.drawable.img_main_menu_5));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("6", "", R.drawable.img_main_menu_6));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("7", "", R.drawable.img_main_menu_7));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("8", "", R.drawable.img_main_menu_8));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("9", "", R.drawable.img_main_menu_9));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("10", "", R.drawable.img_main_menu_10));
                mainMenuRecyclerViewItems.add(new MainMenuRecyclerViewItem("11", "", R.drawable.img_main_menu_11));

                menuRecyclerViewDataAdapter.notifyDataSetChanged();

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

                couponRecyclerViewDataAdapter.updateUsers(couponItemList);
                loading = true;
                break;

            case R.id.review_recycler_view:
                currItemListSize = realReviewItemList.size();
                Log.d("BBT", "real loadData currItemListSize " + currItemListSize);
                for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {
                    if (menuType == 0) {
                        String url = "http://cfile214.uf.daum.net/image/110EBE0E49A774D7AC1983";
                        realReviewItemList.add(new RealReviewRecyclerViewItem(String.valueOf(i), url));
                    } else {
                        Log.d("BBT", "real loadData for int :" + i);
                        String url = "http://philsalgi.com/xe/files/attach/images/145/414/008/2c89504033b3c1ce95755484e4dc5948.jpg";
                        realReviewItemList.add(new RealReviewRecyclerViewItem(String.valueOf(i + 100), url));
                        ///////////////////// 아이디 값이 달라야 한다, 메뉴 변경시 //////////////////////////////////
                    }
                }
                newItemIndex = realReviewItemList.size() - 1;
                realReviewRecyclerViewDataAdapter.reLoadData(realReviewItemList);
                loading = true;
                break;
            case R.id.hotdeal_recycler_view:
                currItemListSize = hotdealItemList.size();
                for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {
                    if (menuType == 0) {
                        String url = "http://www.slist.kr/news/photo/201602/853_3916_1257.jpg";
                        hotdealItemList.add(new HotdealRecyclerViewItem(String.valueOf(i), url, "3", "피자", "불고기피자", "300", "20"));
                    } else {
                        Log.d("BBT", "hotdeal loadData foodType else");
                        String url = "https://steptohealth.co.kr/wp-content/uploads/2017/03/foods-to-avoid-eating-for-breakfast-500x283.jpg";
                        hotdealItemList.add(new HotdealRecyclerViewItem(String.valueOf(i + 100), url, "3", "피자", "불고기피자", "300", "20"));
                        /////////////////////  아이디 값이 달라야 한다, 메뉴 변경시  //////////////////////////////////
                    }
                }
                newItemIndex = hotdealItemList.size() - 1;
                hotdealRecyclerViewDataAdapter.reLoadData(hotdealItemList);
                loading = true;
                break;
        }
    }

    private void initEventBannerSet() {

        rollingAdapter = new RollingAdapter(getActivity(), setEventDummyBannerData(), new RollingAdapter.OnAdapterItemClickListener() {
            @Override
            public void onItemClick(RollingModel object, int position) {
                Intent intent = new Intent(getActivity(), StoreInfoActivity.class);
                intent.putExtra("id", object.getResId());
                BaseApplication.getInstance().ActivityStart(intent, null);
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

                if (scrollY > oldScrollY) { //Scroll DOWN
                    if (((MainActivity) getActivity()).navigation.getVisibility() == View.VISIBLE)
                        ((MainActivity) getActivity()).slideDown(((MainActivity) getActivity()).navigation);
                }
                if (scrollY < oldScrollY) { //Scroll UP

                }

                if (scrollY == 0) { //TOP SCROLL
                    if (((MainActivity) getActivity()).navigation.getVisibility() == View.GONE)
                        ((MainActivity) getActivity()).slideUp(((MainActivity) getActivity()).navigation);
                }

                if (scrollY == ((v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) && !hotplace_isLoading) {
                    loadData_Hotplace(hotplace_sortType);
                }
            }
        });

        nestedScrollView.setScrollListener(new NestedScrollingView.NestedScrollViewScrollStateListener() {
            @Override
            public void onNestedScrollViewStateChanged(int state) {
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

        couponRecyclerView = (RecyclerView) rootView.findViewById(R.id.coupon_recycler_view);
        realReviewRecyclerView = (RecyclerView) rootView.findViewById(R.id.review_recycler_view);
        hotdealRecyclerView = (RecyclerView) rootView.findViewById(R.id.hotdeal_recycler_view);
        menuRecyclerView = (RecyclerView) rootView.findViewById(R.id.menu_recycler_view);

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

        bottomProgressBar = (ProgressBar)rootView.findViewById(R.id.hotplace_bottom_progress);
    }

    private void loadData_Hotplace(int hotplace_sortType) {
//        ((BaseActivity) getActivity()).progressON();
        bottomProgressBar.setVisibility(View.VISIBLE);
        hotplace_isLoading = true;
        hotplace_curruntPage++;

        ServiceGenerator.createService().getHotplaceList(hotplace_curruntPage, 20, GPSData.getInstance().getLatitude(), GPSData.getInstance().getLongitude(), hotplace_sortType)
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
                        hotplace_isLoading = false;
                        bottomProgressBar.setVisibility(View.INVISIBLE);
//                        ((BaseActivity) getActivity()).progressOFF();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        hotplace_isLoading = false;
//                        ((BaseActivity) getActivity()).progressOFF();
//                        hotplaceRecyclerView.requestFocus();
//                        nestedScrollView.requestFocus();
                        bottomProgressBar.setVisibility(View.INVISIBLE);
                    }
                });

    }

    public void hotplaceSet() {
        hotplaceRecyclerView = (RecyclerView) rootView.findViewById(R.id.hotplace_recycler_view);
        final StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        hotplaceRecyclerView.setHasFixedSize(true);
        hotplaceRecyclerView.setNestedScrollingEnabled(false);
        hotplaceRecyclerView.setFocusable(false);
        hotplaceRecyclerView.setLayoutManager(mLayoutManager);
        hotPlaceRecyclerViewDataAdapter = new HotPlaceRecyclerViewDataAdapter(new ArrayList<HotplaceModel>());
        hotplaceRecyclerView.setAdapter(hotPlaceRecyclerViewDataAdapter);
//        hotplaceRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 40);
        hotplaceRecyclerView.setFocusableInTouchMode(true);

        loadData_Hotplace(hotplace_sortType);
    }

    private void reLoadData_HotPlace(int hotplaceLoadType) {

        hotplace_curruntPage = 0;
        hotPlaceRecyclerViewDataAdapter.clearData();
        hotPlaceRecyclerViewDataAdapter = new HotPlaceRecyclerViewDataAdapter(new ArrayList<HotplaceModel>());
        hotplaceRecyclerView.setAdapter(hotPlaceRecyclerViewDataAdapter);

        loadData_Hotplace(hotplaceLoadType);
    }

    View.OnClickListener onClickListenerHotplaceType = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.main_hotplace_pop:
                    hotplace_pop.setSelected(true);
                    hotplace_distance.setSelected(false);
                    hotplace_sortType = 1;
                    reLoadData_HotPlace(hotplace_sortType);
                    break;
                case R.id.main_hotplace_distance:
                    hotplace_pop.setSelected(false);
                    hotplace_distance.setSelected(true);
                    hotplace_sortType = 2;
                    reLoadData_HotPlace(hotplace_sortType);
                    break;
            }
        }
    };

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

    MainMenuRecyclerViewDataAdapter.OnEventCilckListener onEventCilckListener = new MainMenuRecyclerViewDataAdapter.OnEventCilckListener() {
        @Override
        public void menuOnClick(int position) {

            menuType = position;

            realReviewItemList.clear();
            loadData(realReviewRecyclerView);

            hotdealItemList.clear();
            loadData(hotdealRecyclerView);

            reLoadData_HotPlace(hotplace_sortType);
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
//        executorService.shutdown(); //스레드풀 종료
    }

    int test = 0;

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


            if (loading && lastCompleteVisibleItemPosition == (totalItemCount - 1 - 1)) {  // 미리 로드하기 위해서 꼼 수를 써보자 마지막이 아니라, 앞전에
                Log.d("TEST12", "lastCompleteVisibleItemPosition : " + ++test);
                loading = false;
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
