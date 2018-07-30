package com.skt.tmaphot.activity.main.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skt.tmaphot.MainApplication;
import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.IRecyclerItem;
import com.skt.tmaphot.activity.IRecyclerViewDataAdapter;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewHolder;
import com.skt.tmaphot.activity.main.review.RealReviewRecyclerViewItem;

import java.util.ArrayList;

public class MainMenuRecyclerViewDataAdapter extends RecyclerView.Adapter<MainMenuRecyclerViewHolder> {

    private Context context;
    private ArrayList<MainMenuRecyclerViewItem> viewItemList;
    private MainMenuRecyclerViewHolder.OnMenuCilckListener listner;

    public MainMenuRecyclerViewDataAdapter(Context context, ArrayList<MainMenuRecyclerViewItem> viewItemList) {
        this.context = context;
        this.viewItemList = viewItemList;
    }

    public void setOnMenuCilckListener(MainMenuRecyclerViewHolder.OnMenuCilckListener listner){
        this.listner = listner;
    }

    @Override
    public MainMenuRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.main_menu_recycler_item, parent, false);

        // Create and return our customRecycler View Holder object.
        MainMenuRecyclerViewHolder ret = new MainMenuRecyclerViewHolder(itemView);
        ret.setOnMenuCilckListener(listner);
        return ret;
    }

    @Override
    public void onBindViewHolder(MainMenuRecyclerViewHolder holder, int position) {
        if(viewItemList!=null) {
            // Get car item dto in list.
            MainMenuRecyclerViewItem viewItem = viewItemList.get(position);


            if(viewItem != null) {
                // Set car item title.

                // 클릭리스너 전달용
                holder.setIdUrl(viewItem.getMenuType());


                // 아직 메뉴가 없어서 임시 구현
                if(viewItem.getMenuImageUrl() != null && viewItem.getMenuImageUrl() != "")
                {
                    MainApplication.loadUrlImage(context , viewItem.getMenuImageUrl(), holder.getImageView());
                }
                else{
                    holder.getImageView().setBackgroundResource(viewItem.getRes());
//                    MainApplication.loadImage(context, viewItem.getRes(), holder.getImageView());
                }
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