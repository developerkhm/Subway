package com.skt.tmaphot.activity.main.review.more;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.skt.tmaphot.R;
import com.skt.tmaphot.BaseActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RealReviewActivity extends BaseActivity {

    private Handler handler = new Handler();
//    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        LinearLayout linearLayout = (LinearLayout) inflater.inflate( R.layout.realreview_layout, null );
        setContentView( linearLayout );

        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

        RealReview realReview = new RealReview(baceContext,linearLayout, handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        executorService.shutdown(); //스레드풀 종료
    }
}


