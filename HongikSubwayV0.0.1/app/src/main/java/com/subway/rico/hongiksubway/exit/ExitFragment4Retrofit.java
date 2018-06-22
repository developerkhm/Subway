package com.subway.rico.hongiksubway.exit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.subway.rico.hongiksubway.client.APIClient;
import com.subway.rico.hongiksubway.client.APIInterface;
import com.subway.rico.hongiksubway.client.pojo.MultipleResource;
import com.subway.rico.hongiksubway.common.CommonUtil;
import com.subway.rico.hongiksubway.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExitFragment4Retrofit extends Fragment {
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

    private APIInterface apiInterface;

    public ExitFragment4Retrofit() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit_fragment4, container, false);

        mImg_Title = (ImageView) view.findViewById(R.id.img_title);
        mImg_Title.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_TITLE_IMG_W);
        mImg_Title.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_TITLEIMG_H);
        CommonUtil.getInstance().loadImage(getActivity(), R.drawable.exit_head, mImg_Title);

        mImg_res = R.drawable.exit_main_3_1;
        mImg_Main = (ImageView) view.findViewById(R.id.img_main);
        mImg_Main.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_W);
        mImg_Main.getLayoutParams().height = CommonUtil.getInstance().utilGetViewWidthPx(MAX_MAIN_H);
        CommonUtil.getInstance().loadImage(getActivity(), mImg_res, mImg_Main);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.getLayoutParams().width = CommonUtil.getInstance().utilGetViewWidthPx(MAX_RECYLER_W);
        //mRecyclerView.getLayoutParams().height = utilGetViewWidthPx((3840-1146));

        setRecycle();


        return view;
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

        apiInterface = APIClient.getClient().create(APIInterface.class);

        /**
         GET List Resources
         **/
        Call<MultipleResource> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {


                Log.d("TAG",response.code()+"");

                String displayResponse = "";

                MultipleResource resource = response.body();
                Integer text = resource.page;
                Integer total = resource.total;
                Integer totalPages = resource.totalPages;
                List<MultipleResource.Datum> datumList = resource.data;

                displayResponse += text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

                for (MultipleResource.Datum datum : datumList) {
                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";
                }

//                responseText.setText(displayResponse);
                Log.d("test", displayResponse);

            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });

//        /**
//         Create new user
//         **/
//        User user = new User("morpheus", "leader");
//        Call<User> call1 = apiInterface.createUser(user);
//        call1.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user1 = response.body();
//
//                Toast.makeText(getActivity(), user1.name + " " + user1.job + " " + user1.id + " " + user1.createdAt, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                call.cancel();
//            }
//        });

//        /**
//         GET List Users
//         **/
//        Call<UserList> call2 = apiInterface.doGetUserList("2");
//        call2.enqueue(new Callback<UserList>() {
//            @Override
//            public void onResponse(Call<UserList> call, Response<UserList> response) {
//
//                UserList userList = response.body();
//                Integer text = userList.page;
//                Integer total = userList.total;
//                Integer totalPages = userList.totalPages;
//                List<UserList.Datum> datumList = userList.data;
//                Toast.makeText(getActivity(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();
//
//                for (UserList.Datum datum : datumList) {
//                    Toast.makeText(getActivity(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<UserList> call, Throwable t) {
//                call.cancel();
//            }
//        });


//        /**
//         POST name and job Url encoded.
//         **/
//        Call<UserList> call3 = apiInterface.doCreateUserWithField("morpheus","leader");
//        call3.enqueue(new Callback<UserList>() {
//            @Override
//            public void onResponse(Call<UserList> call, Response<UserList> response) {
//                UserList userList = response.body();
//                Integer text = userList.page;
//                Integer total = userList.total;
//                Integer totalPages = userList.totalPages;
//                List<UserList.Datum> datumList = userList.data;
//                Toast.makeText(getActivity(), text + " page\n" + total + " total\n" + totalPages + " totalPages\n", Toast.LENGTH_SHORT).show();
//
//                for (UserList.Datum datum : datumList) {
//                    Toast.makeText(getActivity(), "id : " + datum.id + " name: " + datum.first_name + " " + datum.last_name + " avatar: " + datum.avatar, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UserList> call, Throwable t) {
//                call.cancel();
//            }
//        });




        // ArrayList 에 Item 객체(데이터) 넣기
//        ArrayList<Item> items = new ArrayList();
//
//        items.add(new Item(R.drawable.exit_main_4_banner_sample));
//        items.add(new Item(R.drawable.exit_main_4_banner_sample_2));
//        items.add(new Item(R.drawable.exit_main_4_banner_sample_3));
//        items.add(new Item(R.drawable.exit_main_4_banner_sample));
//
//        mRecyclerView.setLayoutManager(mLayoutManager); // LinearLayout으로 설정
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator()); // Animation Defualt 설정
//        // mRecyclerView.addItemDecoration(new RecyclerViewDecoration(this, RecyclerViewDecoration.VERTICAL_LIST)); //Decoration 설정
//        mAdapter = new RecyclerViewAdapter(items);
//        mRecyclerView.setAdapter(mAdapter);
//        //mAdapter.notifyDataSetChanged();
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
    public void onDestroy() {
        super.onDestroy();
    }
}
