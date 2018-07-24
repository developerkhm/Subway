package com.skt.tmaphot.activity.main.coupon;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;

public class CouponRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImg;
    private TextView mTextSale;
    private TextView mTitleText;
    private TextView mMenueText;
    private TextView mDistanceText;


    public CouponRecyclerViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {

            mImg = (ImageView) itemView.findViewById(R.id.hotdeal_recyler_item_image);
            mTextSale = (TextView)itemView.findViewById(R.id.coupon_recyler_item_txt_sale);
            mTitleText = (TextView)itemView.findViewById(R.id.hotdeal_recyler_item_txt_title);
            mMenueText = (TextView)itemView.findViewById(R.id.hotdeal_recyler_item_txt_menu);
            mDistanceText = (TextView)itemView.findViewById(R.id.coupon_recyler_item_txt_distance);

            int greenColor = itemView.getContext().getResources().getColor(R.color.black_40filter);
            String color = "#"+Integer.toHexString(greenColor);
            mImg.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);

//            GradientDrawable drawable= (GradientDrawable)itemView.getContext().getDrawable(R.drawable.round_main_item);
//            itemView.setBackground(drawable);
//            itemView.setClipToOutline(true);
        }
    }

    public ImageView getmImgReview() {
        return mImg;
    }

    public TextView getmTextSale() {
        return mTextSale;
    }

    public TextView getmTitleText() {
        return mTitleText;
    }

    public TextView getmMenueText() {
        return mMenueText;
    }

    public TextView getmDistanceText() {
        return mDistanceText;
    }
}