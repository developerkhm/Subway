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
public class ExitFragment1 extends Fragment {
    private ImageView mImg_Main;
    private int mImg_res;
    private ImageView mImg_t, mImg_2, mImg_1, mImg_3, mImg_4, mImg_5, mImg_6, mImg_7, mImg_8, mImg_9;
    private ImageView[] mImgArray;
    private int[] mExit_Res;

    private final int MAX_MAIN_IMG_W = 2160;
    private final int MAX_MAIN_IMG_H = 1293;
    private final int MAX_MAIN_LIST_TITLE_W = 2160;
    private final int MAX_MAIN_LIST_TITLE_H = 161;
    private final int MAX_MAIN_LIST_W = 2160;
    private final int MAX_MAIN_LIST_H = 229;

    public ExitFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit_fragment1, container, false);

        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_H);

        if(MainActivity.HOIKMAIN_FLAG == 3)
            mImg_res = R.drawable.exit_main_1_1;
        if(MainActivity.HOIKMAIN_FLAG == 5)
            mImg_res = R.drawable.exit_main_1_2;
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);

        mExit_Res = new int[]{
                R.drawable.hood_main_2_list_title,
                R.drawable.hood_main_2_list_1,
                R.drawable.hood_main_2_list_2,
                R.drawable.hood_main_2_list_3,
                R.drawable.hood_main_2_list_4,
                R.drawable.hood_main_2_list_5,
                R.drawable.hood_main_2_list_6,
                R.drawable.hood_main_2_list_7,
                R.drawable.hood_main_2_list_8,
                R.drawable.hood_main_2_list_9
        };

        mImg_t = (ImageView) view.findViewById(R.id.imageView0);
        mImg_t.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_TITLE_W);
        mImg_t.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_TITLE_H);
        mImg_1 = (ImageView) view.findViewById(R.id.imageView1);
        mImg_1.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_1.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);
        mImg_2 = (ImageView) view.findViewById(R.id.imageView2);
        mImg_2.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_2.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);
        mImg_3 = (ImageView) view.findViewById(R.id.imageView3);
        mImg_3.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_3.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);
        mImg_4 = (ImageView) view.findViewById(R.id.imageView4);
        mImg_4.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_4.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);
        mImg_5 = (ImageView) view.findViewById(R.id.imageView5);
        mImg_5.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_5.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);
        mImg_6 = (ImageView) view.findViewById(R.id.imageView6);
        mImg_6.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_6.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);
        mImg_7 = (ImageView) view.findViewById(R.id.imageView7);
        mImg_7.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_7.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);
        mImg_8 = (ImageView) view.findViewById(R.id.imageView8);
        mImg_8.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_8.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);
        mImg_9 = (ImageView) view.findViewById(R.id.imageView9);
        mImg_9.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_W);
        mImg_9.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_LIST_H);

        mImgArray = new ImageView[]{mImg_t, mImg_1, mImg_2, mImg_3, mImg_4, mImg_5, mImg_6, mImg_7, mImg_8, mImg_9};

        for (int i = 0; i < mExit_Res.length; i++) {
            CommonUtil.getInstance().loadImage(getActivity(), mExit_Res[i], mImgArray[i]);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
