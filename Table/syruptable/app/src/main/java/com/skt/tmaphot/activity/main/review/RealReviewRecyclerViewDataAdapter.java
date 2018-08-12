package com.skt.tmaphot.activity.main.review;

import android.content.Context;
import android.content.Intent;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.hotdeal.HotdealRecyclerViewHolder;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class RealReviewRecyclerViewDataAdapter extends RecyclerView.Adapter<RealReviewRecyclerViewHolder> {

    private Context mContext;
    private List<RealReviewRecyclerViewItem> viewItemList = new ArrayList<>();

    public RealReviewRecyclerViewDataAdapter(List<RealReviewRecyclerViewItem> viewItemList) {
        this.viewItemList.addAll(viewItemList);
    }

    @Override
    public RealReviewRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_realreview_recycler_item, parent, false);
        RealReviewRecyclerViewHolder holder = new RealReviewRecyclerViewHolder(view);
        holder.setOnEventListener(new RealReviewRecyclerViewHolder.EventListener() {
            @Override
            public void onReceivedEvent(int postion) {
                Intent intent = new Intent(mContext, StoreInfoActivity.class);
                intent.putExtra("id", viewItemList.get(postion).getId());
                BaseApplication.getInstance().ActivityStart(intent, null);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RealReviewRecyclerViewHolder holder, int position) {

        RealReviewRecyclerViewItem viewItem = viewItemList.get(position);
        BaseApplication.getInstance().loadImage(mContext, viewItem.getImgUrl(), holder.getImageView(), false);
    }

    public void reLoadData(List<RealReviewRecyclerViewItem> viewItemList) {
        this.viewItemList.addAll(viewItemList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(viewItemList.get(position).getId());
    }
}