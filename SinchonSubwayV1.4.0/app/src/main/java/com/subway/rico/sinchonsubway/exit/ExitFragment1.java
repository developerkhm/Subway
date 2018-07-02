package com.subway.rico.sinchonsubway.exit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.subway.rico.sinchonsubway.R;
import com.subway.rico.sinchonsubway.common.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExitFragment1 extends Fragment {
    @BindView(R.id.img_main)
    ImageView mImg_Main;

    private int mImg_res = R.drawable.exit_main_1;

    public ExitFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit_fragment1, container, false);
        ButterKnife.bind(this, view);

        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(CommonUtil.getInstance().MAX_WIDTH_PX);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().getScreenImageHeightPx(); //헤더없는 높이
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
