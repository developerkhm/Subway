package com.skt.tmaphot;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.tsengvn.typekit.Typekit;

import io.fabric.sdk.android.Fabric;

public class MainApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunGothic.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumBarunGothicBold.ttf"));
    }


    public static void ActivityStart(Intent intent, Bundle bundle){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent, bundle);
    }

    public static void loadResImage(final Context context, int res, ImageView view) {
        Glide.with(context)
                .load(res)
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
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
                .into(view);
    }

    public static void loadResRoundImage(final Context context, int res, ImageView view) {
        Glide.with(context)
                .load(res)
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
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
                .apply(new RequestOptions().transform(new RoundedCorners(100)).diskCacheStrategy(DiskCacheStrategy.ALL))
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(view);
    }

    public static void loadUriImage(final Context context, Uri uri, ImageView view) {
        Glide.with(context)
                .load(uri)
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
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop())
                .into(view);
    }

    public static void loadUrlImage(final Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
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
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop())
                .into(view);
    }

    public static void loadUrlRoundImage(final Context context, String url, ImageView view) {

//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));


        Glide.with(context)
                .load(url)

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {


//                        Glide.with(context).load(model).into(target);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop())
                .apply(new RequestOptions().transform(new RoundedCorners(100)).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(view);
    }
}
