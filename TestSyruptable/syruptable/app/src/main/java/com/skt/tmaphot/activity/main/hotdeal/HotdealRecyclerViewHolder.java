package com.skt.tmaphot.activity.main.hotdeal;

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

public class HotdealRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImg;
    private TextView mTitleText;
    private TextView mMenueText;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HotdealRecyclerViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            mImg = (ImageView) itemView.findViewById(R.id.hotdeal_recyler_item_image);
            mTitleText = (TextView)itemView.findViewById(R.id.hotdeal_recyler_item_txt_title);
            mMenueText = (TextView)itemView.findViewById(R.id.hotdeal_recyler_item_txt_menu);


            int greenColor = itemView.getContext().getResources().getColor(R.color.black_55filter);
            String color = "#"+Integer.toHexString(greenColor);
            mImg.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);


            GradientDrawable drawable= (GradientDrawable)itemView.getContext().getDrawable(R.drawable.round_main_item);
            itemView.setBackground(drawable);
            itemView.setClipToOutline(true);

        }
    }

    public ImageView getmImgReview() {
        return mImg;
    }
    public TextView getmTitleText() {
        return mTitleText;
    }
    public TextView getmMenueText() {
        return mMenueText;
    }
}