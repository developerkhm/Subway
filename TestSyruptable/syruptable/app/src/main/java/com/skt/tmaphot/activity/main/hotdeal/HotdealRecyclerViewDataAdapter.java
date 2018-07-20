package com.skt.tmaphot.activity.main.hotdeal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;

import java.util.List;

public class HotdealRecyclerViewDataAdapter extends RecyclerView.Adapter<HotdealRecyclerViewHolder> {

    private Context context;
    private List<HotdealRecyclerViewItem> viewItemList;


    public HotdealRecyclerViewDataAdapter(Context context, List<HotdealRecyclerViewItem> viewItemList) {
        this.context = context;
        this.viewItemList = viewItemList;
    }

    @Override
    public HotdealRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.activity_main_recycler_hotdeal_item, parent, false);

        // Create and return our customRecycler View Holder object.
        HotdealRecyclerViewHolder ret = new HotdealRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(HotdealRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get car item dto in list.
            HotdealRecyclerViewItem viewItem = viewItemList.get(position);

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