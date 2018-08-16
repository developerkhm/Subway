package com.skt.tmaphot.activity.main.store;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.ImageViewPager;
import com.skt.tmaphot.ImageViewPagerActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.store.review.ReviewItem;
import com.skt.tmaphot.activity.main.store.review.ReviewRecyclerViewAdapter;
import com.skt.tmaphot.activity.main.store.review.SocialReviewItem;
import com.skt.tmaphot.activity.main.store.review.SocialReviewRecyclerViewAdapter;
import com.skt.tmaphot.activity.review.ReviewWriteActivity;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.net.model.store.Instum;
import com.skt.tmaphot.net.model.store.StoreInfoModel;
import com.skt.tmaphot.net.service.APIService;
import com.skt.tmaphot.net.service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StoreInfoActivity extends BaseActivity {

    public static final String INTENT_REVIEW = "R";

    //상단 메인 이미지
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ImageViewPager mViewPager;
    private TextView mViewpagerImageCount;

    private TextView storeEvalute, storeName, storeAddress, storeType, storeReviewCount, storeBookmarkCount, storeDetailAddress, storePhoneNumber, storeBusinessHours, storeDayOff, storeContent;

    // 상점 리뷰사진 모음 리스트
    private ReviewImageRecyclerViewDataAdapter storeInfoRecyclerViewDataAdapter;
    private RecyclerView storeInfoReviewRecyclerView;

    private LinearLayout info_bookmark, info_review, info_evaluation, list_review;
    private RatingBar ratingBar;

    //리뷰리스트
    private RecyclerView reviewRecyclerView;
    private ReviewRecyclerViewAdapter reviewRecyclerViewAdapter;
    private List<ReviewItem> reviewItemList;

    //소셜 리뷰리스트
    private RecyclerView socialReviewRecyclerView;
    private SocialReviewRecyclerViewAdapter socialReviewRecyclerViewAdapter;
    private List<SocialReviewItem> socialReviewItemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baceContext = this;
        setContentView(R.layout.activity_storeinfo_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


//        Button reviewMore = (Button) findViewById(R.id.review_more);
//        reviewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityStart(new Intent(baceContext, ReviewTotalActivity.class), null);
//            }
//        });
//
//        Button socialReviewMore = (Button) findViewById(R.id.socialreview_more);
//        socialReviewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityStart(new Intent(baceContext, SocialReviewTotalActivity.class), null);
//            }
//        });
//
//        info_bookmark = (LinearLayout) findViewById(R.id.storeinfo_info_bookmark);
//        info_evaluation = (LinearLayout) findViewById(R.id.storeinfo_info_evaluation);
//
//        info_review = (LinearLayout) findViewById(R.id.storeinfo_info_review);
//        info_review.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityStart(new Intent(baceContext, ReviewWriteActivity.class), null);
//            }
//        });
//

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String subpath = intent.getStringExtra("subpath");

        initView();
        fetchData(id, subpath);
    }

    private void fetchData(String id, String subpath) {
        progressON();

        ServiceGenerator.getInstance().createService().create(APIService.class).getStoreInfo(subpath, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StoreInfoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StoreInfoModel storeInfoModel) {
                        setStoreInfo(storeInfoModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        progressOFF();
                    }

                    @Override
                    public void onComplete() {
                        progressOFF();
                    }
                });
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_URL = "section_url";

        public PlaceholderFragment() {
        }

        public static StoreInfoActivity.PlaceholderFragment newInstance(int sectionNumber, String sectionUrl) {
            StoreInfoActivity.PlaceholderFragment fragment = new StoreInfoActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SECTION_URL, sectionUrl);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.storeinfo_image_fragment, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.image_view);
            BaseApplication.getInstance().loadImage(getActivity(), getArguments().getString(ARG_SECTION_URL), imageView, false, BaseApplication.getInstance().DEFAULT_ORIGINAL);
            return rootView;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            if (isVisibleToUser) {

            } else {

            }
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<String> stroeImageList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm, List<String> stroeImageList) {
            super(fm);
            this.stroeImageList.addAll(stroeImageList);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position, this.stroeImageList.get(position));
        }

        @Override
        public int getCount() {
            return stroeImageList.size();
        }
    }

    public class ReviewImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView reviewImage;

        public ReviewImageViewHolder(View itemView) {
            super(itemView);
            reviewImage = (ImageView) itemView.findViewById(R.id.storeinfo_recyler_item_image);
        }
    }

    public class ReviewImageRecyclerViewDataAdapter extends RecyclerView.Adapter<ReviewImageViewHolder> implements View.OnClickListener {
        private Context mContext;
        private List<Instum> viewItemList = new ArrayList<>();

        public ReviewImageRecyclerViewDataAdapter() {
        }

        @Override
        public ReviewImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.storeinfo_image_review_recycler_item, parent, false);
            ReviewImageViewHolder ret = new ReviewImageViewHolder(itemView);
            itemView.setOnClickListener(this);
            return ret;
        }

        @Override
        public void onBindViewHolder(ReviewImageViewHolder holder, int position) {
            loadImage(mContext, viewItemList.get(position).getIMGURL(), holder.reviewImage, false, BaseApplication.getInstance().LIST_HORIZONTAL);
        }

        @Override
        public int getItemCount() {
            return viewItemList.size();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(StoreInfoActivity.this, ImageViewPagerActivity.class);
            intent.putParcelableArrayListExtra(INTENT_REVIEW, (ArrayList<? extends Parcelable>) this.viewItemList);
            ActivityStart(intent, null);
        }

        public void fetchData(List<Instum> instums) {
            viewItemList.addAll(instums);
            this.notifyDataSetChanged();
        }
    }

    private void initView() {
        mViewpagerImageCount = (TextView) findViewById(R.id.viewpager_image_count_txt);
        mViewPager = (ImageViewPager) findViewById(R.id.viewpager_container);

        ratingBar = (RatingBar) findViewById(R.id.storeinfo_info_rating);
        ratingBar.setStepSize(0.1f);

        storeEvalute = (TextView) findViewById(R.id.storeinfo_info_evaluate_count);
        storeName = (TextView) findViewById(R.id.storeinfo_info_name);
        storeAddress = (TextView) findViewById(R.id.storeinfo_info_address);
        storeType = (TextView) findViewById(R.id.storeinfo_info_type);
        storeReviewCount = (TextView) findViewById(R.id.storeinfo_review_count);
        storeBookmarkCount = (TextView) findViewById(R.id.storeinfo_bookmark_count);
        storeDetailAddress = (TextView) findViewById(R.id.storeinfo_detail_address);
        storePhoneNumber = (TextView) findViewById(R.id.storeinfo_phone_number);
        storeBusinessHours = (TextView) findViewById(R.id.storeinfo_business_hours);
        storeDayOff = (TextView) findViewById(R.id.storeinfo_day_off);
        storeContent = (TextView) findViewById(R.id.storeinfo_info_content);

        info_review = (LinearLayout) findViewById(R.id.storeinfo_info_review);
        info_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityStart(new Intent(baceContext, ReviewWriteActivity.class), null);
            }
        });
    }

    private void setStoreInfo(StoreInfoModel storeInfo) {

        // 임시 아직 List로 데이터 값 없음
        final List<String> stroeImageList = new ArrayList<>();
        stroeImageList.add(storeInfo.getStore().getRecentImage());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), stroeImageList);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewpagerImageCount.setText((mViewPager.getCurrentItem() + 1) + "/" + stroeImageList.size());
            }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        ratingBar.setRating(Float.valueOf(storeInfo.getStore().getStarRating()));
        storeEvalute.setText(storeInfo.getStore().getRater());
        storeName.setText(storeInfo.getStore().getNAME());
        storeAddress.setText(storeInfo.getStore().getLAreaNm());
        storeType.setText(storeInfo.getStore().getCategoryLName() + "·" + storeInfo.getStore().getCategoryName());
        storeReviewCount.setText(storeInfo.getStore().getBlogReviewCount());
        storeBookmarkCount.setText(storeInfo.getStore().getPickCount());
        storeDetailAddress.setText(storeInfo.getStore().getAddr());
        storePhoneNumber.setText(storeInfo.getStore().getPhone());
        storeBusinessHours.setText(storeInfo.getStore().getBusinessHours());
        storeDayOff.setText(storeInfo.getStore().getDayOff());

        String paking = "";
        String valetPaking = "";
        String wifi = "";
        String reservation = "";

        if (storeInfo.getStore().getParking() != null && storeInfo.getStore().getParking().equals("Y"))
            paking = "주차가능";

        if (storeInfo.getStore().getValetParking() != null && storeInfo.getStore().getValetParking().equals("Y"))
            valetPaking = "발렛파킹";

        if (storeInfo.getStore().getWifi() != null && storeInfo.getStore().getWifi().equals("Y"))
            wifi = "무선인터넷";

        if (storeInfo.getStore().getReservation() != null && storeInfo.getStore().getReservation().equals("Y"))
            reservation = "예약가능";

        storeContent.setText(String.format("%s %s %s %s", paking, valetPaking, wifi, reservation));

        // Review Image
        storeInfoReviewRecyclerView = (RecyclerView) findViewById(R.id.storeinfo_review_recycler_view);
        storeInfoReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        storeInfoRecyclerViewDataAdapter = new ReviewImageRecyclerViewDataAdapter();
        storeInfoReviewRecyclerView.setAdapter(storeInfoRecyclerViewDataAdapter);
        storeInfoReviewRecyclerView.setNestedScrollingEnabled(false);
        storeInfoReviewRecyclerView.setHasFixedSize(true);
        storeInfoReviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 15, 0, 0));

        storeInfoRecyclerViewDataAdapter.fetchData(storeInfo.getStore().getInsta());
    }


    private void setReview() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        reviewRecyclerView = (RecyclerView) findViewById(R.id.review_recycler_view);
        reviewRecyclerView.setLayoutManager(layoutManager);
        reviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(reviewItemList);
        reviewRecyclerView.setAdapter(reviewRecyclerViewAdapter);
        reviewRecyclerView.setFocusable(false);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));
    }

    private void setSocialReview() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        socialReviewRecyclerView = (RecyclerView) findViewById(R.id.socialreview_recycler_view);
        socialReviewRecyclerView.setLayoutManager(layoutManager);
        socialReviewRecyclerViewAdapter = new SocialReviewRecyclerViewAdapter(socialReviewItemList);
        socialReviewRecyclerView.setAdapter(socialReviewRecyclerViewAdapter);
        socialReviewRecyclerView.setFocusable(false);
        socialReviewRecyclerView.setHasFixedSize(true);
        socialReviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));
        socialReviewRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));
    }

    private void getIninData() {
        // dummydata
        reviewItemList = new ArrayList<ReviewItem>();
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));
        reviewItemList.add(new ReviewItem("http://image.chosun.com/sitedata/image/201711/28/2017112802484_0.jpg", "", "", "", ""));

        // dummydata
        socialReviewItemList = new ArrayList<SocialReviewItem>();
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
        socialReviewItemList.add(new SocialReviewItem("http://img.sbs.co.kr/newimg/news/20170622/201061239_1280.jpg", "", "", "", ""));
    }
}


