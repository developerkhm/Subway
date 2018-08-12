package com.skt.tmaphot.activity.main.hotplace;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;
import com.skt.tmaphot.net.model.HotplaceModel;

import java.util.ArrayList;
import java.util.List;

public class HotPlaceRecyclerViewDataAdapter extends RecyclerView.Adapter<HotPlaceRecyclerViewHolder> {

    private Context mContext;
    private List<HotplaceModel> viewItemList = new ArrayList<>();

    public HotPlaceRecyclerViewDataAdapter(List<HotplaceModel> viewItemList) {
        this.viewItemList.addAll(viewItemList);
    }

    @Override
    public HotPlaceRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_hotplace_grid_item, parent, false);
        mContext = parent.getContext();
        HotPlaceRecyclerViewHolder holder = new HotPlaceRecyclerViewHolder(mContext, view);
        holder.setOnEventListener(new HotPlaceRecyclerViewHolder.EventListener() {
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
    public void onBindViewHolder(HotPlaceRecyclerViewHolder holder, int position) {


        BaseApplication.getInstance().loadImage(mContext, viewItemList.get(position).getRecentImage(), holder.mImgUrl, false);
        holder.mTitle.setText(viewItemList.get(position).getNAME());
        holder.mReview.setText(viewItemList.get(position).getReviewStr());
        holder.mReviewCount.setText(viewItemList.get(position).getBlogReviewCount());
        holder.mDistance.setText(viewItemList.get(position).getDm() + "m");
        holder.ratingBar.setRating(Float.valueOf(viewItemList.get(position).getStarRating()));
    }

    public void loadData(List<HotplaceModel> viewItemList) {
        this.viewItemList.addAll(viewItemList);
        notifyDataSetChanged();
    }

    public void clearData(){
        this.viewItemList.clear();
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(viewItemList.get(position).getId());
    }

//    @Override
//    public int getItemViewType(int position) {
//        return this.itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
//    }
}