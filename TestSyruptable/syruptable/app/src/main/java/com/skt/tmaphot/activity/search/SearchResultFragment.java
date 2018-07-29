package com.skt.tmaphot.activity.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.area.PopularityAreaTEST;

import java.util.ArrayList;
import java.util.List;


public class SearchResultFragment extends Fragment {



    private Fragment fagmenttt = PopularityAreaTEST.newInstance(1);

    private RecyclerView searchRecyclerView;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private List<SearchItem> searchItems;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public SearchResultFragment() {
    }

    public static Fragment newInstance(int sectionNumber) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_result_layout, container, false);

        searchItems = new ArrayList<SearchItem>();
        searchItems.add(new SearchItem("1","선릉 맛집"));
        searchItems.add(new SearchItem("1","기타..."));
        searchItems.add(new SearchItem("1","검색 키워드"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        searchRecyclerView = (RecyclerView)rootView.findViewById(R.id.search_search_recycler);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(searchItems);
        searchRecyclerView.setAdapter(searchRecyclerViewAdapter);
        searchRecyclerView.setHasFixedSize(true);

        return rootView;
    }

    private class SearchItem {
        private String id;
        private String area;

        public SearchItem(String id, String area) {
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
                areaTextView = (TextView)itemView.findViewById(R.id.search_search_recycler_txt);
            }
        }
    }

    private class SearchRecyclerViewAdapter extends RecyclerView.Adapter<RecommandRecyclerViewHolder> {

        private List<SearchItem> mItems;
        Context mContext;

        public SearchRecyclerViewAdapter(List<SearchItem> reviewItemList) {
            mItems = reviewItemList;
        }

        @Override
        public RecommandRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.search_search_recycler_item, parent, false);
            RecommandRecyclerViewHolder ret = new RecommandRecyclerViewHolder(itemView);
            return ret;
        }

        @Override
        public void onBindViewHolder(RecommandRecyclerViewHolder holder, final int position) {

            holder.areaTextView.setText(mItems.get(position).getArea());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                    /*
//                     * When this container fragment is created, we fill it with our first
//                     * "real" fragment
//                     */
//                    transaction.replace(R.id.search_main, fagmenttt);
//                    transaction.addToBackStack(null);
//                    transaction.commit();

                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}

