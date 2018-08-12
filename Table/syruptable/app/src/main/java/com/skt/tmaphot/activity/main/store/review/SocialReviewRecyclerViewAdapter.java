package com.skt.tmaphot.activity.main.store.review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skt.tmaphot.R;

import java.util.List;

public class SocialReviewRecyclerViewAdapter extends RecyclerView.Adapter<SocialReviewRecyclerViewHolder> {

    private List<SocialReviewItem> mItems;
    Context mContext;

    public SocialReviewRecyclerViewAdapter(List<SocialReviewItem> reviewItemList) {
        mItems = reviewItemList;
    }

    @Override
    public SocialReviewRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.storeinfo_socialreview_recycler_item, parent, false);
        SocialReviewRecyclerViewHolder ret = new SocialReviewRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(SocialReviewRecyclerViewHolder holder, final int position) {
//            MainApplication.loadUrlImage(mContext, mItems.get(position).getLoginImageUrl(), holder.loginImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, String.format("%d 선택", position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
