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

public class CouponRecyclerViewHolder extends RecyclerView.ViewHolder {

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
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(v.getContext(), StoreInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("key", getAdapterPosition());
                    v.getContext().startActivity(intent);


                    // 여기서 값을 가져갈수 있다 예를 들어서 id, mTextSale.setText
//                    Toast.makeText(v.getContext(), "You clicked " + mTextSale.getText(),Toast.LENGTH_SHORT).show();

//                    // check if item still exists
//                    if(pos != RecyclerView.NO_POSITION){
//                        RvDataItem clickedDataItem = dataItems.get(pos);
//                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
//                    }
                }
            });
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