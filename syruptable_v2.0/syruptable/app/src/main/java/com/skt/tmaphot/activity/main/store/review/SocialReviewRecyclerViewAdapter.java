package com.skt.tmaphot.activity.main.store.review;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.WebViewActivity;
import com.skt.tmaphot.net.model.store.SocialListItem;

import java.util.ArrayList;
import java.util.List;

public class SocialReviewRecyclerViewAdapter extends RecyclerView.Adapter<SocialReviewRecyclerViewHolder> {

    private List<SocialListItem> mItems = new ArrayList<>();
    Context mContext;

    public SocialReviewRecyclerViewAdapter(List<SocialListItem> socialReview) {
        mItems.addAll(socialReview);
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

        holder.title.setText(Html.fromHtml(mItems.get(position).getTitle()));
        holder.description.setText(Html.fromHtml(mItems.get(position).getDescription()));
        holder.link.setText(mItems.get(position).getLink());


        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("urlid", mItems.get(position).getLink());
                BaseApplication.getInstance().ActivityStart(intent, null);
            }
        });


//            MainApplication.loadUrlImage(mContext, mItems.get(position).getLoginImageUrl(), holder.loginImageView);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, String.format("%d 선택", position + 1), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public void fetchData(List<SocialListItem> socialReview)
    {
        mItems.addAll(socialReview);
        this.notifyDataSetChanged();
    }
}
