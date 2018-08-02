package com.skt.tmaphot.activity.main.coupon.more;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;

public class CouponListRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageview = null;
    public TextView nameText;
    public TextView menuText;
    public TextView typeText;
    public TextView saleText;
    public TextView prepriceText;
    public TextView priceText;


    public CouponListRecyclerViewHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            imageview = (ImageView) itemView.findViewById(R.id.coupon_image);
            nameText =(TextView)itemView.findViewById(R.id.coupon_name);
            menuText =(TextView)itemView.findViewById(R.id.coupon_menu);
            typeText =(TextView)itemView.findViewById(R.id.coupon_type);
            saleText =(TextView)itemView.findViewById(R.id.coupon_sale);
            prepriceText =(TextView)itemView.findViewById(R.id.coupon_preprice);
            priceText =(TextView)itemView.findViewById(R.id.coupon_price);

//            Drawable drawable= null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                drawable = (Drawable)itemView.getContext().getDrawable(R.drawable.border_test);
//                mImgReview.setBackground(drawable);
//                mImgReview.setClipToOutline(true);
//            }
        }
    }
}