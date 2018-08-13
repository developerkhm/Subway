package com.skt.tmaphot.net.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String VERSION = "v1";
    private static final String DEFAULT_URL = "http://api.ordertable.co.kr/";

//    private static final String BASE_URL = String.format("%s%s", DEFAULT_URL, VERSION);
    private static final String BASE_URL = "http://api.ordertable.co.kr/v1/";
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    public static StackExchangeService createService(){

        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.addInterceptor(interceptor);
        builder = builder.client(httpClientBuilder.build());
        retrofit = builder.build();

        return retrofit.create(StackExchangeService.class);
    }

    public static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            return chain.proceed(chain.request());
        }
    };
}
