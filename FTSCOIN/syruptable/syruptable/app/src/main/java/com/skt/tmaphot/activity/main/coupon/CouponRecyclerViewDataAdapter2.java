package com.skt.tmaphot.activity.main.coupon;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.net.service.Item;

import java.util.ArrayList;
import java.util.List;

public class CouponRecyclerViewDataAdapter2 extends RecyclerView.Adapter<CouponRecyclerViewHolder>{

    public static final int COUPON_ITEM_MAIN_LAYOUT = 0;
    public static final int COUPON_ITEM_MORE_LAYOUT = 1;
    private Context mContext;

    private int layoutType;
    private List<Item> viewItemList = new ArrayList<>();

    public CouponRecyclerViewDataAdapter2(List<Item> viewItemList, int layoutType) {
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
//        holder.setOnEventListener(new CouponRecyclerViewHolder.EventListener() {
//            @Override
//            public void onReceivedEvent(int postion) {
//                Toast.makeText(mContext, "전달 받은 포지션 : " + viewItemList.get(postion).getMenu(), Toast.LENGTH_SHORT).show();
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder(CouponRecyclerViewHolder holder, int position) {

        Item viewItem = viewItemList.get(position);

        if (layoutType == COUPON_ITEM_MAIN_LAYOUT) {
            BaseApplication.getInstance().loadImage(mContext, viewItem.getProfileImage(), holder.getmImgUrl(), false);
            holder.getmTitle().setText(viewItem.getDisplayName());
            holder.getmMenu().setText(viewItem. getLocation());
            holder.getmSale().setText(viewItem. getLocation() +"%");
            holder.getmDistance().setText(viewItem. getLocation());
        } else if (layoutType == COUPON_ITEM_MORE_LAYOUT) {
            BaseApplication.getInstance().loadImage(mContext, viewItem.getProfileImage(), holder.getmImgUrl(), false);
            holder.getmTitle().setText(viewItem.getDisplayName());
            holder.getmMenu().setText(viewItem. getLocation());
            holder.getmSale().setText(viewItem. getLocation() +"%");
            holder.getmType().setText(viewItem. getLocation());
            holder.getmPrePrice().setText(viewItem. getLocation());
            holder.getmPrice().setText(viewItem. getLocation());
        }
    }

    public void reLoadData(List<Item> viewItemList) {
//        final CouponDiffCallback2 diffCallback = new CouponDiffCallback2(this.viewItemList, viewItemList);
//        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

//        this.viewItemList.clear();
        this.viewItemList.addAll(viewItemList);
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                diffResult.dispatchUpdatesTo(CouponRecyclerViewDataAdapter2.this);
//            }
//        });
        notifyDataSetChanged();
    }


    public void updateUsers(List<Item> items){
        Log.d("WHO1", "updateUsers size:" + items.size());
//

        Log.d("WHO1", "diffCallback oldcount:" + viewItemList.size() + "newcount :" + items.size());
        final CouponDiffCallback2 diffCallback = new CouponDiffCallback2(this.viewItemList, items);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback, false);

        int oldcount = viewItemList.size();
        this.viewItemList.clear();
        this.viewItemList.addAll(items);
        int newcount = viewItemList.size();
        Log.d("WHO1", "updateUsers oldcount:" + oldcount + "newcount :" + newcount);
//        notifyDataSetChanged();
//        notifyItemInserted(newcount -1);
//        notifyDataSetChanged(oldcount, newcount);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                diffResult.dispatchUpdatesTo(CouponRecyclerViewDataAdapter2.this);
            }
        });

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

//    @Override
//    public long getItemId(int position) {
////        return viewItemList.get(position).getId();
//        return Long.valueOf(viewItemList.get(position).getAccountId());
//    }

}