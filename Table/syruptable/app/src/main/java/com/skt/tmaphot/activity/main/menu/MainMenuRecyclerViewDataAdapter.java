package com.skt.tmaphot.activity.main.menu;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;

import java.util.ArrayList;
import java.util.List;

public class MainMenuRecyclerViewDataAdapter extends RecyclerView.Adapter<MainMenuRecyclerViewHolder>{

    int selectedPosition= 0;

    private Context context;
    private List<MainMenuRecyclerViewItem> viewItemList;
    private OnEventCilckListener onEventCilckListener;
    private View slectView;




    public interface OnEventCilckListener {
        void menuOnClick(int position);
    }

    public void setOnEventCilckListener(OnEventCilckListener listener) {
        onEventCilckListener = listener;
    }

//    private MainMenuRecyclerViewHolder.OnMenuCilckListener onMenuCilckListener = new MainMenuRecyclerViewHolder.OnMenuCilckListener() {
//        @Override
//        public void menuOnClick(View view, int position) {
//
//            Log.d("ABCD1", "Click" + position);
//
//            onEventCilckListener.menuOnClick(position);
//
//            if(slectView != null && slectView != view) {
//                slectView.setSelected(false);
//            }
//            view.setSelected(true);
//            slectView = view;
//
//        }
//    };

    public MainMenuRecyclerViewDataAdapter(List<MainMenuRecyclerViewItem> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public MainMenuRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.main_menu_recycler_item, parent, false);

        MainMenuRecyclerViewHolder holder = new MainMenuRecyclerViewHolder(itemView);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });



        return holder;
    }

    @Override
    public void onBindViewHolder(MainMenuRecyclerViewHolder holder, final int position) {
        final MainMenuRecyclerViewItem viewItem = viewItemList.get(position);
        BaseApplication.getInstance().loadImage(context, viewItem.getRes(), holder.getImageView(), false);
//        holder.itemView.setOnClickListener(this);

        if(selectedPosition==position)
            holder.itemView.setSelected(true);
        else
            holder.itemView.setSelected(false);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();

            }
        });

    }

//    @Override
//    public void onClick(View view) {
//
////        onEventCilckListener.menuOnClick(Integer.valueOf(viewItem.getMenuType()));
//
////        if (slectView != null && slectView != view) {
////            slectView.setSelected(false);
////        }
////        view.setSelected(true);
////        slectView = view;
//    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(viewItemList.get(position).getMenuType());
    }
}