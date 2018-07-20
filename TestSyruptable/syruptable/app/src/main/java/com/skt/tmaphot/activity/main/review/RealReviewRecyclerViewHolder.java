package com.skt.tmaphot.activity.main.review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.review.multiple.models.Image;

public class RealReviewRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImgReview = null;

    public RealReviewRecyclerViewHolder(View itemView) {
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