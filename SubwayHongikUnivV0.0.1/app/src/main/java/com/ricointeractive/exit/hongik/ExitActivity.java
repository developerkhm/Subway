package com.ricointeractive.exit.hongik;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ricointeractive.common.CommonUtil;
import com.ricointeractive.subwaydemosinchonstationmap.R;

import org.apache.log4j.Logger;

public class ExitActivity extends AppCompatActivity {

    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    private final int MAX_HEAD_IMG_W = 2160;
    private final int MAX_HEAD_IMG_H = 798;

    private View mScreen_Main;
    private int[] mMenu_Resource;
    private int[] mMenu_Resource_Press;

    private Button mBtn1, mBtn2, mBtn3, mBtn4;
    private Button[] mBtnArray;
    private ImageView mHead, mBtn_Language;
    private LinearLayout mImg;

    private FragmentManager mFragmentManager;
    private ExitFragment1 mContentFragment1;
    private ExitFragment2 mContentFragment2;
    private ExitFragment3 mContentFragment3;
    private ExitFragment4 mContentFragment4;


    private FrameLayout mFrame;
    private int mFullScreenY;

    private int mScrollY;

    private int mBtnHeight;


    public int mScreenY = 0; //메인 스크린에 들어가는 큰 이미지의 높이

    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());

        CommonUtil.getInstance().prePareView(ExitActivity.this);
        setContentView(R.layout.exit_main);
        initView(); //view 세팅
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
//            decorView.setSystemUiVisibility(uiOption);
        }
    }

    class MeneOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Fragment content_fragment = null;
            btnReset();
            int id = v.getId();
            mBtn_Language.setVisibility(View.VISIBLE);
            switch (id) {
                case R.id.button1:
                    content_fragment = mContentFragment1;
                    mBtnArray[0].setBackgroundResource(mMenu_Resource_Press[0]);
                    break;
                case R.id.button2:
                    content_fragment = mContentFragment2;
                    mBtn2.setBackgroundResource(mMenu_Resource_Press[1]);
                    break;
                case R.id.button3:
                    mBtn_Language.setVisibility(View.GONE); //언어선택 숨김
                    content_fragment = mContentFragment3;
                    mBtn3.setBackgroundResource(mMenu_Resource_Press[2]);
                    break;
                case R.id.button4:
                    content_fragment = mContentFragment4;
                    mBtn4.setBackgroundResource(mMenu_Resource_Press[3]);
                    break;
            }
            mFragmentManager.beginTransaction().replace(R.id.content_fragment, content_fragment).commit();
        }
    }

    private void initView() {
        mFragmentManager = getSupportFragmentManager();
        mContentFragment1 = new ExitFragment1();
        mContentFragment2 = new ExitFragment2();
        mContentFragment3 = new ExitFragment3();
        mContentFragment4 = new ExitFragment4();

        mHead = (ImageView) findViewById(R.id.img_head);
        mHead.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_W);
        mHead.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_HEAD_IMG_H);
        CommonUtil.getInstance().loadImage(this, R.drawable.exit_head, mHead);

        ////    스크롤 높이를 계산해서 적용
        mFrame = (FrameLayout) findViewById(R.id.screen_frame);
        mFrame.requestTransparentRegion(mFrame);
        mFullScreenY = CommonUtil.getInstance().getPhoneHeightPx();                // 유효 풀 스크린 높이 구하기
//        mScrollY = mFullScreenY - mBtnHeight;           // 스크롤 영역의 크기 구하기
        mScrollY = mFullScreenY;                          // 스크롤 영역의 크기 구하기
        mFrame.getLayoutParams().height = mScrollY;       // 스크롤 크기를 스크롤에 지정

        ////    스크롤 내에 들어갈 컨텐츠 이미지 세팅
        mScreenY = CommonUtil.getInstance().getScreenImageHeightPx();              // 메인 스크린에 들어가는 큰 이미지의 높이 구하기
        mImg = (LinearLayout) findViewById(R.id.img);     // 구하고 나서 이미지 참조하고
        mImg.getLayoutParams().height = mScreenY;         // 거기에 높이를 적용


        mBtn1 = (Button) findViewById(R.id.button1);
        mBtn2 = (Button) findViewById(R.id.button2);
        mBtn3 = (Button) findViewById(R.id.button3);
        mBtn4 = (Button) findViewById(R.id.button4);
        mBtnArray = new Button[]{mBtn1, mBtn2, mBtn3, mBtn4};


        mBtnHeight = CommonUtil.getInstance().getHomeBtnHeightPx();
        mBtn1.getLayoutParams().height = mBtnHeight;
        mBtn2.getLayoutParams().height = mBtnHeight;
        mBtn3.getLayoutParams().height = mBtnHeight;
        mBtn4.getLayoutParams().height = mBtnHeight;

        mMenu_Resource = new int[]{
                R.drawable.exit_tab1,
                R.drawable.exit_tab2,
                R.drawable.exit_tab3_selector,
                R.drawable.exit_tab4_selector};

        mMenu_Resource_Press = new int[]{
                R.drawable.exit_tab1_tab,
                R.drawable.exit_tab2_dim,
                R.drawable.exit_tab3_dim,
                R.drawable.exit_tab4_dim};

        mBtn_Language = (ImageView) findViewById(R.id.btn_language);
        mBtn_Language.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(CommonUtil.getInstance().GLOBAL_IMG_WIDTH_PX);
        mBtn_Language.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(CommonUtil.getInstance().GLOBAL_IMG_HEIGHT_PX);

        Glide.with(this).load(R.drawable.common_global).into(mBtn_Language);
        mBtn_Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.getInstance().callToast(ExitActivity.this);
            }
        });

        for (int i = 0; i < mBtnArray.length; i++) {
            mBtnArray[i].setOnClickListener(new MeneOnClickListener());
        }

        btnReset(); // 메뉴버튼 초기화
    }




    private void btnReset() {
        for (int i = 0; i < mMenu_Resource.length; i++) {
            mBtnArray[i].setBackgroundResource(mMenu_Resource[i]);
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
            // AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            // am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5000, runner);

            Logger logger = Logger.getLogger(ExitActivity.class.getSimpleName());
            logger.info(e.getMessage());

            mDefaultUncaughtExceptionHandler.uncaughtException(t, e);
        }
    }

}
