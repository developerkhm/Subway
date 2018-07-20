package com.skt.tmaphot.activity.main.hotdeal;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;

public class HotdealRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImg;
    private TextView mTextSale;
    private TextView mTitleText;
    private TextView mMenueText;
    private TextView mDistanceText;

    public HotdealRecyclerViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            mImg = (ImageView) itemView.findViewById(R.id.main_recycle_coupon_img_item);
            mTextSale = (TextView)itemView.findViewById(R.id.main_recycle_coupon_txt_sale);
            mTitleText = (TextView)itemView.findViewById(R.id.main_recycle_coupon_txt_title);
            mMenueText = (TextView)itemView.findViewById(R.id.main_recycle_coupon_txt_menu);
            mDistanceText = (TextView)itemView.findViewById(R.id.main_recycle_coupon_txt_distance);
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