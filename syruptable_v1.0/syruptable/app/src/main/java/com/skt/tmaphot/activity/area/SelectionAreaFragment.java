package com.skt.tmaphot.activity.area;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
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

import com.google.gson.Gson;
import com.skt.tmaphot.R;
import com.skt.tmaphot.common.CommonUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class SelectionAreaFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private Map<Integer, List<AreaModel>> xL_areaMap;
    private Map<Integer, List<AreaModel>> l_areaMap;

    //지역
    private RecyclerView xL_areaRecyclerView;
    private XLAreaRecyclerViewAdapter xL_areaRecyclerViewAdapter;

    //구,시
    private RecyclerView l_areaRecyclerView;
    private LAreaRecyclerViewAdapter l_areaRecyclerViewAdapter;

    //동,읍,면
    private RecyclerView m_areaRecyclerView;
    private MAreaRecyclerViewAdapter m_areaRecyclerViewAdapter;


    public SelectionAreaFragment() {
    }

    public static Fragment newInstance(int sectionNumber) {
        SelectionAreaFragment fragment = new SelectionAreaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selectionarea_selection, container, false);

        xL_areaRecyclerView = (RecyclerView) rootView.findViewById(R.id.selectionarea_selection_xl_area_recyler);
        l_areaRecyclerView = (RecyclerView) rootView.findViewById(R.id.selectionarea_selection_l_area_recycler);
        m_areaRecyclerView = (RecyclerView) rootView.findViewById(R.id.selectionarea_selection_m_area_recycler);

//        LinearLayoutManager xl_layoutManager = new LinearLayoutManager(getContext());
//        xl_layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        LinearLayoutManager l_layoutManager = new LinearLayoutManager(getContext());
//        l_layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//        LinearLayoutManager m_layoutManager = new LinearLayoutManager(getContext());
//        m_layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

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

        AssetManager assetManager = getResources().getAssets();
        InputStream source = null;

        try {
            source = assetManager.open("area.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        Reader reader = new InputStreamReader(source);
        AreaModel[] dbDTOs = gson.fromJson(reader, AreaModel[].class);
        List<AreaModel> xl_jsonList = Arrays.asList(dbDTOs);
        xL_areaMap = xl_jsonList.stream().collect(Collectors.groupingBy(AreaModel::getDoCode));

        List<XLAreaItem> xL_areaItems = new ArrayList<>();
        TreeMap<Integer, List<AreaModel>> tm = new TreeMap<>(xL_areaMap);
        Iterator<Integer> iteratorKey = tm.keySet().iterator();   //키값 오름차순 정렬(기본)

        while (iteratorKey.hasNext()) {
            Integer key = iteratorKey.next();
            Log.d("TEST899 : ", key + " Count : " + tm.get(key).get(0).getDoTxt());
            xL_areaItems.add(new XLAreaItem(key, tm.get(key).get(0).getDoTxt()));
        }

        xL_areaRecyclerViewAdapter.fetchData(xL_areaItems);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xL_areaRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();
            }
        }, 1);
    }

    private class XLAreaItem {
        private int id;
        private String area;

        public XLAreaItem(int id, String area) {
            this.id = id;
            this.area = area;
        }

        public int getId() {
            return id;
        }

        public String getArea() {
            return area;
        }
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

    @SuppressLint("NewApi")
    private class XLAreaRecyclerViewAdapter extends RecyclerView.Adapter<XLAreaRecyclerViewHolder> {

        private List<XLAreaItem> mItems;
        Context mContext;
        private int selectedPosition = 0;

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


//                  List<Example> test2 = mcList.stream().filter(s -> s.getDoCode() == 11).collect(Collectors.toList());

                    List<AreaModel> test = xL_areaMap.get(mItems.get(position).getId());
                    Log.d("TEST899", "[size] :" + test.size());

                    l_areaMap =
//                          test.stream().collect(Collectors.groupingBy(Example::getSigunCode));
                            test.stream().collect(Collectors.groupingBy(e -> e.getSigunCode()));

                    TreeMap<Integer, List<AreaModel>> tm = new TreeMap<>(l_areaMap);
                    Iterator<Integer> iteratorKey = tm.keySet().iterator();   //키값 오름차순 정렬(기본)
                    //Iterator<String> iteratorKey = tm.descendingKeySet().iterator(); //키값 내림차순 정렬
                    List<LAreaItem> l_areaItems = new ArrayList<>();

                    while (iteratorKey.hasNext()) {
                        Integer key = iteratorKey.next();
//                        if(mItems.get(position).getId() != 36) {
                            l_areaItems.add(new LAreaItem(mItems.get(position).getId(), key, tm.get(key).get(0).getSigunTxt(), tm.get(key).get(0).getLati(), tm.get(key).get(0).getLongti()));
                            Log.d("TEST899 : ", "key:" + key + " value:" + tm.get(key).get(0).getSigunTxt() + " Lati:" + tm.get(key).get(0).getLati() + " Longti:" + tm.get(key).get(0).getLongti());
//                        }else{
//                            l_areaItems.add(new LAreaItem(mItems.get(position).getId(), key, tm.get(key).get(0).getDongTxt(), tm.get(key).get(0).getLati(), tm.get(key).get(0).getLongti()));
//                            Log.d("TEST899 : ", "key:" + key + " value:" + tm.get(key).get(0).getDongTxt() + " Lati:" + tm.get(key).get(0).getLati() + " Longti:" + tm.get(key).get(0).getLongti());
//                        }
                    }

                    l_areaRecyclerViewAdapter.fetchData(l_areaItems);
//                    l_areaRecyclerViewAdapter = new LAreaRecyclerViewAdapter(l_areaItems);
//                    l_areaRecyclerView.setAdapter(l_areaRecyclerViewAdapter);
//                    l_areaRecyclerViewAdapter.notifyDataSetChanged();

                    // 초기화
                    m_areaRecyclerViewAdapter.fetchData(new ArrayList<>());
//                    m_areaRecyclerViewAdapter = new MAreaRecyclerViewAdapter(new ArrayList<>());
//                    m_areaRecyclerView.setAdapter(m_areaRecyclerViewAdapter);
//                    m_areaRecyclerViewAdapter.notifyDataSetChanged();
                }
            });

