package com.skt.tmaphot.activity.main.review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.IRecyclerItem;
import com.skt.tmaphot.activity.IRecyclerViewDataAdapter;

import java.util.ArrayList;

public class RealReviewRecyclerViewDataAdapter extends RecyclerView.Adapter<RealReviewRecyclerViewHolder> implements IRecyclerViewDataAdapter {

    private Context context;
    private ArrayList<IRecyclerItem> viewItemList;


    public RealReviewRecyclerViewDataAdapter(Context context, ArrayList<IRecyclerItem> viewItemList) {
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
            RealReviewRecyclerViewItem viewItem = (RealReviewRecyclerViewItem)viewItemList.get(position);

            if(viewItem != null) {
                // Set car item title.
                BaseApplication.getInstance().loadImage(context , viewItem.getText(), holder.getImageView(), false);

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

    @Override
    public void notifyData() {
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyChanged(int start, int last) {
        Log.d("FAB", "RealReviewRecyclerViewDataAdapter [start] :" + start + " [last]" + last);
        this.notifyItemRangeChanged(start, last);
    }
}