package com.skt.tmaphot.activity.main.coupon.more;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;

public class CouponListRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImgReview = null;

    public CouponListRecyclerViewHolder(View itemView) {
        super(itemView);

        if(itemView != null)
        {
            mImgReview = (ImageView) itemView.findViewById(R.id.coupon_recyler_item_image);

//            Drawable drawable= null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                drawable = (Drawable)itemView.getContext().getDrawable(R.drawable.border_test);
//                mImgReview.setBackground(drawable);
//                mImgReview.setClipToOutline(true);
//            }

            TextView text = (TextView) itemView.findViewById(R.id.test_test_test);
            text.setText(Html.fromHtml("<del>test</del><big>sdfsdf</big>"));

        }
    }

    public ImageView getImageView() {
        return mImgReview;
    }
}