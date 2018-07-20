package com.skt.tmaphot.activity;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.skt.tmaphot.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CouponListActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<SingModel> singModels = new ArrayList<>();
    private LinearLayoutManager layoutManager;

    private boolean mLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);




        singModels.add(new SingModel("https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg","test"));
        singModels.add(new SingModel("https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg","test"));
        singModels.add(new SingModel("https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg","test"));
        singModels.add(new SingModel("https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg","test"));
        singModels.add(new SingModel("https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg","test"));
        singModels.add(new SingModel("https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg","test"));
        singModels.add(new SingModel("https://picksell.co.kr/images/product/129084/5809e78b-80ac-42ac-a455-eb179deab4ed.jpg","test"));




        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(getApplicationContext(),singModels);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("T00", "onScrolled");

                int totalItem = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!mLoading && lastVisibleItem == totalItem - 1) {
                    mLoading = true;
                    // Scrolled to bottom. Do something here.

                    singModels.add(new SingModel("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg","test"));
                    singModels.add(new SingModel("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg","test"));
                    singModels.add(new SingModel("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg","test"));
                    singModels.add(new SingModel("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg","test"));
                    singModels.add(new SingModel("https://picksell.co.kr/images/product/128734/1104487a-82c9-41b4-be65-89d3f80088f5.jpg","test"));

                    adapter.notifyDataSetChanged();

                    mLoading = false;
                }
            }
        });

    }//END


    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        Context context;
        ArrayList<SingModel> singModels;

        public Adapter(Context context, ArrayList<SingModel> singModels) {
            this.context = context;
            this.singModels = singModels;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.list_coupon_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            SingModel singModel = singModels.get(position);

            Log.d("TT1234", "TTTTTT : " + singModel.getSong());

//            holder.txt.setText(singModel.getSinger());


            RequestOptions options = new RequestOptions();
            options.fitCenter();

            Glide.with(getBaseContext())
                    .load(singModel.getSong())
                    .apply(options)
                    .into(holder.song);
        }

        @Override
        public int getItemCount() {
            return singModels.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView song;
            TextView txt;

            public ViewHolder(View itemView) {
                super(itemView);
                song = itemView.findViewById(R.id.list_coupon_img);
//                txt = itemView.findViewById(R.id.list_coupon_txt);

            }
        }
    }

    public class SingModel {
        private String song;
        private String singer;

        public SingModel(String song, String singer) {
            this.song = song;
            this.singer = singer;
        }

        public String getSong() {
            return song;
        }

        public void setSong(String song) {
            this.song = song;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.store_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}


