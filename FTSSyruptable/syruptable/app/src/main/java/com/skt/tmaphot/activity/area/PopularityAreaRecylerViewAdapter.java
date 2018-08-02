package com.skt.tmaphot.activity.area;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skt.tmaphot.R;

import java.util.HashMap;
import java.util.List;

public class PopularityAreaRecylerViewAdapter extends RecyclerView.Adapter<PopularityAreaRecylerViewHolder> {

    private List<PopularityAreaRecylerItem> mItems;
    private Context mContext;
    private HashMap<Integer, String> selectValueMap;

    public PopularityAreaRecylerViewAdapter(List<PopularityAreaRecylerItem> reviewItemList, HashMap<Integer, String> selectValueMap) {
        mItems = reviewItemList;
        this.selectValueMap = selectValueMap;
    }

    @Override
    public PopularityAreaRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.selectionaear_popularity_recycler_item, parent, false);
        PopularityAreaRecylerViewHolder ret = new PopularityAreaRecylerViewHolder(itemView);
        ret.setSelectValueMap(selectValueMap);
        return ret;
    }

    @Override
    public void onBindViewHolder(PopularityAreaRecylerViewHolder holder, final int position) {
        holder.getAreaName().setText(mItems.get(position).getAreaName());



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
}