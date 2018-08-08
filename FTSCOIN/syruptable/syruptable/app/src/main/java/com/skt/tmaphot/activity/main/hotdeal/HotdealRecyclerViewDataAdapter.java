package com.skt.tmaphot.activity.main.hotdeal;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.IRecyclerItem;
import com.skt.tmaphot.activity.IRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.coupon.CouponDiffCallback;
import com.skt.tmaphot.activity.main.coupon.CouponRecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

public class HotdealRecyclerViewDataAdapter extends RecyclerView.Adapter<HotdealRecyclerViewHolder>{

    public static final int HOTDEAL_ITEM_MAIN_LAYOUT = 0;
    public static final int HOTDEAL_ITEM_MORE_LAYOUT = 1;

    private Context mContext;
    private int layoutType;
    private List<HotdealRecyclerViewItem> viewItemList = new ArrayList<>();

    public HotdealRecyclerViewDataAdapter(List<HotdealRecyclerViewItem> viewItemList, int layoutType) {
        this.viewItemList.addAll(viewItemList);
        this.layoutType = layoutType;
    }

    public HotdealRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        mContext = parent.getContext();

        if (layoutType == HOTDEAL_ITEM_MAIN_LAYOUT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_hotdeal_recycler_item, parent, false);
        } else if (layoutType == HOTDEAL_ITEM_MORE_LAYOUT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotdeal_recycler_item, parent, false);
        }

        HotdealRecyclerViewHolder holder = new HotdealRecyclerViewHolder(view, layoutType);
        return holder;
    }

    @Override
    public void onBindViewHolder(HotdealRecyclerViewHolder holder, int position) {
        if (viewItemList != null) {
            HotdealRecyclerViewItem viewItem = viewItemList.get(position);

            if (layoutType == HOTDEAL_ITEM_MAIN_LAYOUT) {
                BaseApplication.getInstance().loadImage(mContext, viewItem.getUrl(), holder.getmImgUrl(), false);
                holder.getmTitle().setText(viewItem.getTitle());
                holder.getmMenue().setText(viewItem.getMenu());
//                holder.getmSale().setText(viewItem.getSale() +"%");
//                holder.getmDistance().setText(viewItem.getDistance());
            } else if (layoutType == HOTDEAL_ITEM_MORE_LAYOUT) {
                BaseApplication.getInstance().loadImage(mContext, viewItem.getUrl(), holder.getmImgUrl(), false);
            }
        }
    }

    public void reLoadData(List<HotdealRecyclerViewItem> viewItemList) {
        final HotdealDiffCallback diffCallback = new HotdealDiffCallback(this.viewItemList, viewItemList);
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