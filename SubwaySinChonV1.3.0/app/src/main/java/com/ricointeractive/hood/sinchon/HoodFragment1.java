package com.ricointeractive.hood.sinchon;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ZoomControls;

import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.subwaydemosinchonstationmap.R;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * A simple {@link Fragment} subclass.
 */
public class HoodFragment1 extends Fragment {
    private final float MAX_SCALE = 5f;
    private final float MIN_SCALE = 1f;
    private ImageView mImg_Main;
    private int mImg_res;
    private ZoomControls simpleZoomControls;
    private PhotoViewAttacher mAttacher;

    public HoodFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hood_fragment1, container, false);
        mImg_Main = (ImageView) view.findViewById(R.id.img_main);

        mImg_res = R.drawable.hood_main_1;

        mAttacher = new PhotoViewAttacher(mImg_Main);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_START);
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);

        simpleZoomControls = (ZoomControls) view.findViewById(R.id.simpleZoomControl);
        simpleZoomControls.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttacher.getScale() >= MAX_SCALE) {
                    return;
                }
                mAttacher.setScale(mAttacher.getScale() + 0.5f);
            }
        });
        simpleZoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAttacher.getScale() <= MIN_SCALE) {
                    return;
                }
                mAttacher.setScale(mAttacher.getScale() - 0.5f);
            }
        });
        return view;
    }
}
