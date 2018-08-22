package com.skt.tmaphot.activity.main.store.review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;

public class ReviewRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView loginImageView;
    public TextView nicknameTextview;
    public TextView contentTextview;
    public ImageView sympathyImageView;
    public TextView sympathyTextView;

    public ReviewRecyclerViewHolder(View itemView) {
        super(itemView);
        loginImageView = (ImageView) itemView.findViewById(R.id.review_my_image);
    }
}
