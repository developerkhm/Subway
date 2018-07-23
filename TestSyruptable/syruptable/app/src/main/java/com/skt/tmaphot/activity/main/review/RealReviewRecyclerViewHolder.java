package com.skt.tmaphot.activity.main.review;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;

public class RealReviewRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mImgReview = null;

    public RealReviewRecyclerViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            mImgReview = (ImageView) itemView.findViewById(R.id.main_img_review_item);
            itemView.setOnClickListener(this);
        }
    }

    public ImageView getImageView() {
        return mImgReview;
    }

    @Override
    public void onClick(View v) {
        v.getContext().startActivity(new Intent(v.getContext(), StoreInfoActivity.class));
    }
}