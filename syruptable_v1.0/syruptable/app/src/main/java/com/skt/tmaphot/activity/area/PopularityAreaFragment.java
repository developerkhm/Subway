package com.skt.tmaphot.activity.area;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.skt.tmaphot.R;
import com.skt.tmaphot.HotPlaceResultActivity;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PopularityAreaFragment extends BaseFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private RecyclerView popularityAreaRecyclerView;
    private PopularityAreaRecylerViewAdapter popularityAreaRecylerViewAdapter;
    private List<PopularityAreaRecylerItem> popularityAreaRecylerItems;
    private HashMap<Integer, String> selectValueMap;

    private Button selectButton;
    private FragmentManager manager;

//    private Fragment fagmenttt = PopularityAreaTEST.newInstance(1);

    public PopularityAreaFragment() {
    }

    public static Fragment newInstance(int sectionNumber) {
        PopularityAreaFragment fragment = new PopularityAreaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("sam","onCreate");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("sam","onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("sam","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

//        selectButton.performClick();
//        if(manager.getBackStackEntryCount() > 0) {     // 이전화면 유지
//            manager.popBackStack();
//        }

        Log.d("sam","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("sam","onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("sam","onStop");
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        Log.d("sam","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("sam","onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("sam","onCreateView");

        View rootView = inflater.inflate(R.layout.fragment_selectionarea_popularity_layout, container, false);
//        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        popularityAreaRecylerItems = new ArrayList<PopularityAreaRecylerItem>();
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("가로수길","11"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("선릉","12"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("무슨길","13"));
        popularityAreaRecylerItems.add(new PopularityAreaRecylerItem("기타","14"));


        selectValueMap = new HashMap<Integer, String>();
        selectButton = (Button)rootView.findViewById(R.id.popularity_area_select_btn);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectValueMap.size() == 0) {
                    CommonUtil.getInstance().custom_toast(getContext(), "지역을 선택해 주세요.");
                    return;
                }

                for( Integer key : selectValueMap.keySet() ){

                    Log.d("TEST12", String.format("키 : %s, 값 : %s", key, selectValueMap.get(key)) );
//                    ((SelectionAreaActivity)getActivity()).testTab()
                }


                ActivityStart(new Intent(getActivity(), HotPlaceResultActivity.class), null);
            }
        });


        popularityAreaRecyclerView = (RecyclerView) rootView.findViewById(R.id.popularity_area_recycler_view);
//        popularityAreaRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),new LinearLayoutManager(getContext()).getOrientation());
        popularityAreaRecyclerView.addItemDecoration(dividerItemDecoration);



        popularityAreaRecyclerView.setHasFixedSize(true);

        popularityAreaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        popularityAreaRecyclerView.setItemAnimator(new DefaultItemAnimator());

        popularityAreaRecylerViewAdapter = new PopularityAreaRecylerViewAdapter(popularityAreaRecylerItems, selectValueMap);
        popularityAreaRecyclerView.setAdapter(popularityAreaRecylerViewAdapter);
//        popularityAreaRecyclerView.setFocusable(false);

        manager = getActivity().getSupportFragmentManager();
        return rootView;
    }


    public void testABC(){


//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.remove(fagmenttt);
//        ft.commit();
//        manager.popBackStack();
//        ft.detach(this).attach(this).commit();
    }
}

