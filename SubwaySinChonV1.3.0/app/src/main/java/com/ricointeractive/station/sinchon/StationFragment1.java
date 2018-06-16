package com.ricointeractive.station.sinchon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.subwaydemosinchonstationmap.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StationFragment1 extends Fragment {
    private ImageView mHead, mImg_Main;
    private int mImg_res;

    private final int MAX_HEAD_IMG_W = 2160;
    private final int MAX_HEAD_IMG_H = 798;
    private final int MAX_MAIN_IMG_W = 2160;
    private final int MAX_MAIN_IMG_H = 2482;

    public StationFragment1() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.station_fragment1, container, false);

        mHead = (ImageView) view.findViewById(R.id.img_title);    //bank
        mHead.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_W);
        mHead.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_H);

        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_H);

        mImg_res = R.drawable.station_main_1;
        CommonUtil.getInstance().loadImage( getActivity(), mImg_res, mImg_Main);
        return view;
    }
}
