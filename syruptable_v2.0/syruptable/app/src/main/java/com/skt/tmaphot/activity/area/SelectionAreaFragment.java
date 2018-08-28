package com.skt.tmaphot.activity.area;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.common.CommonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class SelectionAreaFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    //지역
    private RecyclerView xL_areaRecyclerView;
    private XLAreaRecyclerViewAdapter xL_areaRecyclerViewAdapter;

    //구,시
    private RecyclerView l_areaRecyclerView;
    private LAreaRecyclerViewAdapter l_areaRecyclerViewAdapter;

    //동,읍,면
    private RecyclerView m_areaRecyclerView;
    private MAreaRecyclerViewAdapter m_areaRecyclerViewAdapter;

    private DatabaseOpenHelper databaseOpenHelper;

    public SelectionAreaFragment() { }

    public static Fragment newInstance(int sectionNumber) {
        SelectionAreaFragment fragment = new SelectionAreaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selectionarea_selection, container, false);

        xL_areaRecyclerView = (RecyclerView) rootView.findViewById(R.id.selectionarea_selection_xl_area_recyler);
        l_areaRecyclerView = (RecyclerView) rootView.findViewById(R.id.selectionarea_selection_l_area_recycler);
        m_areaRecyclerView = (RecyclerView) rootView.findViewById(R.id.selectionarea_selection_m_area_recycler);

        xL_areaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        xL_areaRecyclerViewAdapter = new XLAreaRecyclerViewAdapter(new ArrayList<>());
        xL_areaRecyclerView.setAdapter(xL_areaRecyclerViewAdapter);
        xL_areaRecyclerView.setHasFixedSize(true);
        xL_areaRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(1, 1, 0, 0));

        l_areaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        l_areaRecyclerViewAdapter = new LAreaRecyclerViewAdapter(new ArrayList<>());
        l_areaRecyclerView.setAdapter(l_areaRecyclerViewAdapter);
        l_areaRecyclerView.setHasFixedSize(true);
        l_areaRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 1, 1, 0));

        m_areaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        m_areaRecyclerViewAdapter = new MAreaRecyclerViewAdapter(new ArrayList<>());
        m_areaRecyclerView.setAdapter(m_areaRecyclerViewAdapter);
        m_areaRecyclerView.setHasFixedSize(true);
        m_areaRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));

        new Thread(new Runnable() {
            @Override
            public void run() {

                //Preparing DB
                databaseOpenHelper = new DatabaseOpenHelper(getActivity(), "/data/data/com.skt.tmaphot/databases");
                try {
                    databaseOpenHelper.prepareDataBase();
                } catch (IOException io) {
                    throw new Error("Unable to create Database");
                }

                List<XLAreaItem> xl_areaList = databaseOpenHelper.getXLarea();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        xL_areaRecyclerViewAdapter.fetchData(xl_areaList);
                    }
                });

            }
        }).start();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                xL_areaRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
            }
        }, 1);
    }

    private class XLAreaRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView areaTextView;

        public XLAreaRecyclerViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {
                areaTextView = (TextView) itemView.findViewById(R.id.selectionarea_selection_area_txt);
            }
        }
    }

    private class XLAreaRecyclerViewAdapter extends RecyclerView.Adapter<XLAreaRecyclerViewHolder> {

        private List<XLAreaItem> mItems;
        Context mContext;
        private int selectedPosition = -1;

        public XLAreaRecyclerViewAdapter(List<XLAreaItem> reviewItemList) {
            mItems = reviewItemList;
        }

        public void fetchData(List<XLAreaItem> reviewItemList) {
            mItems.addAll(reviewItemList);
            this.notifyDataSetChanged();
        }

        @Override
        public XLAreaRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.selectionarea_selection_recycler_xl_area_item, parent, false);
            XLAreaRecyclerViewHolder ret = new XLAreaRecyclerViewHolder(itemView);
            return ret;
        }

        @Override
        public void onBindViewHolder(XLAreaRecyclerViewHolder holder, final int position) {

            holder.areaTextView.setText(mItems.get(position).getArea());

            if (selectedPosition == position)
                holder.itemView.setSelected(true);
            else
                holder.itemView.setSelected(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = position;
                    notifyDataSetChanged();
                    List<LAreaItem> lAreaItems = databaseOpenHelper.getLarea(mItems.get(position).getId());

                    l_areaRecyclerViewAdapter.fetchData(lAreaItems);
                    // 초기화
                    m_areaRecyclerViewAdapter.fetchData(new ArrayList<>());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private class LAreaRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView areaTextView;

        public LAreaRecyclerViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                areaTextView = (TextView) itemView.findViewById(R.id.selectionarea_selection_area_list_txt);
            }
        }
    }

    private class LAreaRecyclerViewAdapter extends RecyclerView.Adapter<LAreaRecyclerViewHolder> {

        private List<LAreaItem> mItems;
        Context mContext;
        private int selectedPosition = -1;

        public LAreaRecyclerViewAdapter(List<LAreaItem> reviewItemList) {
            mItems = reviewItemList;
        }

        public void fetchData(List<LAreaItem> reviewItemList) {
            mItems.clear();
            mItems.addAll(reviewItemList);
            selectedPosition = -1;
            this.notifyDataSetChanged();
        }

        @Override
        public LAreaRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.selectionarea_selection_recycler_l_area_item, parent, false);
            LAreaRecyclerViewHolder ret = new LAreaRecyclerViewHolder(itemView);
            return ret;
        }

        @Override
        public void onBindViewHolder(LAreaRecyclerViewHolder holder, final int position) {

            String txt = mItems.get(position).getArea();

            if (position == 0)
                txt = "전체";

            holder.areaTextView.setText(txt);

            if (selectedPosition == position)
                holder.itemView.setSelected(true);
            else
                holder.itemView.setSelected(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedPosition = position;
                    notifyDataSetChanged();
                    List<MAreaItem> mAreaItemList = databaseOpenHelper.getMarea(mItems.get(position).getPreid(), mItems.get(position).getId());

                    m_areaRecyclerViewAdapter.fetchData(mAreaItemList);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    private class MAreaRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView areaTextView;

        public MAreaRecyclerViewHolder(View itemView) {
            super(itemView);

            if (itemView != null) {
                areaTextView = (TextView) itemView.findViewById(R.id.selectionarea_selection_area_list_txt);
            }
        }
    }

    private class MAreaRecyclerViewAdapter extends RecyclerView.Adapter<MAreaRecyclerViewHolder> {

        private List<MAreaItem> mItems;
        Context mContext;
        private int selectedPosition = -1;

        public MAreaRecyclerViewAdapter(List<MAreaItem> reviewItemList) {
            mItems = reviewItemList;
        }

        public void fetchData(List<MAreaItem> reviewItemList) {
            mItems.clear();
            mItems.addAll(reviewItemList);
            selectedPosition = -1;
            this.notifyDataSetChanged();
        }

        @Override
        public MAreaRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.selectionarea_selection_recycler_m_area_item, parent, false);
            MAreaRecyclerViewHolder ret = new MAreaRecyclerViewHolder(itemView);
            return ret;
        }

        @SuppressLint("NewApi")
        @Override
        public void onBindViewHolder(MAreaRecyclerViewHolder holder, final int position) {

            String txt = mItems.get(position).getArea();

            if (position == 0)
                txt = "전체";

            holder.areaTextView.setText(txt);

            if (selectedPosition == position)
                holder.itemView.setSelected(true);
            else
                holder.itemView.setSelected(false);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    selectedPosition = position;
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}

