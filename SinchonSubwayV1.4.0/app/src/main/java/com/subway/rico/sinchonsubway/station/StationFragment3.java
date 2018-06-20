package com.subway.rico.sinchonsubway.station;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.leakcanary.RefWatcher;
import com.subway.rico.sinchonsubway.common.CommonUtil;
import com.subway.rico.sinchonsubway.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StationFragment3 extends Fragment {
    private ImageView mHead, mImg_Main;
    private int mImg_res;
    private int[] mThumbIds;
    private GridView gridview;

    private final int MAX_HEAD_IMG_W = 2160;
    private final int MAX_HEAD_IMG_H = 798;
    private final int MAX_MAIN_IMG_W = 2160;
    private final int MAX_MAIN_IMG_H = 2482;
    private final int MAX_GRID_ITEM_W = 442;
    private final int MAX_GRID_ITEM_H = 474;
    private final int MAX_GRID_MARGIN_W = 100;
    private final int MAX_GRID_MARGIN_H = 70;
    private final int MAX_GRID_W = MAX_GRID_ITEM_W+MAX_GRID_ITEM_W+MAX_GRID_ITEM_W+MAX_GRID_ITEM_W+MAX_GRID_MARGIN_W;
    private final int MAX_GRID_H = MAX_GRID_ITEM_H+MAX_GRID_ITEM_H+MAX_GRID_ITEM_H+MAX_GRID_MARGIN_H;

    public StationFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.station_fragment3, container, false);

        mHead = (ImageView) view.findViewById(R.id.img_title);    //bank
        mHead.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_W);
        mHead.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_H);

        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_IMG_H);
        mImg_res = R.drawable.station_main_1;
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);

        mImg_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gridview.getVisibility() == View.VISIBLE) {
                    gridview.setVisibility(View.GONE);
                } else {
                    gridview.setVisibility(View.VISIBLE);
                }
            }
        });

        mThumbIds = new int[]{
                R.drawable.station_main_3_grid_1,
                R.drawable.station_main_3_grid_2,
//                R.drawable.grid_btn03,
                R.drawable.station_main_3_grid_4,
                R.drawable.station_main_3_grid_5,
                R.drawable.station_main_3_grid_6,
                R.drawable.station_main_3_grid_7,
//                R.drawable.grid_btn08,
                R.drawable.station_main_3_grid_9,
                R.drawable.station_main_3_grid_10,
                R.drawable.station_main_3_grid_11,
                R.drawable.station_main_3_grid_12
        };

        gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_GRID_W);
        gridview.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_GRID_H);
        gridview.setAdapter(new ImageAdapter(getActivity()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridview.setVisibility(View.GONE);
                switch (position) {
                    case 0:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_1, mImg_Main);
                        break;
                    case 1:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_2, mImg_Main);
                        break;
                    case 2:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_3, mImg_Main);
                        break;
                    case 3:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_4, mImg_Main);
                        break;
                    case 4:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_5, mImg_Main);
                        break;
                    case 5:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_6, mImg_Main);
                        break;
                    case 6:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_7, mImg_Main);
                        break;
                    case 7:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_8, mImg_Main);
                        break;
                    case 8:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_9, mImg_Main);
                        break;
                    case 9:
                        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.station_main_3_location_10, mImg_Main);
                        break;
                }
            }
        });

        return view;
    }

    public class ImageAdapter extends BaseAdapter {

        private Context mContext;

        public ImageAdapter(Context c) {

            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return mThumbIds[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        CommonUtil.getInstance().utilGetViewWidthPx(MAX_GRID_ITEM_W), CommonUtil.getInstance().utilGetViewWidthPx(MAX_GRID_ITEM_H)));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                imageView.setPadding(1, 1, 1, 1);
            } else {

                imageView = (ImageView) convertView;
            }
            CommonUtil.getInstance().loadImage(getActivity(), mThumbIds[position], imageView);
            return imageView;
        }
    }

    public void testGridViewOnoff() {
        if (gridview != null) {
            if (gridview.getVisibility() == View.GONE)
                gridview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
