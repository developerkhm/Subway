package com.skt.tmaphot.activity.area;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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


public class SelectionAreaFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    //시,도
    private RecyclerView areaRecyclerView;
    private AreaRecyclerViewAdapter areaRecyclerViewAdapter;
    private List<AreaItem> areaItems;


    //구
    private RecyclerView listRecyclerView;
    private ListAreaRecyclerViewAdapter listRecyclerViewAdapter;
    private List<ListAreaItem>[] listItems;


    private View slectView;



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

        listItems[2] = new ArrayList<ListAreaItem>();
        listItems[2].add(new ListAreaItem("1","전체"));
        listItems[2].add(new ListAreaItem("1","기장"));
        listItems[2].add(new ListAreaItem("1","금정구(부산대)"));
        listItems[2].add(new ListAreaItem("1","해운대,송정"));
        listItems[2].add(new ListAreaItem("1","동래구(온천장,명륜,사직)"));
        listItems[2].add(new ListAreaItem("1","연제구(연산R,토곡,시청)"));
        listItems[2].add(new ListAreaItem("1","북구(덕천로타리,구포)"));
        listItems[2].add(new ListAreaItem("1","남구(경성대,용호동)"));
        listItems[2].add(new ListAreaItem("1","광안리(민락,수영로타리)"));
        listItems[2].add(new ListAreaItem("1","부산진구(서면,부전,양정)"));
        listItems[2].add(new ListAreaItem("1","동구(부산역)"));
        listItems[2].add(new ListAreaItem("1","중구(남포동)"));
        listItems[2].add(new ListAreaItem("1","영도"));
        listItems[2].add(new ListAreaItem("1","서구(송도)"));
        listItems[2].add(new ListAreaItem("1","사상구(사상역)"));
        listItems[2].add(new ListAreaItem("1","사하구(괴정,하단오거리,동아대)"));
        listItems[2].add(new ListAreaItem("1","강서구(명지)"));

        listItems[3] = new ArrayList<ListAreaItem>();
        listItems[3].add(new ListAreaItem("1","전체"));
        listItems[3].add(new ListAreaItem("1","동해/삼척/태백"));
        listItems[3].add(new ListAreaItem("1","강릉/평창/정선/영월"));
        listItems[3].add(new ListAreaItem("1","원주/횡성/홍천"));
        listItems[3].add(new ListAreaItem("1","속초/양양/고성"));
        listItems[3].add(new ListAreaItem("1","춘천/인제/철원/화천/양구"));

        listItems[4] = new ArrayList<ListAreaItem>();
        listItems[4].add(new ListAreaItem("1","전체"));
        listItems[4].add(new ListAreaItem("1","서구/계양/부평"));
        listItems[4].add(new ListAreaItem("1","중구/동구/남구/강화"));
        listItems[4].add(new ListAreaItem("1","남동/연수/옹진"));

        listItems[5] = new ArrayList<ListAreaItem>();
        listItems[5].add(new ListAreaItem("1","전체"));
        listItems[5].add(new ListAreaItem("1","계룡(계룡대)/세종(조치원)"));
        listItems[5].add(new ListAreaItem("1","공주(동학사,계룡산)"));
        listItems[5].add(new ListAreaItem("1","금산/논산/부여"));
        listItems[5].add(new ListAreaItem("1","당진/예산/홍성"));
        listItems[5].add(new ListAreaItem("1","서산/태안(안면도)"));
        listItems[5].add(new ListAreaItem("1","보령(대천)/서천/청양"));
        listItems[5].add(new ListAreaItem("1","아산(아산온천)"));
        listItems[5].add(new ListAreaItem("1","천안 동남구(천안역)"));
        listItems[5].add(new ListAreaItem("1","천안 서북구(성정동)"));

        listItems[6] = new ArrayList<ListAreaItem>();
        listItems[6].add(new ListAreaItem("1","전체"));
        listItems[6].add(new ListAreaItem("1","보은/옥천/영동"));
        listItems[6].add(new ListAreaItem("1","제천/단양"));
        listItems[6].add(new ListAreaItem("1","진천/괴산/증평/음성"));
        listItems[6].add(new ListAreaItem("1","청주 상당/서원구"));
        listItems[6].add(new ListAreaItem("1","청주 청원구"));
        listItems[6].add(new ListAreaItem("1","청주 흥덕구(가경동)"));
        listItems[6].add(new ListAreaItem("1","충주"));

        listItems[7] = new ArrayList<ListAreaItem>();
        listItems[7].add(new ListAreaItem("1","전체"));
        listItems[7].add(new ListAreaItem("1","대덕구(신탄진)"));
        listItems[7].add(new ListAreaItem("1","동구(용전동,복합터미널)"));
        listItems[7].add(new ListAreaItem("1","서구(둔산동,대전청사)"));
        listItems[7].add(new ListAreaItem("1","유성구(유성오천,충남대)"));
        listItems[7].add(new ListAreaItem("1","중구(은행동,한밭구장)"));


        listItems[8] = new ArrayList<ListAreaItem>();
        listItems[8].add(new ListAreaItem("1","전체"));
        listItems[8].add(new ListAreaItem("1","문경/상주/예천"));
        listItems[8].add(new ListAreaItem("1","영주/봉화/울진"));
        listItems[8].add(new ListAreaItem("1","김천/성주/고령/칠곡"));
        listItems[8].add(new ListAreaItem("1","구미/안동/의성/군위"));
        listItems[8].add(new ListAreaItem("1","포항/영양/영덕/청송"));
        listItems[8].add(new ListAreaItem("1","영천/경산/경주/청도"));
        listItems[8].add(new ListAreaItem("1","울릉"));


        listItems[9] = new ArrayList<ListAreaItem>();
        listItems[9].add(new ListAreaItem("1","전체"));
        listItems[9].add(new ListAreaItem("1","하동/사천/진주"));
        listItems[9].add(new ListAreaItem("1","창녕/함안/밀양/양산"));
        listItems[9].add(new ListAreaItem("1","마산/창원/진해/김해"));
        listItems[9].add(new ListAreaItem("1","통영/거제/남해/고성"));
        listItems[9].add(new ListAreaItem("1","거창/함양/산청/합천/의령"));


        listItems[10] = new ArrayList<ListAreaItem>();
        listItems[10].add(new ListAreaItem("1","전체"));
        listItems[10].add(new ListAreaItem("1","달서/서구/달성군"));
        listItems[10].add(new ListAreaItem("1","수성/동구"));
        listItems[10].add(new ListAreaItem("1","중구/북구/남구"));


        listItems[11] = new ArrayList<ListAreaItem>();
        listItems[11].add(new ListAreaItem("1","전체"));
        listItems[11].add(new ListAreaItem("1","중구/동구/울주군"));
        listItems[11].add(new ListAreaItem("1","남구/북구"));

        listItems[12] = new ArrayList<ListAreaItem>();
        listItems[12].add(new ListAreaItem("1","전체"));
        listItems[12].add(new ListAreaItem("1","서구"));
        listItems[12].add(new ListAreaItem("1","동구/북구"));
        listItems[12].add(new ListAreaItem("1","광산구/남구"));

        listItems[13] = new ArrayList<ListAreaItem>();
        listItems[13].add(new ListAreaItem("1","전체"));
        listItems[13].add(new ListAreaItem("1","무안/함평/영광/장성"));
        listItems[13].add(new ListAreaItem("1","담양/곡성/구례"));
        listItems[13].add(new ListAreaItem("1","목포/나주/영암"));
        listItems[13].add(new ListAreaItem("1","순천/광양/화순/여수"));
        listItems[13].add(new ListAreaItem("1","해남/강진/장흥/보성/고흥/완도"));

        listItems[14] = new ArrayList<ListAreaItem>();
        listItems[14].add(new ListAreaItem("1","전체"));
        listItems[14].add(new ListAreaItem("1","전주/완주"));
        listItems[14].add(new ListAreaItem("1","군산/익산"));
        listItems[14].add(new ListAreaItem("1","김제/부안/정읍/고창/남원/무주/진안/임실"));

        listItems[15] = new ArrayList<ListAreaItem>();
        listItems[15].add(new ListAreaItem("1","전체"));
        listItems[15].add(new ListAreaItem("1","제주"));
        listItems[15].add(new ListAreaItem("1","서귀포"));


        areaRecyclerView = (RecyclerView)rootView.findViewById(R.id.selectionarea_selection_area_recyler);
        listRecyclerView = (RecyclerView)rootView.findViewById(R.id.selectionarea_selection_list_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);

        areaRecyclerView.setLayoutManager(layoutManager);
        areaRecyclerViewAdapter = new AreaRecyclerViewAdapter(areaItems);
        areaRecyclerView.setAdapter(areaRecyclerViewAdapter);
        areaRecyclerView.setHasFixedSize(true);
        areaRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(1, 1, 0, 0));

        //초기값

