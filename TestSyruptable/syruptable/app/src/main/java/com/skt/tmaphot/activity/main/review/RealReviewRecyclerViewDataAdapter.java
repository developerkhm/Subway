package com.skt.tmaphot.activity.main.review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;

import java.util.List;

public class RealReviewRecyclerViewDataAdapter extends RecyclerView.Adapter<RealReviewRecyclerViewHolder> {

    private Context context;
    private List<RealReviewRecyclerViewItem> viewItemList;


    public RealReviewRecyclerViewDataAdapter(Context context, List<RealReviewRecyclerViewItem> viewItemList) {
        this.context = context;
        this.viewItemList = viewItemList;
    }

    @Override
    public RealReviewRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.main_realreview_recycler_item, parent, false);

        // Create and return our customRecycler View Holder object.
        RealReviewRecyclerViewHolder ret = new RealReviewRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(RealReviewRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get car item dto in list.
            RealReviewRecyclerViewItem viewItem = viewItemList.get(position);

            if(viewItem != null) {
                // Set car item title.
                MainApplication.loadUrlImage(context , viewItem.getText(), holder.getImageView());

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