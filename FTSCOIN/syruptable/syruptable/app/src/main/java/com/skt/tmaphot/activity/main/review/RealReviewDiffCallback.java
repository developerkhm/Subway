package com.skt.tmaphot.activity.main.review;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

public class RealReviewDiffCallback extends DiffUtil.Callback{

    private final List<RealReviewRecyclerViewItem> oldList;
    private final List<RealReviewRecyclerViewItem> newList;

    public RealReviewDiffCallback(List<RealReviewRecyclerViewItem> oldList, List<RealReviewRecyclerViewItem> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final RealReviewRecyclerViewItem oldItem = oldList.get(oldItemPosition);
        final RealReviewRecyclerViewItem newItem = newList.get(newItemPosition);

        return oldItem.getId().equals(newItem.getId());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
