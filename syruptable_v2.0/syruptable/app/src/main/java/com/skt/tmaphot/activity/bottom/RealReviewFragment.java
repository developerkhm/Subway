package com.skt.tmaphot.activity.bottom;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.review.more.RealReview;
import com.skt.tmaphot.fragment.BaseFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RealReviewFragment extends BaseFragment {

    private Handler handler = new Handler();
//    private ExecutorService executorService;
    RealReview realReview;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        Log.d("TestFg",getClass().getName() + "setUserVisibleHint : " + isVisibleToUser);

        if (isVisibleToUser)
        {
            //화면에 실제로 보일때
//            ((MainActivity) getActivity()).fab.setVisibility(View.VISIBLE);
        }
        else
        {
            //preload 될때(전페이지에 있을때)
        }

        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("BCA12", "onCreateView RealReviewFragment");

        View rootView = inflater.inflate(R.layout.realreview_layout, container, false);
        rootView.findViewById(R.id.toolbar).setVisibility(View.GONE);

//        executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        realReview = new RealReview(getContext(),rootView, handler);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        executorService.shutdown(); //스레드풀 종료
    }

    public void setScrollTopFocus(){
        if(realReview != null)
            realReview.setFocusTop();
    }
}
