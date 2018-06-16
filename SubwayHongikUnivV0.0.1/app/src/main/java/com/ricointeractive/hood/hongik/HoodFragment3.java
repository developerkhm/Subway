package com.ricointeractive.hood.hongik;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.subwaydemosinchonstationmap.R;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * A simple {@link Fragment} subclass.
 */
public class HoodFragment3 extends Fragment {
    private LinearLayout mBottom;
    private ImageView mHead, mLineTitle, mImg_Main;
    public int mImg_res;
    private ImageView mLine_1, mLine_2, mLine_3, mLine_4, mLine_5, mLine_6, mLine_7, mLine_8, mLine_9, mLine_10;
    private int mLineRes_1, mLineRes_2, mLineRes_3, mLineRes_4, mLineRes_5, mLineRes_6, mLineRes_7, mLineRes_8, mLineRes_9;
    private ImageView[] mLine;
    private int[] mImg_LineRes;

    private int[] mLine_Resource;
    private int[] mLine_Resource_Press;

    private LineOnClickListener mLineOnClickListener;

    public HoodFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hood_fragment3, container, false);

        mHead = (ImageView) view.findViewById(R.id.img_head);   //blank
        mHead.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(2160);
        mHead.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(798);

        mLineTitle = (ImageView) view.findViewById(R.id.img_line_title);
        mLineTitle.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(2160);
        mLineTitle.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(164);
        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.hood_main_3_title, mLineTitle);

        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(2160);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(1618);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(mImg_Main);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_START);


        mBottom = (LinearLayout) view.findViewById(R.id.img_bottom);
        mBottom.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(2160);
        mBottom.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(204 + 50);

        mImg_LineRes = new int[]{R.drawable.hood_main_3_line_1,
                R.drawable.hood_main_3_line_2,
                R.drawable.hood_main_3_line_3,
                R.drawable.hood_main_3_line_4,
                R.drawable.hood_main_3_line_5,
                R.drawable.hood_main_3_line_6,
                R.drawable.hood_main_3_line_7,
                R.drawable.hood_main_3_line_8,
                R.drawable.hood_main_3_line_9
        };

        mLine_1 = (ImageView) view.findViewById(R.id.img_line1);
        mLine_2 = (ImageView) view.findViewById(R.id.img_line2);
        mLine_3 = (ImageView) view.findViewById(R.id.img_line3);
        mLine_4 = (ImageView) view.findViewById(R.id.img_line4);
        mLine_5 = (ImageView) view.findViewById(R.id.img_line5);
        mLine_6 = (ImageView) view.findViewById(R.id.img_line6);
        mLine_7 = (ImageView) view.findViewById(R.id.img_line7);
        mLine_8 = (ImageView) view.findViewById(R.id.img_line8);
        mLine_9 = (ImageView) view.findViewById(R.id.img_line9);
        mLine_10 = (ImageView) view.findViewById(R.id.img_line10);

        mLine = new ImageView[]{mLine_1, mLine_2, mLine_3, mLine_4, mLine_5,
                mLine_6, mLine_7, mLine_8, mLine_9, mLine_10};

        mLine_Resource = new int[]{
                R.drawable.hood_main_3_btn_1_dim,
                R.drawable.hood_main_3_btn_2_dim,
                R.drawable.hood_main_3_btn_3_dim,
                R.drawable.hood_main_3_btn_4_dim,
                R.drawable.hood_main_3_btn_5_dim,
                R.drawable.hood_main_3_btn_6_dim,
                R.drawable.hood_main_3_btn_7_dim,
                R.drawable.hood_main_3_btn_8_dim,
                R.drawable.hood_main_3_btn_9_dim,
                R.drawable.hood_main_3_btn_10_blank
        };

        mLine_Resource_Press = new int[]{
                R.drawable.hood_main_3_btn_1,
                R.drawable.hood_main_3_btn_2,
                R.drawable.hood_main_3_btn_3,
                R.drawable.hood_main_3_btn_4,
                R.drawable.hood_main_3_btn_5,
                R.drawable.hood_main_3_btn_6,
                R.drawable.hood_main_3_btn_7,
                R.drawable.hood_main_3_btn_8,
                R.drawable.hood_main_3_btn_9,
                R.drawable.hood_main_3_btn_10_blank
        };

        mLineOnClickListener = new LineOnClickListener();
        for (int i = 0; i < mLine.length; i++) {
            mLine[i].setOnClickListener(mLineOnClickListener);
        }
        mImg_res = mImg_LineRes[1]; //2호선
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);


        btnReset();
        CommonUtil.getInstance().loadImage(getActivity(), mLine_Resource_Press[1], mLine[1]);

        return view;
    }

    class LineOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            btnReset();
            int imgRes = 0;
            int id = 0;
            switch (v.getId()) {
                case R.id.img_line1:
                    imgRes = mImg_LineRes[0];
                    id = 0;
                    break;
                case R.id.img_line2:
                    imgRes = mImg_LineRes[1];
                    id = 1;
                    break;
                case R.id.img_line3:
                    imgRes = mImg_LineRes[2];
                    id = 2;
                    break;
                case R.id.img_line4:
                    imgRes = mImg_LineRes[3];
                    id = 3;
                    break;
                case R.id.img_line5:
                    imgRes = mImg_LineRes[4];
                    id = 4;
                    break;
                case R.id.img_line6:
                    imgRes = mImg_LineRes[5];
                    id = 5;
                    break;
                case R.id.img_line7:
                    imgRes = mImg_LineRes[6];
                    id = 6;
                    break;
                case R.id.img_line8:
                    imgRes = mImg_LineRes[7];
                    id = 7;
                    break;
                case R.id.img_line9:
                    imgRes = mImg_LineRes[8];
                    id = 8;
                    break;

            }
            CommonUtil.getInstance().loadImage(getActivity(), mLine_Resource_Press[id], mLine[id]);
//            mLine[id].setBackgroundResource(mLine_Resource_Press[id]);
            CommonUtil.getInstance().loadImage(getContext(), imgRes, mImg_Main);
        }
    }

    private void btnReset() {
        for (int i = 0; i < mLine_Resource.length; i++) {
//            mLine[i].setBackgroundResource(mLine_Resource[i]);
            CommonUtil.getInstance().loadImage(getActivity(), mLine_Resource[i], mLine[i]);
        }
    }
}
