package com.skt.tmaphot.activity.main.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;

import java.util.ArrayList;
import java.util.List;

public class MainMenuRecyclerViewDataAdapter extends RecyclerView.Adapter<MainMenuRecyclerViewHolder> {

    private Context context;
    private List<MainMenuRecyclerViewItem> viewItemList;
    private OnEventCilckListener onEventCilckListener;

    public interface OnEventCilckListener{
        void menuOnClick(int position);
    }

    public void setOnEventCilckListener(OnEventCilckListener listener){
        onEventCilckListener = listener;
    }

    private MainMenuRecyclerViewHolder.OnMenuCilckListener onMenuCilckListener = new MainMenuRecyclerViewHolder.OnMenuCilckListener() {
        @Override
        public void menuOnClick(int position) {
            onEventCilckListener.menuOnClick(position);
        }
    };

    public MainMenuRecyclerViewDataAdapter(List<MainMenuRecyclerViewItem> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public MainMenuRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.main_menu_recycler_item, parent, false);

        MainMenuRecyclerViewHolder holder = new MainMenuRecyclerViewHolder(itemView);
        holder.setOnMenuCilckListener(onMenuCilckListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainMenuRecyclerViewHolder holder, int position) {
        MainMenuRecyclerViewItem viewItem = viewItemList.get(position);
        BaseApplication.getInstance().loadImage(context, viewItem.getRes(), holder.getImageView(), false);
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }
}