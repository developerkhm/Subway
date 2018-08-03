package com.skt.tmaphot.activity.main.menu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;

public class MainMenuRecyclerViewHolder extends RecyclerView.ViewHolder  {

    private String id;
    private ImageView mImgReview;
    private TextView mType;
    private OnMenuCilckListener onMenuCilckListener;


    public interface OnMenuCilckListener{
        public void menuOnClick(View v, int id, int position);
    }


    public MainMenuRecyclerViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {
            mImgReview = (ImageView) itemView.findViewById(R.id.main_menu_recyler_item_image);
        }

        // on item click
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // id임시
                int pos = getAdapterPosition();
                onMenuCilckListener.menuOnClick(v,Integer.parseInt(id), pos);
            }
        });
    }
    public ImageView getImageView() {
        return mImgReview;
    }

    public void setOnMenuCilckListener(OnMenuCilckListener listener){
        onMenuCilckListener = listener;
    }

    public void setIdUrl(String id){
        this.id = id;
    }
}