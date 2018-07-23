package com.skt.tmaphot.activity.main.coupon.more;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.skt.tmaphot.R;

public class CouponListRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImgReview = null;

    public CouponListRecyclerViewHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            mImgReview = (ImageView) itemView.findViewById(R.id.main_img_review_item);
        }
    }

    public ImageView getImageView() {
        return mImgReview;
    }
}