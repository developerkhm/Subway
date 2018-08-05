package com.skt.tmaphot.activity.main.review.more;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;

import java.util.ArrayList;

public class RealReview {

    private GridView gridview;
    private Point size;
    private int width;
    private int height;
    private boolean mLockListView;
    private ArrayList<String> mRowList;
    protected ImageAdapter imageAdapter;

    public Context mContext;
    public View rootView;
    public android.os.Handler handler;


    public RealReview(Context mContext, View rootView, Handler handler) {
        this.mContext = mContext;
        this.rootView = rootView;
        this.handler = handler;

        init();
    }

    private void init() {

        mRowList = new ArrayList<String>();

        setDummyData();
        gridview = (GridView) rootView.findViewById(R.id.simpleGridView);
        size = new Point();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(size);
        width = size.x;
        height = size.y;

        imageAdapter = new ImageAdapter();
        gridview.setAdapter(imageAdapter);
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

//                Log.e("GridView", "onScrollStateChanged : " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                Log.e("GridView", "firstVisibleItem: " + firstVisibleItem + "\nLastVisibleItem: " + totalItemCount);

                // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
                // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
                int count = totalItemCount - visibleItemCount;

                if (firstVisibleItem >= count && totalItemCount != 0 && mLockListView == false) {
                    setDummyData();
                }
            }
        });
    }

    private class ImageAdapter extends BaseAdapter {

        public ImageAdapter() {
        }

        public int getCount() {
            return mRowList.size();
        }

        public Object getItem(int position) {
            return mRowList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(parent.getContext());
                imageView.setLayoutParams(new GridView.LayoutParams(
                        width / 3, width / 3));
//                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                imageView.setPadding(1, 1, 1, 1);
            } else {

                imageView = (ImageView) convertView;
            }

            BaseApplication.getInstance().loadImage(parent.getContext(), mRowList.get(position), imageView, false);
            return imageView;
        }
    }

    private void setDummyData() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                mLockListView = true;

                if (mRowList == null)
                    mRowList = new ArrayList<String>();

                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");
                mRowList.add("https://picksell.co.kr/images/product/128719/f18a709b-069a-4a3a-b74d-9b36a3600204.jpg");

                mLockListView = false;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}



