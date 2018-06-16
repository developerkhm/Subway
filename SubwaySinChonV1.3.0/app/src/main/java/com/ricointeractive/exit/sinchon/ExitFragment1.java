package com.ricointeractive.exit.sinchon;


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
public class ExitFragment1 extends Fragment {
    private ImageView mTitle, mImg_Main;
    private int mImg_res;

    public ExitFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit_fragment1, container, false);

        mImg_res = R.drawable.exit_main_1;
        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(CommonUtil.getInstance().MAX_WIDTH_PX);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().getScreenImageHeightPx(); //헤더없는 높이
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);

        return view;
    }
}
