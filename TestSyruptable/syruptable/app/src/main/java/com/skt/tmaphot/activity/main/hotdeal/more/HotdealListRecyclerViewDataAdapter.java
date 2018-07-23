package com.skt.tmaphot.activity.main.hotdeal.more;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;
import java.util.List;

public class HotdealListRecyclerViewDataAdapter extends RecyclerView.Adapter<HotdealListRecyclerViewHolder> {

    private Context context;
    private List<HotdealListRecyclerViewItem> viewItemList;


    public HotdealListRecyclerViewDataAdapter(Context context, List<HotdealListRecyclerViewItem> viewItemList) {
        this.context = context;
        this.viewItemList = viewItemList;
    }

    @Override
    public HotdealListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.activity_main_recycler_hotdeal_list_item, parent, false);

        // Create and return our customRecycler View Holder object.
        HotdealListRecyclerViewHolder ret = new HotdealListRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(HotdealListRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get car item dto in list.
            HotdealListRecyclerViewItem viewItem = viewItemList.get(position);

            if(viewItem != null) {
                // Set car item title.
                MainApplication.loadUrlImage(context , viewItem.getUrl(), holder.getImageView());
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