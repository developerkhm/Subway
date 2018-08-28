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
import android.widget.Toast;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.search.db.DatabaseHelper;
import com.skt.tmaphot.activity.search.db.SearchWord;
import com.skt.tmaphot.common.CommonUtil;

import java.util.List;


public class LatestSearchWordFragment extends Fragment {


    private RecyclerView latestRecyclerView;
    private LatestRecyclerViewAdapter latestRecyclerViewAdapter;
    private List<SearchWord> searchWords;

    private DatabaseHelper db;


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

        db = new DatabaseHelper(getActivity());
        searchWords = db.getAll();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        latestRecyclerView = (RecyclerView) rootView.findViewById(R.id.search_latest_recycler);
        latestRecyclerView.setLayoutManager(layoutManager);
        latestRecyclerViewAdapter = new LatestRecyclerViewAdapter(searchWords);
        latestRecyclerView.setAdapter(latestRecyclerViewAdapter);
        latestRecyclerView.setHasFixedSize(true);
        latestRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));

        return rootView;
    }


    private class RecommandRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView keywordTextView;
        public TextView keywordDelete;


        public RecommandRecyclerViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                keywordTextView = (TextView) itemView.findViewById(R.id.search_latest_keyword);
                keywordDelete = (TextView) itemView.findViewById(R.id.search_latest_keyword_delete);
            }
        }
    }

    private class LatestRecyclerViewAdapter extends RecyclerView.Adapter<RecommandRecyclerViewHolder> {

        private List<SearchWord> mItems;
        Context mContext;

        public LatestRecyclerViewAdapter(List<SearchWord> reviewItemList) {
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

            holder.keywordTextView.setText(mItems.get(position).getKeyword());
            holder.keywordDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.delete(mItems.get(position).getId());
                    mItems.remove(position);
                    latestRecyclerViewAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public void saveSearchKeyword(String keyword){
        if(db != null)
        {
//            int totalCount = db.getTotalCount();
            int deleteCount = db.delete(keyword);

            if(deleteCount > 0){
                db.add(new SearchWord(keyword));
                searchWords.clear();
                searchWords = db.getAll();
                latestRecyclerViewAdapter = new LatestRecyclerViewAdapter(searchWords);
                latestRecyclerView.setAdapter(latestRecyclerViewAdapter);
            }
            else{
                db.add(new SearchWord(keyword));
                searchWords.add(0, db.getLast());
            }

            latestRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}

