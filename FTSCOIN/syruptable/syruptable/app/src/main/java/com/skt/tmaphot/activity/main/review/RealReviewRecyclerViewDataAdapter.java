package com.skt.tmaphot.activity.main.review;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;

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
        return holder;
    }

    @Override
    public void onBindViewHolder(RealReviewRecyclerViewHolder holder, int position) {

        RealReviewRecyclerViewItem viewItem = viewItemList.get(position);
        BaseApplication.getInstance().loadImage(mContext, viewItem.getImgUrl(), holder.getImageView(), false);
    }

    public void reLoadData(List<RealReviewRecyclerViewItem> viewItemList) {
        final RealReviewDiffCallback diffCallback = new RealReviewDiffCallback(this.viewItemList, viewItemList);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.viewItemList.clear();
        this.viewItemList.addAll(viewItemList);

        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    @Override
    public long getItemId(int position) {
//        return viewItemList.get(position).getId();
        return Long.valueOf(viewItemList.get(position).getId());
    }
}