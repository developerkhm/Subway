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
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.skt.tmaphot.location.GPSData;
import com.skt.tmaphot.location.GPSTracker;
import com.tsengvn.typekit.Typekit;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class BaseApplication extends Application {

    private AppCompatDialog progressDialog;
    private static BaseApplication baseApplication;

    public final int MAIN_BANNER = 0;
    public final int MAIN_FOOD = 1;
    public final int MAIN_FOOD_HOTDEAL = 2;
    public final int MAIN_FOOD_HOTPLACE = 3;
    public final int FOOD_LIST = 4;
    public final int DEFAULT = 5;
    public final int BASE = 6;

    public static BaseApplication getInstance() {
        return baseApplication;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

//        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.ttf"));
    }


    public void ActivityStart(Intent intent, Bundle bundle) {
        Log.d("getgps", "ActivityStart]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        baseApplication.startActivity(intent, bundle);
    }

    public void loadImage(Context context, Object res, ImageView view, boolean isRound) {

//        int width = 0;
//        int height = 0;
//
//        switch (imageType) {
//            case MAIN_BANNER:
//                width = 300;
//                height = 350;
//                break;
//            case MAIN_FOOD:
//                width = 100;
//                height = 100;
//                break;
//            case MAIN_FOOD_HOTDEAL:
//                width = 350;
//                height = 160;
//                break;
//            case MAIN_FOOD_HOTPLACE:
//                width = 159;
//                height = 207;
//                break;
//            case FOOD_LIST:
//                width = 480;
//                height = 150;
//                break;
//            case DEFAULT:
//                width = 500;
//                height = 400;
//                break;
//            case BASE:
//                break;
//        }


        RequestOptions requestOptions = null;
        if (isRound) {
//            requestOptions = new RequestOptions().transform(new RoundedCorners(100)).diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop();
            requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).circleCrop().error(R.drawable.img_error);
        } else {
            requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).error(R.drawable.img_error).fitCenter().override(Target.SIZE_ORIGINAL / 2, Target.SIZE_ORIGINAL / 2);
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
//
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_progressbar_loading);
//        img_loading_frame.setAnimation(animation);

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
//        builder.setTitle("다이얼로그 제목임")
        builder.setMessage("Network 연결을 확인해 주세요.")
//                .setPositiveButton("긍정", null)
//                .setNegativeButton("부정", null)
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
