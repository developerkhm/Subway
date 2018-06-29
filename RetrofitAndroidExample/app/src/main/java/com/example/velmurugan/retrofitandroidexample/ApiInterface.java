package com.example.velmurugan.retrofitandroidexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("json/gateInfo/0/10/{id}") //지하철 출구 번호별 정보
    Call<GateInfo> getGateInfo(@Path(value="id", encoded=true) String id);
}
