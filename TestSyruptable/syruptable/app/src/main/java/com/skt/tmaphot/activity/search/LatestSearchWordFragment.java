package com.skt.tmaphot.activity.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skt.tmaphot.R;

import java.util.ArrayList;
import java.util.List;


public class LatestSearchWordFragment extends Fragment {


    private RecyclerView latestRecyclerView;
    private LatestRecyclerViewAdapter latestRecyclerViewAdapter;
    private List<LatestItem> latestItems;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public LatestSearchWordFragment() {
    }

    public static Fragment newInstance(int sectionNumber) {
        LatestSearchWordFragment fragment = new LatestSearchWordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_latest_searchword_layout, container, false);

        latestItems = new ArrayList<LatestItem>();
        latestItems.add(new LatestItem("1", "이태원 음식점"));
        latestItems.add(new LatestItem("1", "삼국지 삼겹살"));
        latestItems.add(new LatestItem("1", "선릉"));
        latestItems.add(new LatestItem("1", "뽕나무쟁이 선릉"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        latestRecyclerView = (RecyclerView) rootView.findViewById(R.id.search_latest_recycler);
        latestRecyclerView.setLayoutManager(layoutManager);
        latestRecyclerViewAdapter = new LatestRecyclerViewAdapter(latestItems);
        latestRecyclerView.setAdapter(latestRecyclerViewAdapter);
        latestRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    private class LatestItem {
        private String id;
        private String area;

        public LatestItem(String id, String area) {
            this.id = id;
            this.area = area;
        }

        public String getId() {
            return id;
        }

        public String getArea() {
            return area;
        }
    }

    private class RecommandRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView areaTextView;


        public RecommandRecyclerViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {
                areaTextView = (TextView) itemView.findViewById(R.id.search_latest_recycler_txt);
            }
        }
    }

    private class LatestRecyclerViewAdapter extends RecyclerView.Adapter<RecommandRecyclerViewHolder> {

        private List<LatestItem> mItems;
        Context mContext;

        public LatestRecyclerViewAdapter(List<LatestItem> reviewItemList) {
            mItems = reviewItemList;
        }

        @Override
        public RecommandRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.search_latest_recycler_item, parent, false);
            RecommandRecyclerViewHolder ret = new RecommandRecyclerViewHolder(itemView);
            return ret;
        }

        @Override
        public void onBindViewHolder(RecommandRecyclerViewHolder holder, final int position) {

            holder.areaTextView.setText(mItems.get(position).getArea());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public void saveSearchKeyword(String keyword){
       // 여기서부터 해야됨
    }
}

