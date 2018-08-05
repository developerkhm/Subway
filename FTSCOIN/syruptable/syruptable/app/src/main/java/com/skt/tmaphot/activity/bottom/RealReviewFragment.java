package com.skt.tmaphot.activity.bottom;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.review.more.RealReview;
import com.skt.tmaphot.fragment.BaseFragment;

public class RealReviewFragment extends BaseFragment {

    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.realreview_layout, container, false);
        rootView.findViewById(R.id.toolbar).setVisibility(View.GONE);
        RealReview realReview = new RealReview(getContext(),rootView, handler);
        return rootView;
    }
}