//        areaRecyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(areaRecyclerView.findViewHolderForAdapterPosition(0).itemView != null){
//                    areaRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
//                }
//            }
//        }, 1);
//        areaReviewRecyclerView.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.selectionarea_selection_area_txt).performClick();


        listRecyclerView.setLayoutManager(layoutManager2);
        listRecyclerViewAdapter = new ListAreaRecyclerViewAdapter(listItems[0]);
        listRecyclerView.setAdapter(listRecyclerViewAdapter);
        listRecyclerView.setHasFixedSize(true);
//        areaReviewRecyclerView.setFocusable(false);
        listRecyclerView.addItemDecoration(CommonUtil.getInstance().new SpacesItemDecoration(0, 0, 1, 0));



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                areaRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
            }
        }, 1);
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

                    if(slectView != null && slectView != v) {
                        slectView.setSelected(false);
                    }
                    v.setSelected(true);
                    slectView = v;

                    listRecyclerViewAdapter = new ListAreaRecyclerViewAdapter(listItems[position]);
                    listRecyclerView.setAdapter(listRecyclerViewAdapter);
                    listRecyclerViewAdapter.notifyDataSetChanged();

//                    listRecyclerView.findViewHolderForAdapterPosition(position).itemView.performClick();
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

                    if(slectView != null && slectView != v){
                        slectView.setSelected(false);
                    }
                    v.setSelected(true);
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

