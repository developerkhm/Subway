package com.subway.rico.hongiksubway.hood;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.subway.rico.hongiksubway.MainActivity;
import com.subway.rico.hongiksubway.common.CommonUtil;
import com.subway.rico.hongiksubway.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HoodFragment2 extends Fragment {
    private ImageView mImg_Main;
    private int mImg_res;
    private ImageView mImg_t, mImg_2, mImg_1, mImg_3, mImg_4, mImg_5, mImg_6, mImg_7, mImg_8, mImg_9;
    private ImageView mImg_1_1, mImg_2_1, mImg_3_1, mImg_4_1, mImg_6_1, mImg_8_1, mImg_9_1;
    private ImageView[] mImgArray, mImgArray2;
    private int[] mExit_Res, mExit_Res2;

    private ScrollView mMain_scroll;

    private final int MAX_MAIN_IMG_W = 2160;
    private final int MAX_MAIN_IMG_H = 1146;
    private final int MAX_MAIN_LIST_TITLE_W = 2160;
    private final int MAX_MAIN_LIST_TITLE_H = 161;
    private final int MAX_MAIN_LIST_W = 2160;
    private final int MAX_MAIN_LIST_H = 229;


    private Context mContext;

    private View.OnClickListener mOnClickListener;

    public HoodFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hood_fragment2, container, false);

        mContext = getActivity();

        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_H);

        if(MainActivity.HOIKMAIN_FLAG == 2)
            mImg_res = R.drawable.hood_main_2;
        if(MainActivity.HOIKMAIN_FLAG == 4)
            mImg_res = R.drawable.hood_main_2_1;




        CommonUtil.getInstance().loadImage(mContext, mImg_res, mImg_Main);

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

        mExit_Res2 = new int[]{
                R.drawable.hood_main_2_list_1_1,
                R.drawable.hood_main_2_list_2_1,
                R.drawable.hood_main_2_list_3_1,
                R.drawable.hood_main_2_list_4_1,
                R.drawable.hood_main_2_list_6_1,
                R.drawable.hood_main_2_list_8_1,
                R.drawable.hood_main_2_list_9_1
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

        mImg_1_1 = (ImageView) view.findViewById(R.id.imageView1_1);
        mImg_2_1 = (ImageView) view.findViewById(R.id.imageView2_1);
        mImg_3_1 = (ImageView) view.findViewById(R.id.imageView3_1);
        mImg_4_1 = (ImageView) view.findViewById(R.id.imageView4_1);
        mImg_6_1 = (ImageView) view.findViewById(R.id.imageView6_1);
        mImg_8_1 = (ImageView) view.findViewById(R.id.imageView8_1);
        mImg_9_1 = (ImageView) view.findViewById(R.id.imageView9_1);

        mOnClickListener = new ExitsOnClickListener();

        mImgArray = new ImageView[]{mImg_t, mImg_1, mImg_2, mImg_3, mImg_4, mImg_5, mImg_6, mImg_7, mImg_8, mImg_9};
        mImgArray2 = new ImageView[]{mImg_1_1, mImg_2_1, mImg_3_1, mImg_4_1, mImg_6_1, mImg_8_1, mImg_9_1};

        for (int i = 0; i < mExit_Res.length; i++) {
            CommonUtil.getInstance().loadImage(mContext, mExit_Res[i], mImgArray[i]);
        }

        for (int i = 0; i < mExit_Res2.length; i++) {
            CommonUtil.getInstance().loadImage(mContext, mExit_Res2[i], mImgArray2[i]);
        }

        for (int i = 0; i < mImgArray.length; i++) {
            mImgArray[i].setOnClickListener(mOnClickListener);
        }


        mMain_scroll = (ScrollView) view.findViewById(R.id.main_scroll);

//        mMain_scroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            int scrollY = 0;
//
//            @Override
//            public void onScrollChanged() {
//                scrollY = mMain_scroll.getScrollY(); // For ScrollView
//                mMain_scroll.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        HoodActivity.mHead.setY(HoodActivity.mHead.getY() - scrollY);
//                    }
//                });
//            }
//        });

        return view;
    }

    class ExitsOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.imageView1:
                    toggleView(mImg_1_1);
                    break;
                case R.id.imageView2:
                    toggleView(mImg_2_1);
                    break;
                case R.id.imageView3:
                    toggleView(mImg_3_1);
                    break;
                case R.id.imageView4:
                    toggleView(mImg_4_1);
                    break;
                case R.id.imageView6:
                    toggleView(mImg_6_1);
                    break;
                case R.id.imageView8:
                    toggleView(mImg_8_1);
                    break;
                case R.id.imageView9:
                    toggleView(mImg_9_1);
                    break;
            }
        }
    }

    private void toggleView(ImageView view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
