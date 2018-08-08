package com.skt.tmaphot.activity.main.coupon;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.IRecyclerViewDataAdapter;

import java.util.ArrayList;
import java.util.List;

public class CouponRecyclerViewDataAdapter extends RecyclerView.Adapter<CouponRecyclerViewHolder>{

    public static final int COUPON_ITEM_MAIN_LAYOUT = 0;
    public static final int COUPON_ITEM_MORE_LAYOUT = 1;
    private Context mContext;

    private int layoutType;
    private List<CouponRecyclerViewItem> viewItemList = new ArrayList<>();

    public CouponRecyclerViewDataAdapter(List<CouponRecyclerViewItem> viewItemList, int layoutType) {
        this.viewItemList.addAll(viewItemList);
        this.layoutType = layoutType;
    }

    @Override
    public CouponRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        mContext = parent.getContext();

        if (layoutType == COUPON_ITEM_MAIN_LAYOUT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_coupon_recycler_item, parent, false);
        } else if (layoutType == COUPON_ITEM_MORE_LAYOUT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_recycler_item, parent, false);
        }

        CouponRecyclerViewHolder holder = new CouponRecyclerViewHolder(view, layoutType);
        return holder;
    }

    @Override
    public void onBindViewHolder(CouponRecyclerViewHolder holder, int position) {

        CouponRecyclerViewItem viewItem = viewItemList.get(position);

        if (layoutType == COUPON_ITEM_MAIN_LAYOUT) {
            BaseApplication.getInstance().loadImage(mContext, viewItem.getUrl(), holder.getmImgUrl(), false);
            holder.getmTitle().setText(viewItem.getTitle());
            holder.getmMenu().setText(viewItem.getMenu());
            holder.getmSale().setText(viewItem.getSale() +"%");
            holder.getmDistance().setText(viewItem.getDistance());
        } else if (layoutType == COUPON_ITEM_MORE_LAYOUT) {
            BaseApplication.getInstance().loadImage(mContext, viewItem.getUrl(), holder.getmImgUrl(), false);
            holder.getmTitle().setText(viewItem.getTitle());
            holder.getmMenu().setText(viewItem.getMenu());
            holder.getmSale().setText(viewItem.getSale() +"%");
            holder.getmType().setText(viewItem.getType());
            holder.getmPrePrice().setText(viewItem.getPreprice());
            holder.getmPrice().setText(viewItem.getPrice());
        }
    }

    public void reLoadData(List<CouponRecyclerViewItem> viewItemList) {
        final CouponDiffCallback diffCallback = new CouponDiffCallback(this.viewItemList, viewItemList);
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