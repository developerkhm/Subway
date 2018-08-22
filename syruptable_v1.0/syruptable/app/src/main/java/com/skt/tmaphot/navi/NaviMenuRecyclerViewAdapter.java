package com.skt.tmaphot.navi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skt.tmaphot.R;
import java.util.List;

public class NaviMenuRecyclerViewAdapter extends RecyclerView.Adapter<NaviMenuRecyclerViewAdapter.ViewHolder> {

    private ClickEventListener mSampleEventListener;

    public interface ClickEventListener {
        void onReceivedEvent(String menuid);
    }

    public void setOnClickReceivedEvent(ClickEventListener listener){
        mSampleEventListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            mMenu = (TextView) itemView.findViewById(R.id.navi_recycler_menu);
        }
    }

    private List<NaviMenuItem> naviMenuItems;

    public NaviMenuRecyclerViewAdapter(List<NaviMenuItem> naviMenuItems) {
        this.naviMenuItems = naviMenuItems;
    }

    @Override
    public int getItemCount() {
        return naviMenuItems.size();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navi_menu_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        NaviMenuItem menu = naviMenuItems.get(position);
        holder.mMenu.setText(menu.getMenu());
        holder.mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSampleEventListener.onReceivedEvent(menu.getId());
            }
        });
    }
}
