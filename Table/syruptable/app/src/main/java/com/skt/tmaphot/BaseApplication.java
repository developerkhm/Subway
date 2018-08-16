package com.skt.tmaphot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.stetho.Stetho;
import com.tsengvn.typekit.Typekit;

public class BaseApplication extends Application {

    private AppCompatDialog progressDialog;
    private static BaseApplication baseApplication;

    public final int BASE_DISPLAY = 0;
    public final int LIST_HORIZONTAL = 1;
    public final int LIST_HORIZONTAL_HOTDEAL = 2;
    public final int LIST_HORIZONTAL_HOTPLACE = 3;
    public final int LIST_HORIZONTAL_MORE = 4;
    public final int MAIN_BANNER = 5;
    public final int DEFAULT_ORIGINAL = 6;
    public final int REAL_REVIEW = 7;

    private DisplayMetrics displayMetrics;
    private int displayWidth;
    private int displayHeight_custom;
    private int gridWidth_custom;
    private int gridHeight_custom;

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

        displayMetrics = getResources().getDisplayMetrics();
        displayWidth = displayMetrics.widthPixels;
        displayHeight_custom = displayMetrics.widthPixels + (int) (displayMetrics.widthPixels * 0.2);
        gridWidth_custom = displayWidth / 3;
        gridHeight_custom = displayWidth / 3 + (int) (displayWidth * 0.2);

//        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.ttf"));
    }


    public void ActivityStart(Intent intent, Bundle bundle) {
        Log.d("getgps", "ActivityStart]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        baseApplication.startActivity(intent, bundle);
    }

    public void loadImage(Context context, Object res, ImageView view, boolean isRound, int sizeType) {
        // 임시 모든 사진이 가로길이가 길다고 판단, 세로길이 80% 감안하여 확장, 세로가 긴 사진이 왔을시, 대체 해야함
        int width = 0;
        int height = 0;

        switch (sizeType) {
            case BASE_DISPLAY:
                width = displayWidth;
                height = displayHeight_custom;
                break;
            case LIST_HORIZONTAL:
                width = 200;
                height = 240;
                break;
            case LIST_HORIZONTAL_HOTDEAL:
                width = 350;
                height = 150;
                break;
            case LIST_HORIZONTAL_HOTPLACE:
                width = 250;
                height = 370;
                break;
            case LIST_HORIZONTAL_MORE:
                width = 500;
                height = 250;
                break;
            case MAIN_BANNER:
                width = displayWidth;
                height = displayHeight_custom;
                break;
            case DEFAULT_ORIGINAL:
                width = Target.SIZE_ORIGINAL;
                height = Target.SIZE_ORIGINAL;
                break;
            case REAL_REVIEW:
                width = gridWidth_custom;
                height = gridHeight_custom;
                break;
        }

        RequestOptions requestOptions = null;
        if (isRound) {
            requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop().error(R.drawable.img_error);
        } else {
            requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.img_error).centerCrop().override(width, height);
        }

        Glide.with(context)
                .load(res)
                .transition(new DrawableTransitionOptions().crossFade())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(requestOptions)
                .into(view);
    }

    public void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();
        }

        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
    }

    public void progressSET(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public boolean isNetworkConnected(final Activity ctx) {

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx, AlertDialog.THEME_HOLO_DARK);

        builder.setMessage("Network 연결을 확인해 주세요.")
                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctx.finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }
}
