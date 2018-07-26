package com.skt.tmaphot.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class CommonUtil {

    private static CommonUtil uniqueInstance;
    public Context mainContex;
    public String devicePhoneNumber;

    private CommonUtil() {
    }

    public static CommonUtil getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CommonUtil();
        }
        return uniqueInstance;
    }

    public void loadImage(final Context context, int res, ImageView view) {
        Glide.with(context)
                .load(res)
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
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(view);
    }

    @SuppressLint("MissingPermission")
    public String getPhoneNumber() {
        return ((TelephonyManager) mainContex.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int top;
        private int right;
        private int bottom;
        private int left;

        public SpacesItemDecoration(int top, int right, int bottom, int left) {
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
        }

        public SpacesItemDecoration(int offset) {
            this.top = offset;
            this.right = offset;
            this.bottom = offset;
            this.left = offset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.top = top;
            outRect.right = right;
            outRect.bottom = bottom;
            outRect.left = left;

            // Add top margin only for the first item to avoid double space between items
//            if(parent.getChildAdapterPosition(view) == 0) {
//                outRect.top = space;
//            }
        }
    }
}