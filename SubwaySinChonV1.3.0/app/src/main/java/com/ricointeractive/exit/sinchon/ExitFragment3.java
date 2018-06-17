package com.ricointeractive.exit.sinchon;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.main.LeakCanaryApplication;
import com.ricointeractive.subwaydemosinchonstationmap.R;
import com.squareup.leakcanary.RefWatcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExitFragment3 extends Fragment {
    private ImageView mImg_Title, mImg_Main, mImg_temp;
    public int mImg_res;

    private final int MAX_TITLE_IMG_W = 2160;
    private final int MAX_TITLEIMG_H = 798;
    private final int MAX_MAIN_W = 2160;
    private final int MAX_MAIN_H = 1146;
    private final int MAX_AD_W = 2160;
    private final int MAX_AD_H = 1974;

    public ExitFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit_fragment3, container, false);
        mImg_Title = (ImageView) view.findViewById(R.id.img_title);
        mImg_Title.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_TITLE_IMG_W);
        mImg_Title.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_TITLEIMG_H);
        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.exit_head, mImg_Title);

        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_H);
        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.exit_main_3, mImg_Main);

        mImg_temp = (ImageView) view.findViewById(R.id.img_temp);
        mImg_temp.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_AD_W);
        mImg_temp.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_AD_H);
        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.exit_main_3_ad_sample, mImg_temp);

        return view;
    }

    public void testAdOnoff() {
        if (mImg_temp != null) {
            if (mImg_temp.getVisibility() == View.VISIBLE)
                mImg_temp.setVisibility(View.GONE);
            else
                mImg_temp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LeakCanaryApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
