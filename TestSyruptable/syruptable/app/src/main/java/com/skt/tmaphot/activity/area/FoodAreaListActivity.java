package com.skt.tmaphot.activity.area;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.BaseActivity;
import com.skt.tmaphot.activity.main.hotplace.HotplaceGridAdapter;
import com.skt.tmaphot.activity.main.hotplace.HotplaceGridViewItem;
import com.skt.tmaphot.activity.main.store.StoreInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class FoodAreaListActivity extends BaseActivity {

    // 핫플레이스
    private List<HotplaceGridViewItem> hotplace_Pop_ItemList, hotplace_Distance_ItemList;
    private GridView hotplaceGridview;
    private boolean ishotplaceItemLoad;
    private HotplaceGridAdapter hotplaceGridAdapter;
    private boolean ishotplaceModeDistance;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_selectionarea_popularity_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        hotplaceGridview = (GridView) findViewById(R.id.hotplace_gridview);

        initHotplaceSet();
    }

    private void initHotplaceSet() {


        hotplaceGridview.setOnItemClickListener(onItemClickListenerHotplace);

        if (hotplace_Pop_ItemList == null)
            hotplace_Pop_ItemList = new ArrayList<HotplaceGridViewItem>();

        hotplaceGridAdapter = new HotplaceGridAdapter(this, hotplace_Pop_ItemList);
        hotplaceGridview.setAdapter(hotplaceGridAdapter);
        hotplaceGridview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

                Log.e("GridView", "onScrollStateChanged : " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                Log.e("GridView", "firstVisibleItem: " + firstVisibleItem + "\nvisibleItemCount: " + visibleItemCount + "\ntotalItemCount: " + totalItemCount);

                // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
                // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
                int count = totalItemCount - visibleItemCount;


                if (firstVisibleItem >= count && totalItemCount != 0 && ishotplaceItemLoad == false) {
                    //loadGridItem();
                    Log.e("GridView", "XXXXXXXXX loadGridItem : ");
                }
            }
        });


        hotplaceGridview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

                Log.e("GridView", "onScrollStateChanged : " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                Log.e("GridView", "firstVisibleItem: " + firstVisibleItem + "\nvisibleItemCount: " + visibleItemCount + "\ntotalItemCount: " + totalItemCount);

                // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
                // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
                int count = totalItemCount - visibleItemCount;


                if (firstVisibleItem >= count && totalItemCount != 0 && ishotplaceItemLoad == false) {
                    //loadGridItem();
                    Log.e("GridView", "XXXXXXXXX loadGridItem : ");
                    loadHotplaceItem(false);
                }
            }
        });


        //데이터 로드
        loadHotplaceItem(ishotplaceModeDistance);
    }

    private void loadHotplaceItem(final boolean mode) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                ishotplaceItemLoad = true;

                if (!mode) {

                    Log.d("QQQ", "pop dataload");

//                    if(hotplace_Pop_ItemList == null);
//                        hotplace_Pop_ItemList = new ArrayList<HotplaceGridViewItem>();

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "몰라짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));

                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Pop_ItemList.add(new HotplaceGridViewItem("http://trendinsight.biz/wp-content/uploads/2014/05/file291298583404-1024x682.jpg",
                            "황제짜장", "수제피자", "200m", "50%", "맛이어요"));
                } else {

                    Log.d("QQQ", "distance dataload");

//                    if(hotplace_Distance_ItemList == null);
//                        hotplace_Distance_ItemList = new ArrayList<HotplaceGridViewItem>();


                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));
                    hotplace_Distance_ItemList.add(new HotplaceGridViewItem("http://spnimage.edaily.co.kr/images/photo/files/NP/S/2016/10/PS16102100005.jpg",
                            "distance", "수제피자", "200m", "50%", "맛이어요"));

                }

                ishotplaceItemLoad = false;
                hotplaceGridview.post(new Runnable() {
                    @Override
                    public void run() {
                        hotplaceGridAdapter.notifyDataSetChanged();
                    }
                });

                hotplaceGridview.setFocusable(false);
            }
        }).start();
    }


    AdapterView.OnItemClickListener onItemClickListenerHotplace = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //이렇게 아이디 등 값을 가져와서 전달한다.
            //TextView tv = (TextView)view.findViewById(R.id.hotplace_grid_txt_title);
//            Intent intent = new Intent(FoodAreaListActivity.this, StoreInfoActivity.class);
//            Bundle bundle = new Bundle();
//            startActivity(intent);
        }
    };
}
