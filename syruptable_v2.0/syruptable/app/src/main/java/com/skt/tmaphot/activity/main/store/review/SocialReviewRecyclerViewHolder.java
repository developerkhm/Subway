package com.skt.tmaphot.activity.main.store.review;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.R;

public class SocialReviewRecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView description;
    public TextView link;

    public SocialReviewRecyclerViewHolder(View itemView) {
        super(itemView);

        title = (TextView)itemView.findViewById(R.id.storeinfo_socialreview_title);
        description = (TextView)itemView.findViewById(R.id.storeinfo_socialreview_decription);
        link = (TextView)itemView.findViewById(R.id.storeinfo_socialreview_link);
    }
}
