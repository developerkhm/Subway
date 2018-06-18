package com.ricointeractive.station.sinchon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;
import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.main.LeakCanaryApplication;
import com.ricointeractive.subwaydemosinchonstationmap.R;
import com.squareup.leakcanary.RefWatcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class StationFragment1 extends Fragment {
    private ImageView mHead, mImg_Main;
    private int mImg_res;
    private LinearLayout maptest;

    private final int MAX_HEAD_IMG_W = 2160;
    private final int MAX_HEAD_IMG_H = 798;
    private final int MAX_MAIN_IMG_W = 2160;
    private final int MAX_MAIN_IMG_H = 2482;

    private NMapContext mMapContext;

    public StationFragment1() {
    }

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
        mImg_Main.setVisibility(View.GONE);

        maptest = (LinearLayout) view.findViewById(R.id.img_map);
        maptest.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_W);
        maptest.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_H);

        mImg_res = R.drawable.station_main_1;
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext = new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NMapView mapView = (NMapView) getView().findViewById(R.id.mapView);
        mapView.setClientId("PJ8ZdZcFP19If1yVg9wu");// 클라이언트 아이디 설정
//        mapView.setClickable(true);
//        mapView.setEnabled(true);
//        mapView.setFocusable(true);
//        mapView.setFocusableInTouchMode(true);
//        mapView.setScalingFactor(1.5f);
//        mapView.requestFocus();

        mMapContext.setupMapView(mapView);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapContext.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }

    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LeakCanaryApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
