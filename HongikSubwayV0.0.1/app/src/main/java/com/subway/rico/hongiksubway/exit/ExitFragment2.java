package com.subway.rico.hongiksubway.exit;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.subway.rico.hongiksubway.common.CommonUtil;
import com.subway.rico.hongiksubway.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExitFragment2 extends Fragment {
    private LinearLayout mFrame;
    private ImageView mImg_Main;
    private int mImg_res, mImg_res_3, mImg_res_4, mImg_res_5;
    private ImageView mImg_t, mImg_2, mImg_1, mImg_3, mImg_4, mImg_5, mImg_6, mImg_7, mImg_8;
    private ImageView mImg_3_1, mImg_4_1, mImg_5_1;
    private ImageView[] mImgArray, mImgArray2;
    private int[] mExit_Res, mExit_Res2;


    private final int MAX_MAIN_IMG_W = 2160;
    private final int MAX_MAIN_IMG_H = 1904;
    private final int MAX_MAIN_LIST_TITLE_W = 2160;
    private final int MAX_MAIN_LIST_TITLE_H = 161;
    private final int MAX_MAIN_LIST_W = 2160;
    private final int MAX_MAIN_LIST_H = 229;


    private Context mContext;

    private View.OnClickListener mOnClickListener;

    public ExitFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit_fragment2, container, false);

        mContext = getActivity();
        mImg_res = R.drawable.exit_main_2;
        mImg_res_3 = R.drawable.exit_main_2_3;
        mImg_res_4 = R.drawable.exit_main_2_4;
        mImg_res_5 = R.drawable.exit_main_2_5;

        mFrame = (LinearLayout) view.findViewById(R.id.frame_main);
//        mFrame.requestTransparentRegion(mFrame);
        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_H);
        CommonUtil.getInstance().loadImage(mContext, mImg_res, mImg_Main);

        mExit_Res = new int[]{
                R.drawable.exit_main_2_list_title,
                R.drawable.exit_main_2_list_1,
                R.drawable.exit_main_2_list_2,
                R.drawable.exit_main_2_list_3,
                R.drawable.exit_main_2_list_4,
                R.drawable.exit_main_2_list_5,
                R.drawable.exit_main_2_list_6,
                R.drawable.exit_main_2_list_7,
                R.drawable.exit_main_2_list_8
        };

        mExit_Res2 = new int[]{
                R.drawable.exit_main_2_list_3_1,
                R.drawable.exit_main_2_list_4_1,
                R.drawable.exit_main_2_list_5_1,
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


        mImg_3_1 = (ImageView) view.findViewById(R.id.imageView3_1);
        mImg_4_1 = (ImageView) view.findViewById(R.id.imageView4_1);
        mImg_5_1 = (ImageView) view.findViewById(R.id.imageView5_1);


        mOnClickListener = new ExitsOnClickListener();

        mImgArray = new ImageView[]{ mImg_t,  mImg_1,  mImg_2, mImg_3, mImg_4, mImg_5, mImg_6, mImg_7, mImg_8};
        mImgArray2 = new ImageView[]{mImg_3_1, mImg_4_1, mImg_5_1};

        for (int i = 0; i < mExit_Res.length; i++) {
            CommonUtil.getInstance().loadImage(mContext, mExit_Res[i], mImgArray[i]);
        }

        for (int i = 0; i < mExit_Res2.length; i++) {
            CommonUtil.getInstance().loadImage(mContext, mExit_Res2[i], mImgArray2[i]);
        }


        for (int i = 0; i < mImgArray.length; i++) {
            mImgArray[i].setOnClickListener(mOnClickListener);
        }



        return view;
    }

    class ExitsOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.imageView1:
                    CommonUtil.getInstance().loadImage(mContext, mImg_res, mImg_Main);
                    break;
                case R.id.imageView2:
                    CommonUtil.getInstance().loadImage(mContext, mImg_res, mImg_Main);
                    break;
                case R.id.imageView3:
                    toggleView(mImg_3_1);
                    CommonUtil.getInstance().loadImage(mContext, mImg_res_3, mImg_Main);
                    break;
                case R.id.imageView4:
                    toggleView(mImg_4_1);
                    CommonUtil.getInstance().loadImage(mContext, mImg_res_4, mImg_Main);
                    break;
                case R.id.imageView5:
                    toggleView(mImg_5_1);
                    CommonUtil.getInstance().loadImage(mContext, mImg_res_5, mImg_Main);
                    break;
                case R.id.imageView6:
                    CommonUtil.getInstance().loadImage(mContext, mImg_res, mImg_Main);
                    CommonUtil.getInstance().callToast(getActivity());
                    break;
                case R.id.imageView7:
                    CommonUtil.getInstance().loadImage(mContext, mImg_res, mImg_Main);
                    CommonUtil.getInstance().callToast(getActivity());
                    break;
                case R.id.imageView8:
                    CommonUtil.getInstance().loadImage(mContext, mImg_res, mImg_Main);
                    CommonUtil.getInstance().callToast(getActivity());
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
