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
import com.skt.tmaphot.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;


public class RecommanSearchWordFragment extends Fragment {


    private RecyclerView recommandRecyclerView;
    private RecommandRecyclerViewAdapter recommandRecyclerViewAdapter;
    private List<RecommandItem> recommandItems;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public RecommanSearchWordFragment() {
    }

    public static Fragment newInstance(int sectionNumber) {
        RecommanSearchWordFragment fragment = new RecommanSearchWordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recommand_searchword_layout, container, false);

        recommandItems = new ArrayList<RecommandItem>();
        recommandItems.add(new RecommandItem("1","반경 1km 음식정"));
        recommandItems.add(new RecommandItem("1","반경 1km 술집"));
        recommandItems.add(new RecommandItem("1","반경 1km 50% 할인 술집"));
        recommandItems.add(new RecommandItem("1","인기포차 - 코다차야"));
        recommandItems.add(new RecommandItem("1","농장 직송 고급 돼지 - 제주통도야지"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recommandRecyclerView = (RecyclerView)rootView.findViewById(R.id.search_recommand_recycler);
        recommandRecyclerView.setLayoutManager(layoutManager);
        recommandRecyclerViewAdapter = new RecommandRecyclerViewAdapter(recommandItems);
        recommandRecyclerView.setAdapter(recommandRecyclerViewAdapter);
        recommandRecyclerView.setHasFixedSize(true);
        recommandRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));

        return rootView;
    }

    private class RecommandItem {
        private String id;
        private String area;

        public RecommandItem(String id, String area) {
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

            if(itemView != null)
            {
                areaTextView = (TextView)itemView.findViewById(R.id.search_recommand_recycler_txt);
            }
        }
    }

    private class RecommandRecyclerViewAdapter extends RecyclerView.Adapter<RecommandRecyclerViewHolder> {

        private List<RecommandItem> mItems;
        Context mContext;

        public RecommandRecyclerViewAdapter(List<RecommandItem> reviewItemList) {
            mItems = reviewItemList;
        }

        @Override
        public RecommandRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.search_recommand_recycler_item, parent, false);
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
}

