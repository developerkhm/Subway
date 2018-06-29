package com.example.velmurugan.retrofitandroidexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    GateInfo gateInfo; //지하철 출구 번호별 정보
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<GateList> gateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<GateInfo> call = apiService.getGateInfo("신촌");

                call.enqueue(new Callback<GateInfo>() {
                    @Override
                    public void onResponse(Call<GateInfo> call, Response<GateInfo> response) {
                        gateInfo = response.body();


                        if (response.isSuccessful()) {
                            try {
                                //null처리 해줘야됨 응답 시간 때문에 그런가? 콜백인데??
                                gateList = gateInfo.getGateList();
                                for (GateList gate : gateList) {
                                    Log.d("TAG", "Response = " + gate.getCfrBuild());
                                }
                                recyclerAdapter.setGateList(gateList);
                            }
                            catch(Exception e) {
                                Log.d("TAG", e.toString());
                                Toast.makeText(MainActivity.this, "Response 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GateInfo> call, Throwable t) {
                        Log.d("TAG", "Response = " + t.toString());
                    }
                });
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new RecyclerAdapter(getApplicationContext(),gateList);
        recyclerView.setAdapter(recyclerAdapter);

//        LinearLayout test = (LinearLayout)findViewById(R.id.test);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "클릭", Toast.LENGTH_SHORT).show();
//            }
//        });


    }


//    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//
//            int lastvisibleitemposition = layoutManager.findLastVisibleItemPosition();
//
//            if (lastvisibleitemposition == recyclerAdapter.getItemCount() - 1) {
//
//                if (!loading && !isLastPage) {
//
//                    loading = true;
//                    fetchData((++pageCount));
//                    // Increment the pagecount everytime we scroll to fetch data from the next page
//                    // make loading = false once the data is loaded
//                    // call mAdapter.notifyDataSetChanged() to refresh the Adapter and Layout
//
//                }
//
//
//            }
//        }
//    });

}
