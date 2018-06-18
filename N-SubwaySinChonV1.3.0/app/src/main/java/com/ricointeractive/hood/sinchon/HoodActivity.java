package com.ricointeractive.hood.sinchon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.exit.sinchon.ExitActivity;
import com.ricointeractive.main.MainActivity;
import com.ricointeractive.subwaydemosinchonstationmap.R;

import org.apache.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

public class HoodActivity extends AppCompatActivity {

    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private final int MAX_HEAD_IMG_W = 2160;
    private final int MAX_HEAD_IMG_H = 798;

    public static int mModeNo = 1;

    private LinearLayout mImg;
    private ImageView mGlobal;
    public static ImageView mHead;

    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;

    private int mFullScreenY;
    private int mScreenY;   // 메인 스크린에 들어가는 큰 이미지의 높이

    private int mBtnHeight;

    private FrameLayout mFrame;
    private int mScrollY;

    private final Handler handler = new Handler();

    private FragmentManager mFragmentManager;
    private HoodFragment1 mContentFragment1;
    private HoodFragment2 mContentFragment2;
    private HoodFragment3 mContentFragment3;
    private HoodFragment4 mContentFragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CommonUtil.getInstance().prePareView(HoodActivity.this);
        super.onCreate(savedInstanceState);

        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());

        setContentView(R.layout.hood_main);
        initView();
        btnsSet();
    }

    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        mContentFragment1 = new HoodFragment1();
        mContentFragment2 = new HoodFragment2();
        mContentFragment3 = new HoodFragment3();
        mContentFragment4 = new HoodFragment4();



        mBtn1 = findViewById(R.id.button1);
        mBtn2 = findViewById(R.id.button2);
        mBtn3 = findViewById(R.id.button3);
        mBtn4 = findViewById(R.id.button4);

        mBtn1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mModeNo = 1;
                btnsSet();
                mFragmentManager.beginTransaction().replace(R.id.content_fragment, mContentFragment1).commit();
                return false;
            }
        });
        mBtn2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mModeNo = 2;
                btnsSet();
                mFragmentManager.beginTransaction().replace(R.id.content_fragment, mContentFragment2).commit();
                return false;
            }
        });
        mBtn3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mModeNo = 3;
                btnsSet();
                mFragmentManager.beginTransaction().replace(R.id.content_fragment, mContentFragment3).commit();
                return false;
            }
        });
        mBtn4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mModeNo = 4;
                btnsSet();
                mFragmentManager.beginTransaction().replace(R.id.content_fragment, mContentFragment4).commit();
                mContentFragment4.testGridViewOnoff();
                return false;
            }
        });

        ////    버튼 높이를 계산해서 적용
        mBtnHeight = CommonUtil.getInstance().getHomeBtnHeightPx();
        mBtn1.getLayoutParams().height = mBtnHeight;
        mBtn2.getLayoutParams().height = mBtnHeight;
        mBtn3.getLayoutParams().height = mBtnHeight;
        mBtn4.getLayoutParams().height = mBtnHeight;

        mHead = (ImageView) findViewById(R.id.img_title);
        mHead.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_W);
        mHead.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_H);
        CommonUtil.getInstance().loadImage(this, R.drawable.hood_head, mHead);

        ////    스크롤 높이를 계산해서 적용
        mFrame = (FrameLayout) findViewById(R.id.screen_frame);
        mFullScreenY = CommonUtil.getInstance().getPhoneHeightPx();              // 유효 풀 스크린 높이 구하기
//        mScrollY = mFullScreenY - mBtnHeight;           // 스크롤 영역의 크기 구하기
        mScrollY = mFullScreenY;           // 스크롤 영역의 크기 구하기
        mFrame.getLayoutParams().height = mScrollY;     // 스크롤 크기를 스크롤에 지정

        ////    스크롤 내에 들어갈 컨텐츠 이미지 세팅
        mScreenY = CommonUtil.getInstance().getScreenImageHeightPx();            // 메인 스크린에 들어가는 큰 이미지의 높이 구하기
        mImg = (LinearLayout) findViewById(R.id.img);       // 구하고 나서 이미지 참조하고
        mImg.getLayoutParams().height = mScreenY;       // 거기에 높이를 적용

        mGlobal = (ImageView) findViewById(R.id.btn_language);
        mGlobal.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(CommonUtil.getInstance().GLOBAL_IMG_WIDTH_PX);
        mGlobal.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(CommonUtil.getInstance().GLOBAL_IMG_HEIGHT_PX);
        Glide.with(this).load(R.drawable.common_global).into(mGlobal);

        mGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.getInstance().callToast(HoodActivity.this);
            }
        });
    }

    private void btnsSet() {
        mBtn1.setBackgroundResource(R.drawable.hood_tab1_selector);
        mBtn2.setBackgroundResource(R.drawable.hood_tab2_selector);
        mBtn3.setBackgroundResource(R.drawable.hood_tab3_selector);
        mBtn4.setBackgroundResource(R.drawable.hood_tab4_selector);
        mHead.setVisibility(View.VISIBLE);
        switch (mModeNo) {
            case 1:
                mBtn1.setBackgroundResource(R.drawable.hood_tab1_dim);
                break;
            case 2:
                mBtn2.setBackgroundResource(R.drawable.hood_tab2_dim);
                mHead.setVisibility(View.GONE); //임시
                break;
            case 3:
                mBtn3.setBackgroundResource(R.drawable.hood_tab3_dim);
                break;
            case 4:
                mBtn4.setBackgroundResource(R.drawable.hood_tab4_dim);
                break;
        }
    }

    public Thread.UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
        return mDefaultUncaughtExceptionHandler;
    }

    private class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            // Try everything to make sure this process goes away.
            // android.os.Process.killProcess(android.os.Process.myPid());
            // System.exit(10);
            // Intent restartIntent = new Intent(getApplicationContext(), MainActivity.class);
            // PendingIntent runner = PendingIntent.getActivity(getApplicationContext(), 99, restartIntent, PendingIntent.FLAG_ONE_SHOT);
            // AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE); am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000, runner);

            Logger logger = Logger.getLogger(HoodActivity.class.getSimpleName());
            logger.info(e.getMessage());

            mDefaultUncaughtExceptionHandler.uncaughtException(t, e);
        }
    }
}
