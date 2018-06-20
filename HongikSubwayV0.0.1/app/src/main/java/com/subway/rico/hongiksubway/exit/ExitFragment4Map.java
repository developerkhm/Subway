package com.subway.rico.hongiksubway.exit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;
import com.subway.rico.hongiksubway.common.CommonUtil;
import com.subway.rico.hongiksubway.R;

import java.util.ArrayList;

public class ExitFragment4Map extends Fragment {
    private ImageView mImg_Title, mImg_Main;
    private int mImg_res;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerViewAdapter mAdapter;


    private final int MAX_TITLE_IMG_W = 2160;
    private final int MAX_TITLEIMG_H = 798;
    private final int MAX_MAIN_W = 2160;
    private final int MAX_MAIN_H = 1146;
    private final int MAX_RECYLER_W = 2160;
    private final int MAX_RECYLER_H = 0;


    private static final String CLIENT_ID = "sziGn9EE7vFi11vhnWBl";// 애플리케이션 클라이언트 아이디 값


    private NMapContext mMapContext;

    public ExitFragment4Map() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit_fragment4_map, container, false);

        mImg_Title = (ImageView) view.findViewById(R.id.img_title);
        mImg_Title.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_TITLE_IMG_W);
        mImg_Title.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_TITLEIMG_H);
        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.exit_head, mImg_Title);

        mImg_res = R.drawable.exit_main_3;
        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
//        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_W);
//        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_H);
//        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);
        mImg_Main.setVisibility(View.GONE);

        LinearLayout test = (LinearLayout)view.findViewById(R.id.lay_main);
        test.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_W);
        test.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_H);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_RECYLER_W);
        //mRecyclerView.getLayoutParams().height = utilGetViewWidthPx((3840-1146));

        setRecycle();


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext =  new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NMapView mapView = (NMapView)getView().findViewById(R.id.mapView);
        mapView.setClientId(CLIENT_ID);// 클라이언트 아이디 설정
        mMapContext.setupMapView(mapView);
    }


    public class Item {
        public int res;

        public int getRes() {
            return res;
        }

        public Item(int res) {
            this.res = res;
        }
    }

    private void setRecycle() {
        mLayoutManager = new LinearLayoutManager(getContext());
        //mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // ArrayList 에 Item 객체(데이터) 넣기
        ArrayList<Item> items = new ArrayList();

        items.add(new Item(R.drawable.exit_main_4_banner_sample));
        items.add(new Item(R.drawable.exit_main_4_banner_sample_2));
        items.add(new Item(R.drawable.exit_main_4_banner_sample_3));
        items.add(new Item(R.drawable.exit_main_4_banner_sample));

        mRecyclerView.setLayoutManager(mLayoutManager); // LinearLayout으로 설정
        mRecyclerView.setItemAnimator(new DefaultItemAnimator()); // Animation Defualt 설정
        // mRecyclerView.addItemDecoration(new RecyclerViewDecoration(this, RecyclerViewDecoration.VERTICAL_LIST)); //Decoration 설정
        mAdapter = new RecyclerViewAdapter(items);
        mRecyclerView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        private ArrayList<Item> mItems;
        Context mContext;

        public RecyclerViewAdapter(ArrayList itemList) {
            mItems = itemList;
        }

        // 필수 오버라이드 : View 생성, ViewHolder 호출
        @Override

        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exit_main_4_recycle_item, parent, false);
            mContext = parent.getContext();
            RecyclerViewHolder holder = new RecyclerViewHolder(v);
            return holder;
        }

        // 필수 오버라이드 : 재활용되는 View 가 호출, Adapter 가 해당 position 에 해당하는 데이터를 결합
        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
            // 해당 position 에 해당하는 데이터 결합
//            holder.mIndex.setText(mItems.get(position).index);
            CommonUtil.getInstance().loadImage(mContext, mItems.get(position).res, holder.mRes);
//            holder.mName.setText(mItems.get(position).name);
            Log.d("test", "onBindViewHolder : 호출 호출 호출");

            // 이벤트처리 : 생성된 List 중 선택된 목록번호를 Toast로 출력
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mContext, String.format("%d 선택", position + 1), Toast.LENGTH_SHORT).show();
                    CommonUtil.getInstance().callToast((Activity) mContext);
                }
            });
        }

        // 필수 오버라이드 : 데이터 갯수 반환
        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mRes;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mRes = (ImageView) itemView.findViewById(R.id.index);
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
