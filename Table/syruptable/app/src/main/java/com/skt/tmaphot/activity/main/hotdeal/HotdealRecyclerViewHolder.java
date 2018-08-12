package com.skt.tmaphot.activity.main.hotdeal;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skt.tmaphot.R;

public class HotdealRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public static final int HOTDEAL_ITEM_MAIN_LAYOUT = 0;
    public static final int HOTDEAL_ITEM_MORE_LAYOUT = 1;

    private ImageView mImgUrl;
    private RatingBar mGrade;
    private TextView mTitle;
    private TextView mMenue;
    private TextView mDistance;
    private TextView mSale;

    private EventListener eventListener;

    public interface EventListener{
        void onReceivedEvent(int postion);
    }

    public void setOnEventListener(EventListener listener){
        eventListener = listener;
    }

    public HotdealRecyclerViewHolder(View itemView, int layoutType) {
        super(itemView);

        if (layoutType == HOTDEAL_ITEM_MAIN_LAYOUT) {
            mImgUrl = (ImageView) itemView.findViewById(R.id.main_hotdeal_recyler_item_image);
            mTitle = (TextView)itemView.findViewById(R.id.main_hotdeal_recyler_item_title);
            mMenue = (TextView)itemView.findViewById(R.id.main_hotdeal_recyler_item_menu);

            int greenColor = itemView.getContext().getResources().getColor(R.color.black_55filter);
            String color = "#"+Integer.toHexString(greenColor);
            mImgUrl.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_OVER);

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                Drawable drawable = (Drawable)itemView.getContext().getDrawable(R.drawable.round_main_item);
//                itemView.setBackground(drawable);
//                itemView.setClipToOutline(true);
//            }

        }else if(layoutType == HOTDEAL_ITEM_MORE_LAYOUT){
            mImgUrl = (ImageView) itemView.findViewById(R.id.hotdeal_recyler_item_image);
        }

        itemView.setOnClickListener(this);
    }

    public ImageView getmImgUrl() {
        return mImgUrl;
    }

    public RatingBar getmGrade() {
        return mGrade;
    }

    public TextView getmTitle() {
        return mTitle;
    }

    public TextView getmMenue() {
        return mMenue;
    }

    public TextView getmDistance() {
        return mDistance;
    }

    public TextView getmSale() {
        return mSale;
    }

    @Override
    public void onClick(View view) {
        eventListener.onReceivedEvent(getAdapterPosition());
    }
}