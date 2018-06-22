package com.subway.rico.hongiksubway.exit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.subway.rico.hongiksubway.MainActivity;
import com.subway.rico.hongiksubway.common.CommonUtil;
import com.subway.rico.hongiksubway.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExitFragment3 extends Fragment {

    private ImageView mImg_Title, mImg_Main, mImg_temp;

    private final int MAX_TITLE_IMG_W = 2160;
    private final int MAX_TITLEIMG_H = 798;
    private final int MAX_MAIN_W = 2160;
    private final int MAX_MAIN_H = 1146;
    private final int MAX_AD_W = 2160;
    private final int MAX_AD_H = 1974;
    private int mImg_res;

    public ExitFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit_fragment3, container, false);

        if (MainActivity.HOIKMAIN_FLAG == 3)
            mImg_res = R.drawable.exit_main_3_1;
        if (MainActivity.HOIKMAIN_FLAG == 5)
            mImg_res = R.drawable.exit_main_3_2;


        mImg_Title = (ImageView) view.findViewById(R.id.img_title);
        mImg_Title.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_TITLE_IMG_W);
        mImg_Title.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_TITLEIMG_H);
        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.exit_head, mImg_Title);

        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_H);
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);

        mImg_temp = (ImageView) view.findViewById(R.id.img_temp);
        mImg_temp.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_AD_W);
        mImg_temp.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_AD_H);
        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.exit_main_3_ad_sample, mImg_temp);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
