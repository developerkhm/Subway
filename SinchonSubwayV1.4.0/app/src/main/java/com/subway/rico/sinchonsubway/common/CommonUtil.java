package com.subway.rico.sinchonsubway.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.subway.rico.sinchonsubway.R;

import org.apache.log4j.Logger;

public class CommonUtil {

    private static CommonUtil uniqueInstance;
    public Activity MainActivity;

    public final int MAX_WIDTH_PX = 2160;     // 시안상 화면 최대 폭
    public final int MAX_HEIGHT_PX = 3840;     // 시안상 화면 최대 높이
    private final int MAX_WIDTH_PX_TOAST = 1784;
    private final int MAX_HEIGHT_PX_TOAST = 933;

    private final int HOME_BTN_WIDTH_PX = 540;
    private final int HOME_BTN_HEIGHT_PX = 560;
    private final int HOME_IMG_WIDTH_PX = 2160;
    private final int HOME_IMG_HEIGHT_PX = 3280;

    public final int GLOBAL_IMG_WIDTH_PX = 953;
    public final int GLOBAL_IMG_HEIGHT_PX = 136;

    private Logger logger;

    private CommonUtil(){
        logger = Logger.getLogger(CommonUtil.class.getSimpleName());
    }

    public static CommonUtil getInstance(){
        if (uniqueInstance == null){
            uniqueInstance = new CommonUtil();
        }
        return uniqueInstance;
    }


    public void loadImage(final Context context, int res, ImageView view){
        Glide.with( context )
                .load(res)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {


                        logger.info("Glide onLoadFailed");
                        if(e != null)
                        {
                            logger.info(e.getLocalizedMessage());
                        }

                        Glide.with(context).load(model).into(target);
                        return true; //return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(view);
    }

    public void callToast(Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        final View toastDesign = inflater.inflate(R.layout.common_toast, null);

        ImageButton img = (ImageButton)toastDesign.findViewById(R.id.imageButton);
        img.getLayoutParams().width = utilGetViewWidthPx(MAX_WIDTH_PX_TOAST);
        img.getLayoutParams().height = utilGetViewWidthPx(MAX_HEIGHT_PX_TOAST);
        CommonUtil.getInstance().loadImage(activity, R.drawable.common_toast, img);

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER, 0, 0); // CENTER를 기준으로 0, 0 위치에 메시지 출력
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastDesign);
        toast.show();
    }


    ////    공용으로 쓸 유틸 - 현 기기상에 표시할 뷰의 높이 구하기 ////
    public int getPhoneWidthPx(){
        DisplayMetrics metrics = new DisplayMetrics();
        MainActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;

        Log.i("hoyangi","screen width:"+screenWidth);
        return screenWidth;
    }

    public int getPhoneHeightPx(){
        DisplayMetrics metrics = new DisplayMetrics();
        MainActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;

        Log.i("hoyangi","screen width:"+screenHeight);
        return screenHeight;
    }

    // 스크린 스크롤에 들어갈 이미지의 높이를 구함.
    public int getScreenImageHeightPx() {
        // 1. 현 기기 가로 px 구하고
        int screenWidth = getPhoneWidthPx();
        // 2. 대상 이미지는 가로는 동일하니까 두고
        // 3. 2160 px 과의 비율 구하고 (2160:3280 = 1080 : x)
        // 4. 이 비율로 3280px 과 대응대는 현 기기의 세로 px 구해서
        int homeImgHeightPx = HOME_IMG_HEIGHT_PX * screenWidth / HOME_IMG_WIDTH_PX;
        // 5. 리턴 끝.
        Log.i("hoyangi", "계산한 현 스크린의 홈 스크린 이미지 height px :" + homeImgHeightPx);
        return homeImgHeightPx;
    }

    ////    공용으로 쓸 유틸 - 현 기기상에 표시할 뷰의 폭 구하기 ////
    public int utilGetViewWidthPx(int originWidth){
        // 1. 시안 총 폭을 구하고   =   PDF_WIDTH_PX
        // 2. 현 기기 가로 px 구하고
        int screenWidth = getPhoneWidthPx();
        // 3. 대상 이미지의 폭 인자로 받고
        // 4. 이 기기에서의 x 값을 구함. (2160:1080 = 2080:x)
        int x = screenWidth * originWidth / MAX_WIDTH_PX;
        return x;
    }

    public int utilGetViewHeightPx(int originHeight){
        // 1. 시안 총 폭을 구하고   =   PDF_WIDTH_PX
        // 2. 현 기기 가로 px 구하고
        int screenHeight = getPhoneHeightPx();
        // 3. 대상 이미지의 폭 인자로 받고
        // 4. 이 기기에서의 x 값을 구함. (2160:1080 = 2080:x)
        int y = screenHeight * originHeight / MAX_HEIGHT_PX;
        return y;
    }

    public int getHomeBtnHeightPx() {
        // 1. 현 기기 가로 px 구하고
        int screenWidth = getPhoneWidthPx();
        // 2. /4 해서 현 기기의 버튼 한칸의 가로 px 구하고
        int homeBtnWidthPx = screenWidth / 4;
        // 3. 540 px 과의 비율 구하고 (540:560 = homeBtnWidthPx : x)
        // 4. 이 비율로 560px 과 대응대는 현 기기의 세로 px 구해서
        int homeBtnHeightPx = HOME_BTN_HEIGHT_PX * homeBtnWidthPx / HOME_BTN_WIDTH_PX;
        // 5. 리턴 끝.
        Log.i("hoyangi", "계산한 현 스크린의 홈 버튼 height px :" + homeBtnHeightPx);
        return homeBtnHeightPx;
    }


    public void prePareView(Activity activity) {
        // 상단 상태 표시 줄 gone
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 하단 소프트키 gone
        int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ");
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.");
        }
        // 몰입 모드를 꼭 적용해야 한다면 아래의 3가지 속성을 모두 적용시켜야 합니다
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}

//    public void markerOn(ImageView view, int offSetTop, int offSetLeft) {
//
//        int markerSizeH = ActivityHome.PDF_HEIGHT_PX / 35;
//        int markerSizeW = ActivityHome.PDF_WIDTH_PX / 30;
//
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        params.topMargin = (ActivityHome.PDF_HEIGHT_PX / 2) + offSetTop;
//        params.leftMargin = (ActivityHome.PDF_WIDTH_PX / 2) + offSetLeft;
//        view.setLayoutParams(params);
//
//        ViewGroup.LayoutParams Imgparams = view.getLayoutParams();
//        Imgparams.height = markerSizeH;
//        Imgparams.width = markerSizeW;
//
//        view.setImageResource(R.drawable.exit_marker_linked_traffic);
//
//        Animation mAnimation = new AlphaAnimation(1, 0);
//        mAnimation.setDuration(1000);
//        mAnimation.setInterpolator(new LinearInterpolator());
//        mAnimation.setRepeatCount(Animation.INFINITE);
//        mAnimation.setRepeatMode(Animation.REVERSE);
//
//        view.startAnimation(mAnimation);  //clearAnimation();
//        view.setVisibility(View.VISIBLE);
//    }

//    private void setHideActionNavigation() {
//        decorView = getWindow().getDecorView();
//        uiOption = getWindow().getDecorView().getSystemUiVisibility();
//
//        uiOption |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//
//        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility) {
//                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                    decorView.setSystemUiVisibility(uiOption);
//                }
//            }
//        });
//    }