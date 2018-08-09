package com.skt.tmaphot.activity.main.coupon;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.skt.tmaphot.net.service.Item;

import java.util.List;

public class CouponDiffCallback2 extends DiffUtil.Callback{

    private final List<Item> oldList;
    private final List<Item> newList;

    public CouponDiffCallback2(List<Item> oldList, List<Item> newList) {
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

        boolean result = oldList.get(oldItemPosition).getAccountId() == newList.get(newItemPosition).getAccountId();
                Log.d("WHO1", "[1]areItemsTheSame :" + String.valueOf(result));

        return result;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Item oldItem = oldList.get(oldItemPosition);
        final Item newItem = newList.get(newItemPosition);

        boolean result = oldItem.getAccountId().equals(newItem.getAccountId());

        Log.d("WHO1", "[2]areItemsTheSame :" + String.valueOf(result));

        return result;

//        return oldItem.getAccountId() == newItem.getAccountId();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        Log.d("WHO3", "getChangePayload :" + "oldItemPosition :" + oldItemPosition + " newItemPosition : " + newItemPosition);
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
