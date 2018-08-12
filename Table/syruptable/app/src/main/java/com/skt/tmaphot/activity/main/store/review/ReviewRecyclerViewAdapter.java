package com.skt.tmaphot.activity.main.store.review;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.blog.MyBlogActivity;

import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewHolder> {

    private List<ReviewItem> mItems;
    Context mContext;

    public ReviewRecyclerViewAdapter(List<ReviewItem> reviewItemList) {
        mItems = reviewItemList;
    }

    @Override
    public ReviewRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.storeinfo_review_recycler_item, parent, false);
        ReviewRecyclerViewHolder ret = new ReviewRecyclerViewHolder(itemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(ReviewRecyclerViewHolder holder, final int position) {

        BaseApplication.getInstance().loadImage(mContext, R.drawable.img_default_user, holder.loginImageView,true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, String.format("%d 선택", position + 1), Toast.LENGTH_SHORT).show();
//                //////////////////////////////////////////////// 임시 ////////////////////////////////////////////////
                BaseApplication.getInstance().ActivityStart(new Intent(mContext, MyBlogActivity.class), null);
//                //////////////////////////////////////////////////// 임시 ///////////////////////////////////////////////
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}