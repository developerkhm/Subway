package com.skt.tmaphot.activity.main.hotplace;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.coupon.CouponRecyclerViewDataAdapter;
import com.skt.tmaphot.common.CommonUtil;

public class HotPlaceRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Context mContext;
    public ImageView mImgUrl;
    public TextView mTitle;
    public TextView mReview;
    public TextView mReviewCount;
    public TextView mDistance;
    public RatingBar ratingBar;

    private EventListener eventListener;

    public interface EventListener {
        void onReceivedEvent(int postion);
    }

    public void setOnEventListener(EventListener listener) {
        eventListener = listener;
    }

    public HotPlaceRecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;

        mImgUrl = (ImageView) itemView.findViewById(R.id.hotplace_grid_item_image);
        mTitle = (TextView) itemView.findViewById(R.id.hotplace_grid_txt_title);
        mReview = (TextView) itemView.findViewById(R.id.hotplace_review);
        mDistance = (TextView) itemView.findViewById(R.id.hotplace_grid_txt_distance);
        mReviewCount = (TextView) itemView.findViewById(R.id.hotplace_review_count_txt);
        ratingBar = (RatingBar) itemView.findViewById(R.id.hotplace_ratingBar);
        CommonUtil.getInstance().setColorRatingBar(ratingBar);
        ratingBar.setStepSize(0.1f);

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            Drawable drawable = (Drawable) itemView.getContext().getDrawable(R.drawable.round_main_item);
//            mImgUrl.setBackground(drawable);
//            mImgUrl.setClipToOutline(true);
//        }
    }

    @Override
    public void onClick(View view) {
        eventListener.onReceivedEvent(getAdapterPosition());
    }
}