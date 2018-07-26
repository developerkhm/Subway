package com.skt.tmaphot.activity.main.hotdeal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;

public class HotdealRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImg;
    private TextView mTitleText;
    private TextView mMenueText;



    public HotdealRecyclerViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            mImg = (ImageView) itemView.findViewById(R.id.main_hotdeal_recyler_item_image);
            mTitleText = (TextView)itemView.findViewById(R.id.main_hotdeal_recyler_item_txt_title);
            mMenueText = (TextView)itemView.findViewById(R.id.main_hotdeal_recyler_item_txt_menu);


            int greenColor = itemView.getContext().getResources().getColor(R.color.black_55filter);
            String color = "#"+Integer.toHexString(greenColor);
            mImg.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);

            Drawable drawable= null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                drawable = (Drawable)itemView.getContext().getDrawable(R.drawable.round_main_item);
                itemView.setBackground(drawable);
                itemView.setClipToOutline(true);
            }

            // on item click
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(v.getContext(), StoreInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", getAdapterPosition());
                    v.getContext().startActivity(intent);
                }
            });

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