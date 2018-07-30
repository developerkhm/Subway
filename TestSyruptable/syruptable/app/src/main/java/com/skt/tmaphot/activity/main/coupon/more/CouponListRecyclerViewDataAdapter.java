package com.skt.tmaphot.activity.main.coupon.more;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;

import java.util.List;

public class CouponListRecyclerViewDataAdapter extends RecyclerView.Adapter<CouponListRecyclerViewHolder> {

    private Context context;
    private List<CouponListRecyclerViewItem> viewItemList;


    public CouponListRecyclerViewDataAdapter(Context context, List<CouponListRecyclerViewItem> viewItemList) {
        this.context = context;
        this.viewItemList = viewItemList;
    }

    @Override
    public CouponListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.coupon_recycler_item, parent, false);

        // Create and return our customRecycler View Holder object.
        CouponListRecyclerViewHolder ret = new CouponListRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(CouponListRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get car item dto in list.
            CouponListRecyclerViewItem viewItem = viewItemList.get(position);

            if(viewItem != null) {
                // Set car item title.
                MainApplication.loadUrlImage(context , viewItem.getUrl(), holder.imageview);
                holder.nameText.setText(viewItem.getName());
                holder.menuText.setText(viewItem.getMenu());
                holder.priceText.setText(viewItem.getPrice());
                holder.prepriceText.setText(viewItem.getPreprice());
                holder.typeText.setText(viewItem.getType());
                holder.saleText.setText(viewItem.getSale() +"%");
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