package com.ricointeractive.station.sinchon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.subwaydemosinchonstationmap.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StationFragment2 extends Fragment {
    private ImageView mHead, mImg_Main;
    private LinearLayout mImgBtn;
    private int mImg_res;
    private ImageButton mBtn1, mBtn2, mBtn3;

    private final int MAX_HEAD_IMG_W = 2160;
    private final int MAX_HEAD_IMG_H = 798;
    private final int MAX_HEAD_ITEM_W = 2160;
    private final int MAX_HEAD_ITEM_H = 251;
    private final int MAX_HEAD_BTN_W = 640;
    private final int MAX_HEAD_BTN_H = 120;
    private final int MAX_MAIN_IMG_W = 2160;
    private final int MAX_MAIN_IMG_H = 2231;

    public StationFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.station_fragment2, container, false);

        mHead = (ImageView) view.findViewById(R.id.img_title);    //bank
        mHead.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_W);
        mHead.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_H);


        mImgBtn = (LinearLayout) view.findViewById(R.id.img_btn);
        mImgBtn.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_ITEM_W);
        mImgBtn.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_ITEM_H);

        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_H);
        mImg_res = R.drawable.station_main_2_time_1;
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);

        mBtn1 = (ImageButton) view.findViewById(R.id.button1);
        mBtn1.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_BTN_W);
        mBtn1.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_BTN_H);
        mBtn2 = (ImageButton) view.findViewById(R.id.button2);
        mBtn2.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_BTN_W);
        mBtn2.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_BTN_H);
        mBtn3 = (ImageButton) view.findViewById(R.id.button3);
        mBtn3.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_BTN_W);
        mBtn3.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_BTN_H);

        mBtn1.setOnClickListener(new DaysOnClickListener());
        mBtn2.setOnClickListener(new DaysOnClickListener());
        mBtn3.setOnClickListener(new DaysOnClickListener());

        btnReset();
        mBtn1.setBackgroundResource(R.drawable.station_main_2_time_btn_1_dim);    //초기 선택

        return view;
    }

    class DaysOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            btnReset();
            switch (v.getId()) {
                case R.id.button1:
                    CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_2_time_1, mImg_Main);
                    mBtn1.setBackgroundResource(R.drawable.station_main_2_time_btn_1_dim);
                    break;
                case R.id.button2:
                    CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_2_time_2, mImg_Main);
                    mBtn2.setBackgroundResource(R.drawable.station_main_2_time_btn_2_dim);
                    break;
                case R.id.button3:
                    CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_2_time_3, mImg_Main);
                    mBtn3.setBackgroundResource(R.drawable.station_main_2_time_btn_3_dim);
                    break;
            }
        }
    }

    private void btnReset() {
        mBtn1.setBackgroundResource(R.drawable.station_main_2_time_btn_1);
        mBtn2.setBackgroundResource(R.drawable.station_main_2_time_btn_2);
        mBtn3.setBackgroundResource(R.drawable.station_main_2_time_btn_3);
    }
}
