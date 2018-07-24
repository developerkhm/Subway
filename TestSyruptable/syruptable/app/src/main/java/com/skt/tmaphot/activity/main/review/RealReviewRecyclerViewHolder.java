package com.skt.tmaphot.activity.main.review;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
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


            mImgReview = (ImageView) itemView.findViewById(R.id.realreview_recyler_item_image);

//            int greenColor = itemView.getContext().getResources().getColor(R.color.white_40filter);
//            String color = "#"+Integer.toHexString(greenColor);
//            mImgReview.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);

//            GradientDrawable drawable= (GradientDrawable) itemView.getContext().getDrawable(R.drawable.round_main_grid_hotplace_item);
//            mImgReview.setBackground(drawable);
//            mImgReview.setClipToOutline(true);

            GradientDrawable drawable=
                    (GradientDrawable)itemView.getContext().getDrawable(R.drawable.round_main_item);
            mImgReview.setBackground(drawable);
            mImgReview.setClipToOutline(true);


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