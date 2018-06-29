package com.example.velmurugan.retrofitandroidexample;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static OkHttpClient client;

    public static String BASE_URL ="http://swopenAPI.seoul.go.kr/api/subway/696e697566737069313030676957476f/";
    private static Retrofit retrofit;
    public static Retrofit getClient(){

//        OkHttpClient client = null;
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(180, TimeUnit.SECONDS);
//        builder.addNetworkInterceptor(new StethoInterceptor());
//        client = builder.build();

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
