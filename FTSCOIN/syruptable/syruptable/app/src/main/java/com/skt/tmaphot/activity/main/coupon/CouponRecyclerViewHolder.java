package com.skt.tmaphot.activity.main.coupon;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;

public class CouponRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mImgUrl;
    private TextView mTitle;
    private TextView mMenu;
    private TextView mType;
    private TextView mDistance;
    private TextView mSale;
    private TextView mPrePrice;
    private TextView mPrice;

    private EventListener eventListener;

    public interface EventListener{
        void onReceivedEvent(int postion);
    }

    public void setOnEventListener(EventListener listener){
        eventListener = listener;
    }

    public CouponRecyclerViewHolder(View itemView, int layoutType) {
        super(itemView);

        if(layoutType == CouponRecyclerViewDataAdapter.COUPON_ITEM_MAIN_LAYOUT)
        {
            mImgUrl = (ImageView) itemView.findViewById(R.id.main_coupon_recyler_item_image);
            mTitle = (TextView) itemView.findViewById(R.id.main_coupon_recyler_item_title);
            mSale = (TextView) itemView.findViewById(R.id.main_coupon_recyler_item_sale);
            mMenu = (TextView) itemView.findViewById(R.id.main_coupon_recyler_item_menu);
            mDistance = (TextView) itemView.findViewById(R.id.main_coupon_recyler_item_distance);

            int greenColor = itemView.getContext().getResources().getColor(R.color.black_40filter);
            String color = "#" + Integer.toHexString(greenColor);
            mImgUrl.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Drawable drawable = (Drawable) itemView.getContext().getDrawable(R.drawable.round_main_item);
                itemView.setBackground(drawable);
                itemView.setClipToOutline(true);
            }
        }else if(layoutType == CouponRecyclerViewDataAdapter.COUPON_ITEM_MORE_LAYOUT){
            mImgUrl = (ImageView) itemView.findViewById(R.id.coupon_recyler_item_image);
            mTitle =(TextView)itemView.findViewById(R.id.coupon_recyler_item_name);
            mMenu =(TextView)itemView.findViewById(R.id.coupon_recyler_item_menu);
            mType =(TextView)itemView.findViewById(R.id.coupon_recyler_item_type);
            mSale =(TextView)itemView.findViewById(R.id.coupon_recyler_item_sale);
            mPrePrice =(TextView)itemView.findViewById(R.id.coupon_recyler_item_preprice);
            mPrice =(TextView)itemView.findViewById(R.id.coupon_recyler_item_price);
        }

        itemView.setOnClickListener(this);
    }

    public ImageView getmImgUrl() {
        return mImgUrl;
    }

    public TextView getmTitle() {
        return mTitle;
    }

    public TextView getmMenu() {
        return mMenu;
    }

    public TextView getmType() {
        return mType;
    }

    public TextView getmDistance() {
        return mDistance;
    }

    public TextView getmSale() {
        return mSale;
    }

    public TextView getmPrePrice() {
        return mPrePrice;
    }

    public TextView getmPrice() {
        return mPrice;
    }

    @Override
    public void onClick(View view) {

        eventListener.onReceivedEvent(getAdapterPosition());

//        int pos = getAdapterPosition();
//
//        Intent intent = new Intent(view.getContext(), StoreInfoActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("key", getAdapterPosition());
//        view.getContext().startActivity(intent);
    }
}