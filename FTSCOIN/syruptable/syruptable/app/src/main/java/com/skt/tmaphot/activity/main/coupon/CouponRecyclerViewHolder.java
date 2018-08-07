package com.skt.tmaphot.activity.main.coupon;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;

public class CouponRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView mImg;
    private TextView mTextSale;
    private TextView mTitleText;
    private TextView mMenueText;
    private TextView mDistanceText;


    public CouponRecyclerViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {

            mImg = (ImageView) itemView.findViewById(R.id.main_coupon_recyler_item_image);
            mTextSale = (TextView)itemView.findViewById(R.id.coupon_sale);
            mTitleText = (TextView)itemView.findViewById(R.id.main_coupon_recyler_item_txt_title);
            mMenueText = (TextView)itemView.findViewById(R.id.main_coupon_recyler_item_txt_menu);
            mDistanceText = (TextView)itemView.findViewById(R.id.main_coupon_recyler_item_txt_distance);

            int greenColor = itemView.getContext().getResources().getColor(R.color.black_40filter);
            String color = "#"+Integer.toHexString(greenColor);
            mImg.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);


            Drawable drawable= null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                drawable = (Drawable)itemView.getContext().getDrawable(R.drawable.round_main_item);
                itemView.setBackground(drawable);
                itemView.setClipToOutline(true);
            }

            // on item click
            itemView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int pos = getAdapterPosition();

        Intent intent = new Intent(view.getContext(), StoreInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("key", getAdapterPosition());
        view.getContext().startActivity(intent);
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