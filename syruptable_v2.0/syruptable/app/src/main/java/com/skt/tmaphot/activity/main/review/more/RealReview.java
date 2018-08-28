package com.skt.tmaphot.activity.main.review.more;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.MainActivity;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewItem;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RealReview {

    private GridView gridview;
    private Point size;
    private int width;
    private int height;
    private boolean mLockListView = true;
    private List<RealReviewRecyclerViewItem> mRowList;
    protected RealReveiwGridAdapter imageAdapter;
//    private ExecutorService executorService;

    public Context mContext;
    public View rootView;
    public android.os.Handler handler;


    public RealReview(Context mContext, View rootView, Handler handler) {
        this.mContext = mContext;
        this.rootView = rootView;
        this.handler = handler;
//        this.executorService = executorService;

        init();
    }

    private void init() {

        mRowList = new ArrayList<>();


        gridview = (GridView) rootView.findViewById(R.id.simpleGridView);
        size = new Point();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(size);
        width = size.x;
        height = size.y;

        imageAdapter = new RealReveiwGridAdapter(mRowList);
        gridview.setAdapter(imageAdapter);
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:

                        if (mContext instanceof MainActivity) {

                            if (((MainActivity) mContext).fab.getVisibility() == View.GONE && ((MainActivity) mContext).navigation.getVisibility() == View.GONE) {
                                ((MainActivity) mContext).fab.setVisibility(View.VISIBLE);
                            }
                        }
                        break;

                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:

                        if (mContext instanceof MainActivity) {
                            if (((MainActivity) mContext).fab.getVisibility() == View.VISIBLE) {
                                ((MainActivity) mContext).fab.setVisibility(View.GONE);
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem == 0 && mContext instanceof MainActivity) {
                    if (((MainActivity) mContext).navigation.getVisibility() == View.GONE)
                        ((MainActivity) mContext).slideUp(((MainActivity) mContext).navigation);
                }

                if (firstVisibleItem != 0 && mContext instanceof MainActivity) {
                    if (((MainActivity) mContext).navigation.getVisibility() == View.VISIBLE)
                        ((MainActivity) mContext).slideDown(((MainActivity) mContext).navigation);

//                    if (((MainActivity) mContext).fab.getVisibility() == View.VISIBLE) {
//                        ((MainActivity) mContext).fab.setVisibility(View.GONE);
//                    }

                }

                Log.e("RRRTT", "firstVisibleItem: " + firstVisibleItem + "\n visibleItemCount: " + visibleItemCount + "\n totalItemCount: " + totalItemCount);


                int count = totalItemCount - visibleItemCount;

                if (firstVisibleItem >= count && totalItemCount != 0 && mLockListView) {
                    // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
                    // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.

                    setDummyData();
                }
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RealReviewRecyclerViewItem realReviewRecyclerViewItem = (RealReviewRecyclerViewItem) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(mContext, StoreInfoActivity.class);
                intent.putExtra("id", realReviewRecyclerViewItem.getId());
                BaseApplication.getInstance().ActivityStart(intent, null);
            }
        });

        setDummyData();
    }

    private class RealReveiwGridAdapter extends BaseAdapter {

        public List<RealReviewRecyclerViewItem> viewItemList = new ArrayList<>();

        public RealReveiwGridAdapter(List<RealReviewRecyclerViewItem> viewItemList) {
            this.viewItemList.addAll(viewItemList);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            mContext = parent.getContext();
            ImageView imageView = null;

            if (convertView == null) {
                imageView = new ImageView(parent.getContext());
                imageView.setLayoutParams(new GridView.LayoutParams(width / 3, width / 3));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {

                imageView = (ImageView) convertView;
            }
            BaseApplication.getInstance().loadImage(parent.getContext(), viewItemList.get(position).getImgUrl(), imageView, false, BaseApplication.getInstance().REAL_REVIEW);

//            imageView.setOnClickListener(this);

            return imageView;
        }

        public int getCount() {
            return this.viewItemList.size();
        }

        public long getItemId(int position) {
            return position;
        }

        public Object getItem(int position) {
            return viewItemList.get(position);
        }

//        @Override
//        public void onClick(View view) {
//
//        }
    }

    public void setDummyData() {
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
                mLockListView = false;

                if (mRowList == null)
                    mRowList = new ArrayList<>();


                int currItemListSize = 0;   // 현재 리스트 사이즈
                int newItemIndex = 0;       // 아이템 끝 index
                int loadMoreItemCount = 10;  // 로드할 아이템 개수

                currItemListSize = mRowList.size();
                for (int i = currItemListSize; i < currItemListSize + loadMoreItemCount; i++) {

                    String url = "http://upload2.inven.co.kr/upload/2018/04/11/bbs/i15799604597.jpg";

                    mRowList.add(new RealReviewRecyclerViewItem(String.valueOf(i + 101), url));
                }
                newItemIndex = mRowList.size() - 1;
                imageAdapter.viewItemList.addAll(mRowList);

                mLockListView = true;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageAdapter.notifyDataSetChanged();
                    }
                });
//            }
//        });
    }

    public void setFocusTop() {
//        int index = gridview.getFirstVisiblePosition();
        gridview.smoothScrollToPosition(0);
    }
}



