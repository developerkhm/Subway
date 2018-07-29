package com.skt.tmaphot.activity.area;

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


public class SelectionAreaFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    //시,도
    private RecyclerView areaRecyclerView;
    private AreaRecyclerViewAdapter areaRecyclerViewAdapter;
    private List<AreaItem> areaItems;


    //구
    private RecyclerView listReviewRecyclerView;
    private ListAreaRecyclerViewAdapter listRecyclerViewAdapter;
    private List<ListAreaItem>[] listItems;




    private View slectView;

    private Fragment fagmenttt = PopularityAreaTEST.newInstance(1);

    public SelectionAreaFragment() {
    }

    public static Fragment newInstance(int sectionNumber) {
        SelectionAreaFragment fragment = new SelectionAreaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selectionarea_selection, container, false);


        areaItems = new ArrayList<AreaItem>();
        areaItems.add(new AreaItem("1","서울"));
        areaItems.add(new AreaItem("1","경기"));
        areaItems.add(new AreaItem("1","부산"));
        areaItems.add(new AreaItem("1","강원"));
        areaItems.add(new AreaItem("1","인천"));
        areaItems.add(new AreaItem("1","충남"));
        areaItems.add(new AreaItem("1","충북"));
        areaItems.add(new AreaItem("1","대전"));
        areaItems.add(new AreaItem("1","경북"));
        areaItems.add(new AreaItem("1","경남"));
        areaItems.add(new AreaItem("1","대구"));
        areaItems.add(new AreaItem("1","울산"));
        areaItems.add(new AreaItem("1","광주"));
        areaItems.add(new AreaItem("1","전남"));
        areaItems.add(new AreaItem("1","전북"));
        areaItems.add(new AreaItem("1","제주"));



        listItems = new List[areaItems.size()];

        listItems[0] = new ArrayList<ListAreaItem>();
        listItems[0].add(new ListAreaItem("1","전체"));
        listItems[0].add(new ListAreaItem("1","송파/광진/강동"));
        listItems[0].add(new ListAreaItem("1","강남/서초"));
        listItems[0].add(new ListAreaItem("1","동작/관악"));
        listItems[0].add(new ListAreaItem("1","영등포/구로/금천"));
        listItems[0].add(new ListAreaItem("1","강서/양천"));
        listItems[0].add(new ListAreaItem("1","강북/성북/도봉/노원"));
        listItems[0].add(new ListAreaItem("1","종로/중구/용산"));
        listItems[0].add(new ListAreaItem("1","동대문/성동/중량"));
        listItems[0].add(new ListAreaItem("1","마포/서대문/은평"));

        listItems[1] = new ArrayList<ListAreaItem>();
        listItems[1].add(new ListAreaItem("1","전체"));
        listItems[1].add(new ListAreaItem("1","성남/하남/광주/이천/여주"));
        listItems[1].add(new ListAreaItem("1","과천/안양/군포/의왕"));
        listItems[1].add(new ListAreaItem("1","화성/수원/용인"));
        listItems[1].add(new ListAreaItem("1","오산/평택/안성"));
        listItems[1].add(new ListAreaItem("1","부천/광명시/시흥/안산"));
        listItems[1].add(new ListAreaItem("1","김포/고양/파주"));
        listItems[1].add(new ListAreaItem("1","의정부/양주/동두천"));
        listItems[1].add(new ListAreaItem("1","포천/연천"));
        listItems[1].add(new ListAreaItem("1","구리/남양주"));
        listItems[1].add(new ListAreaItem("1","가평/양평"));



        areaRecyclerView = (RecyclerView)rootView.findViewById(R.id.selectionarea_selection_area_recyler);
        listReviewRecyclerView = (RecyclerView)rootView.findViewById(R.id.selectionarea_selection_list_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        areaRecyclerView.setLayoutManager(layoutManager);
        areaRecyclerViewAdapter = new AreaRecyclerViewAdapter(areaItems);
        areaRecyclerView.setAdapter(areaRecyclerViewAdapter);
        areaRecyclerView.setHasFixedSize(true);

        //초기값

        areaRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(areaRecyclerView.findViewHolderForAdapterPosition(0).itemView != null){
                    areaRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
            }
        }, 1);
//        areaReviewRecyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.selectionarea_selection_area_txt).performClick();


        listReviewRecyclerView.setLayoutManager(layoutManager2);
        listRecyclerViewAdapter = new ListAreaRecyclerViewAdapter(listItems[0]);
        listReviewRecyclerView.setAdapter(listRecyclerViewAdapter);
        listReviewRecyclerView.setHasFixedSize(true);
//        areaReviewRecyclerView.setFocusable(false);

        return rootView;
    }


    private class AreaItem {
        private String id;
        private String area;

        public AreaItem(String id, String area) {
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


    private class AreaRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView areaTextView;


        public AreaRecyclerViewHolder(View itemView) {
            super(itemView);

            if(itemView != null)
            {
                areaTextView = (TextView)itemView.findViewById(R.id.selectionarea_selection_area_txt);
            }
        }
    }


    private class AreaRecyclerViewAdapter extends RecyclerView.Adapter<AreaRecyclerViewHolder> {

        private List<AreaItem> mItems;
        Context mContext;

        public AreaRecyclerViewAdapter(List<AreaItem> reviewItemList) {
            mItems = reviewItemList;
        }

        @Override
        public AreaRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.selectionarea_selection_recycler_area_item, parent, false);
            AreaRecyclerViewHolder ret = new AreaRecyclerViewHolder(itemView);
            return ret;
        }

        @Override
        public void onBindViewHolder(AreaRecyclerViewHolder holder, final int position) {

            holder.areaTextView.setText(mItems.get(position).getArea());


//            MainApplication.loadUrlImage(mContext, mItems.get(position).getLoginImageUrl(), holder.loginImageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(slectView != null && slectView != v)
                        slectView.setSelected(false);



                    v.setSelected(!v.isSelected());
                    slectView = v;

                    listRecyclerViewAdapter = new ListAreaRecyclerViewAdapter(listItems[position]);
                    listReviewRecyclerView.setAdapter(listRecyclerViewAdapter);
                    listRecyclerViewAdapter.notifyDataSetChanged();

                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }


    private class ListAreaItem {
        private String id;
        private String area;

        public ListAreaItem(String id, String area) {
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

    private class ListAreaRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView areaTextView;


        public ListAreaRecyclerViewHolder(View itemView) {
            super(itemView);

            if(itemView != null)
            {
                areaTextView = (TextView)itemView.findViewById(R.id.selectionarea_selection_area_list_txt);
            }
        }
    }

    private class ListAreaRecyclerViewAdapter extends RecyclerView.Adapter<ListAreaRecyclerViewHolder> {

        private List<ListAreaItem> mItems;
        Context mContext;

        public ListAreaRecyclerViewAdapter(List<ListAreaItem> reviewItemList) {
            mItems = reviewItemList;
        }

        @Override
        public ListAreaRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View itemView = layoutInflater.inflate(R.layout.selectionarea_selection_recycler_list_item, parent, false);
            ListAreaRecyclerViewHolder ret = new ListAreaRecyclerViewHolder(itemView);
            return ret;
        }

        @Override
        public void onBindViewHolder(ListAreaRecyclerViewHolder holder, final int position) {

            holder.areaTextView.setText(mItems.get(position).getArea());


//            MainApplication.loadUrlImage(mContext, mItems.get(position).getLoginImageUrl(), holder.loginImageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(slectView != null && slectView != v)
                        slectView.setSelected(false);



                    v.setSelected(!v.isSelected());
                    slectView = v;

                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}

