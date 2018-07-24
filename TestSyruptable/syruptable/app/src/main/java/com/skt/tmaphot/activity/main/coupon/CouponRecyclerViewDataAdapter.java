package com.skt.tmaphot.activity.main.coupon;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;

import java.util.List;

public class CouponRecyclerViewDataAdapter extends RecyclerView.Adapter<CouponRecyclerViewHolder> {

    private Context context;
    private List<CouponRecyclerViewItem> viewItemList;


    public CouponRecyclerViewDataAdapter(Context context, List<CouponRecyclerViewItem> viewItemList) {
        this.context = context;
        this.viewItemList = viewItemList;
    }


    public CouponRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.main_coupon_recycler_item, parent, false);

        // Create and return our customRecycler View Holder object.
        CouponRecyclerViewHolder ret = new CouponRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(CouponRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get car item dto in list.
            CouponRecyclerViewItem viewItem = viewItemList.get(position);

            if(viewItem != null) {
                // Set car item title.
                MainApplication.loadUrlImage(context , viewItem.getUrl(), holder.getmImgReview());
                holder.getmTextSale().setText(viewItem.getSale());
                holder.getmTitleText().setText(viewItem.getTitle());
                holder.getmMenueText().setText(viewItem.getMenu());
                holder.getmDistanceText().setText(viewItem.getDistance());
            }
        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(viewItemList!=null)
        {
            ret = viewItemList.size();
        }
        return ret;
    }
}