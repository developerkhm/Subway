package com.skt.tmaphot.activity.main.menu;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.skt.tmaphot.R;

public class MainMenuRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mImg;
    private OnMenuCilckListener onMenuCilckListener;

    public interface OnMenuCilckListener {
        void menuOnClick(int position);
    }

    public void setOnMenuCilckListener(OnMenuCilckListener listener) {
        onMenuCilckListener = listener;
    }

    public MainMenuRecyclerViewHolder(View itemView) {
        super(itemView);

        mImg = (ImageView) itemView.findViewById(R.id.main_menu_recyler_item_image);
        mImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("AT", "MainMenuRecyclerViewHolder onClick");
        onMenuCilckListener.menuOnClick(getAdapterPosition());
    }

    public ImageView getImageView() {
        return mImg;
    }
}