//            MainApplication.loadUrlImage(mContext, mItems.get(position).getLoginImageUrl(), holder.loginImageView);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    if(slectView != null && slectView != v) {
////                        slectView.setSelected(false);
////                    }
////                    v.setSelected(true);
////                    slectView = v;
//
////                    listRecyclerViewAdapter = new ListAreaRecyclerViewAdapter(listItems[position]);
////                    listRecyclerView.setAdapter(listRecyclerViewAdapter);
////                    listRecyclerViewAdapter.notifyDataSetChanged();
//
////                    listRecyclerView.findViewHolderForAdapterPosition(position).itemView.performClick();
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }


    private class LAreaItem{
        private int preid;
        private int id;
        private String area;
        private String lati;
        private String longti;

        public LAreaItem(int preid, int id, String area, String lati, String longti) {
            this.id = id;
            this.area = area;
            this.lati = lati;
            this.longti = longti;
        }

        public int getPreid() {
            return preid;
        }

        public int getId() {
            return id;
        }

        public String getArea() {
            return area;
        }

        public String getLati() {
            return lati;
        }

        public String getLongti() {
            return longti;
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
        @SuppressLint("NewApi")
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

//                    Toast.makeText(mContext, "Position: " +  position, Toast.LENGTH_SHORT).show();

                    List<AreaModel> test = l_areaMap.get(mItems.get(position).getId());
                    Log.d("TEST899", "[=============size============] :" + test.size());

//                    for(Example ex : test){
//                        Log.d("TEST899", "[ " + ex.getDongTxt() + ex.getDoCode()+" ]");
//                    }

                    Map<Integer, List<AreaModel>> liteMap =
//                          test.stream().collect(Collectors.groupingBy(Example::getSigunCode));
                            test.stream().collect(Collectors.groupingBy(e -> e.getDongCode()));

                    TreeMap<Integer, List<AreaModel>> tm = new TreeMap<>(liteMap);
                    Iterator<Integer> iteratorKey = tm.keySet().iterator();   //키값 오름차순 정렬(기본)
                    //Iterator<String> iteratorKey = tm.descendingKeySet().iterator(); //키값 내림차순 정렬
                    List<MAreaItem> m_areaItems = new ArrayList<>();

                    while (iteratorKey.hasNext()) {
                        Integer key = iteratorKey.next();
                        if(mItems.get(position).getPreid() != 36){
                            m_areaItems.add(new MAreaItem(key, tm.get(key).get(0).getDongTxt(), tm.get(key).get(0).getLati(), tm.get(key).get(0).getLongti()));
                            Log.d("TEST899 : ", "key:" + key + " value:" + tm.get(key).get(0).getDongTxt() + " Lati:" + tm.get(key).get(0).getLati() + " Longti:" + tm.get(key).get(0).getLongti());
                        }else{
                            m_areaItems.add(new MAreaItem(key, tm.get(key).get(0).getRiTxt(), tm.get(key).get(0).getLati(), tm.get(key).get(0).getLongti()));
                            Log.d("TEST899 : ", "key:" + key + " value:" + tm.get(key).get(0).getRiTxt() + " Lati:" + tm.get(key).get(0).getLati() + " Longti:" + tm.get(key).get(0).getLongti());
                        }
                    }

                    m_areaRecyclerViewAdapter.fetchData(m_areaItems);
//                    m_areaRecyclerViewAdapter = new MAreaRecyclerViewAdapter(m_areaItems);
//                    m_areaRecyclerView.setAdapter(m_areaRecyclerViewAdapter);
//                    m_areaRecyclerViewAdapter.notifyDataSetChanged();
                }
            });


//            MainApplication.loadUrlImage(mContext, mItems.get(position).getLoginImageUrl(), holder.loginImageView);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if(slectView != null && slectView != v){
//                        slectView.setSelected(false);
//                    }
//                    v.setSelected(true);
//                    slectView = v;
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }


    private class MAreaItem {
        private int id;
        private String area;
        private String lati;
        private String longti;

        public MAreaItem(int id, String area, String lati, String longti) {
            this.id = id;
            this.area = area;
            this.lati = lati;
            this.longti = longti;
        }

        public int getId() {
            return id;
        }

        public String getArea() {
            return area;
        }

        public String getLati() {
            return lati;
        }

        public String getLongti() {
            return longti;
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


//            MainApplication.loadUrlImage(mContext, mItems.get(position).getLoginImageUrl(), holder.loginImageView);
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if(slectView != null && slectView != v){
//                        slectView.setSelected(false);
//                    }
//                    v.setSelected(true);
//                    slectView = v;
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }
}

