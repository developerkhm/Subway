package com.subway.rico.sinchonsubway.station;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.leakcanary.RefWatcher;
import com.subway.rico.sinchonsubway.common.CommonUtil;
import com.subway.rico.sinchonsubway.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class StationFragment4 extends Fragment {
    private ImageView mHead, mImg_Main;
    private int mImg_res;
    private PhotoViewAttacher mAttacher;

    private final int MAX_HEAD_IMG_W = 2160;
    private final int MAX_HEAD_IMG_H = 798;
    private final int MAX_MAIN_IMG_W = 2160;
    private final int MAX_MAIN_IMG_H = 2482;

    public StationFragment4() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.station_fragment4, container, false);

        mHead = (ImageView) view.findViewById(R.id.img_title);    //bank
        mHead.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_W);
        mHead.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_H);


        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_H);
        mImg_res = R.drawable.station_main_4;
        mAttacher = new PhotoViewAttacher(mImg_Main);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_XY);
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
