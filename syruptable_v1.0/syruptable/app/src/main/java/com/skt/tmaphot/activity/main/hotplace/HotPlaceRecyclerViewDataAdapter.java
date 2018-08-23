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
import com.skt.tmaphot.net.model.hotplace.HotplaceModel;

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
                intent.putExtra("storeId", viewItemList.get(postion).getId());
                intent.putExtra("subpath", "hotplace");
                BaseApplication.getInstance().ActivityStart(intent, null);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(HotPlaceRecyclerViewHolder holder, final int position) {
        BaseApplication.getInstance().loadImage(mContext, viewItemList.get(position).getRecentImage(), holder.mImgUrl, false, BaseApplication.getInstance().LIST_HORIZONTAL_HOTPLACE);
        holder.ratingBar.setRating(Float.valueOf(viewItemList.get(position).getStarRating()));
        holder.mTitle.setText(viewItemList.get(position).getNAME());
        holder.mStoreType.setText(viewItemList.get(position).getCategoryLName() + "·" + viewItemList.get(position).getCategoryName());
        holder.mReviewCount.setText(viewItemList.get(position).getBlogReviewCount());
        holder.mDistance.setText(viewItemList.get(position).getDk()+"km"  +" "+ viewItemList.get(position).getDm());
    }

    public void loadData(List<HotplaceModel> viewItemList) {
        this.viewItemList.addAll(viewItemList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.viewItemList.clear();
    }

    public String getDatatotalCount(){
        String count = "0";
        if(viewItemList != null)
            if(viewItemList.size() > 0){
                count = viewItemList.get(0).getTotalCount();
            }
            return count;
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